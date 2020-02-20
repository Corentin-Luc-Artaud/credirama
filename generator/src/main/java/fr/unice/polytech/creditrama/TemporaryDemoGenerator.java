package fr.unice.polytech.creditrama;

import com.google.gson.Gson;
import fr.unice.polytech.creditrama.clients.AdultSerious;
import fr.unice.polytech.creditrama.clients.Client;
import fr.unice.polytech.creditrama.clients.ClientCreationResponse;
import fr.unice.polytech.creditrama.transactions.Transaction;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static java.time.ZoneOffset.UTC;

/**
 * Hello world!
 */
public class TemporaryDemoGenerator {

    private static final String HOST = "http://localhost:";
    private static final String TIME_SERVICE = "8081/";
    private static final String ATOMIC_TIME_SERVICE = "9081/";
    private static final String CLIENTS_FR = "8080/clients/";
    private static final String CLIENTS_US = "9080/clients/";
    private static final String TRANSACTIONS = "8080/transactions/";

    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        LocalDateTime create = LocalDateTime.now(UTC).minusHours(1);
        LocalDateTime first = LocalDateTime.now(UTC);
        LocalDateTime second = first.plus(1200, ChronoUnit.MILLIS);
        LocalDateTime third = second.plus(100, ChronoUnit.MILLIS);
        LocalDateTime fourth = first.plusMinutes(2);
        LocalDateTime fifth = first.minusMinutes(2);
        LocalDateTime sixth = first;


        // SET TIME-SERVICE's TIME
        setTime(create);

        // CREATE OUR CLIENT
        Client client = new AdultSerious();
        createClient(client);
        System.out.println("\n -- " + client.toString());


        // PUBLISH TRANSACTIONS
        try {
            setTime(first);
            publish(new Transaction(client, 200, first), "First transaction (Success)");
            publish(new Transaction(client, 200, second), "Second transaction (Success)");
            publish(new Transaction(client, 200, third), "Third transaction (Fail, too fast)");
            publish(new Transaction(client, 200, fourth), "Fourth transaction (Fail, in future)");
            publish(new Transaction(client, 200, fifth), "Fifth transaction (Fail, in past)");
            //publishToFail(new Transaction(client, 200, sixth));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private static void publish(Transaction transaction, String comment) {
        Thread.sleep(100);
        System.out.println("\n --- Publishing " + comment);

        try {
            new RestTemplate().postForObject(new URL(HOST + TRANSACTIONS).toString(), transaction, String.class);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println();
    }

    @SneakyThrows
    private static void publishToFail(Transaction transaction) {

        Thread.sleep(100);
        System.out.println("\n --- Publishing Sixth transaction (Fail, timeservice)");

        new RestTemplate().postForObject(new URL(HOST + TIME_SERVICE + "/fail").toString(), null, String.class);

        try {
            new RestTemplate().postForObject(new URL(HOST + TRANSACTIONS).toString(), transaction, String.class);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println();
    }

    private static void setTime(LocalDateTime time) {
        Arrays.asList(TIME_SERVICE) //, ATOMIC_TIME_SERVICE)
                .forEach(__ -> {
                    try {
                        URL url = new URL(HOST + __);
                        RestTemplate restTemplate = new RestTemplate();
                        long millis = toLong(time);
                        restTemplate.postForEntity(url.toString(), millis, String.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private static void createClient(Client client) {
        try {
            URL url = new URL(HOST + CLIENTS_FR);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.toString(), client, String.class);

            ClientCreationResponse response = gson.fromJson(responseEntity.getBody(), ClientCreationResponse.class);
            client.setClientID(response.getClientID());
            client.setAccountID(response.getAccountID());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long toLong(LocalDateTime ldt) {
        return ldt.toInstant(ZoneOffset.ofHours(0)).toEpochMilli();
    }

}
