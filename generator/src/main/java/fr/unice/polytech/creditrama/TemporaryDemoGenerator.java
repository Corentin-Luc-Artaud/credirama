package fr.unice.polytech.creditrama;

import com.google.gson.Gson;
import fr.unice.polytech.creditrama.clients.AdultSerious;
import fr.unice.polytech.creditrama.clients.Client;
import fr.unice.polytech.creditrama.clients.ClientCreationResponse;
import fr.unice.polytech.creditrama.transactions.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Hello world!
 */
public class TemporaryDemoGenerator {

    public static void main(String[] args) throws IOException {
        String host = args.length != 0 ? args[0] : "http://localhost:";

        LocalDateTime create = LocalDateTime.now().minusHours(1);
        LocalDateTime first = LocalDateTime.now().minusMinutes(5);
        LocalDateTime second = first.minusMinutes(5).plus(3, ChronoUnit.MINUTES);

        ClientCreationResponse response;
        Gson gson = new Gson();
        Client client = new AdultSerious();

        try {
            URL url = new URL(host + "8081/");
            RestTemplate restTemplate = new RestTemplate();
            String time = create.toString();
            restTemplate.postForEntity(url.toString(), time, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL(host + "8080/clients/");
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.toString(), client, String.class);

            response = gson.fromJson(responseEntity.getBody(), ClientCreationResponse.class);
            client.setClientID(response.getClientID());
            client.setAccountID(response.getAccountID());

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL(host + "8080/transactions/");
            Transaction transaction = new Transaction()
                    .withClient(client)
                    .withAmount(200)
                    .withTime(first);

            Transaction transaction2 = new Transaction()
                    .withClient(client)
                    .withAmount(200)
                    .withTime(second);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(url.toString(), transaction, String.class);

 //           Thread.sleep(3000);

 //           restTemplate = new RestTemplate();
 //           restTemplate.postForObject(url.toString(), transaction2, String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
