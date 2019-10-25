package fr.unice.polytech.creditrama;

import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;

import static fr.unice.polytech.creditrama.ClientFactory.createBatch;

/**
 * Hello world!
 */
public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        String host = args.length != 0 ? args[0] : "http://localhost:8080";

        createBatch(10).forEach(client -> {
            logger.info(client.toString());
            try {
                URL url = new URL("http://localhost:8081");
                RestTemplate restTemplate = new RestTemplate();
                String time = TimeFactory.generateTimeStamp(client.getDateOfBirth()).toString();
                restTemplate.postForEntity(url.toString(), time, String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                URL url = new URL(host + "/clients/");
                RestTemplate restTemplate = new RestTemplate();

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("firstName", client.getFirstName());
                jsonObject.addProperty("lastName", client.getLastName());
                jsonObject.addProperty("city", client.getCity());
                jsonObject.addProperty("height", client.getHeight());
                jsonObject.addProperty("weight", client.getWeight());

                restTemplate.postForEntity(url.toString(), jsonObject, String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
