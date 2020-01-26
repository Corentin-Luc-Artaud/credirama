package fr.unice.polytech.creditrama;

import com.google.gson.Gson;
import fr.unice.polytech.creditrama.clients.AdultSerious;
import fr.unice.polytech.creditrama.clients.Client;
import fr.unice.polytech.creditrama.clients.ClientCreationResponse;
import fr.unice.polytech.creditrama.transactions.Transaction;
import lombok.SneakyThrows;
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
        LocalDateTime first = LocalDateTime.now();
        LocalDateTime second = first.plus(1200, ChronoUnit.MILLIS);
        LocalDateTime third = second.plus(100, ChronoUnit.MILLIS);
        LocalDateTime fourth = first.plusMinutes(1);
        LocalDateTime fifth = first.minusMinutes(1);

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

            publish(new Transaction(client, 200, first), url, "First transaction (Success)");
            publish(new Transaction(client, 200, second), url, "Second transaction (Success)");
            publish(new Transaction(client, 200, third), url, "Third transaction (Fail, too fast)");
            publish(new Transaction(client, 200, fourth), url, "Fourth transaction (Fail, in future)");
            publish(new Transaction(client, 200, fifth), url, "Fifth transaction (Fail, in past)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private static void publish(Transaction transaction, URL url, String comment) {
        Thread.sleep(100);
        System.out.println("\n --- Publishing " + comment);

        try {
            new RestTemplate().postForObject(url.toString(), transaction, String.class);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println();
    }
}
