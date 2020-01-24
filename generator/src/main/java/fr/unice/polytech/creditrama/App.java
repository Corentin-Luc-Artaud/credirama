package fr.unice.polytech.creditrama;

import com.google.gson.Gson;
import fr.unice.polytech.creditrama.clients.ClientCreationResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;

import static fr.unice.polytech.creditrama.ClientFactory.createBatch;

/**
 * Hello world!
 */
public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        String host = args.length != 0 ? args[0] : "http://localhost:";

        createBatch(50).forEach(client -> {

            LocalDateTime creationTime = TimeFactory.generateTimeStamp(client.getDateOfBirth());
            ClientCreationResponse response;
            Gson gson = new Gson();

            try {
                URL url = new URL(host + "8081/");
                RestTemplate restTemplate = new RestTemplate();
                String time = creationTime.toString();
                logger.info("Setting time to " + time);
                restTemplate.postForEntity(url.toString(), time, String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                URL url = new URL(host + "8080/clients/");
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.toString(), client, String.class);

                response = gson.fromJson(responseEntity.getBody(), ClientCreationResponse.class);

                logger.info("Created client : " + response);

                client.setClientID(response.getClientID());
                client.setAccountID(response.getAccountID());

            } catch (Exception e) {
                e.printStackTrace();
            }

            /*try {
                URL url = new URL(host + "8080/transactions/");
                List<Transaction> transactions = client.makeTransactions(15, creationTime);

                transactions.stream()
                        .filter(Transaction::isPast)
                        .forEach(transaction -> {
                                    RestTemplate restTemplate = new RestTemplate();
                                    restTemplate.postForObject(url.toString(), transaction, String.class);
                                    logger.info("Created transaction : " + transaction);
                                }
                        );
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        });
    }
}
