package fr.unice.polytech.si5.al.clientService.components;

import com.google.gson.Gson;
import fr.unice.polytech.si5.al.clientService.kafka.Producer;
import fr.unice.polytech.si5.al.clientService.models.Client;
import fr.unice.polytech.si5.al.clientService.repositories.ClientRepository;
import fr.unice.polytech.si5.al.models.events.CrediramaEvent;
import fr.unice.polytech.si5.al.models.events.EventName;
import fr.unice.polytech.si5.al.models.events.EventPhase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Component
public class ClientRegisterer {

    @Autowired
    private ClientRepository clientRepository;

    private static final Logger logger = LogManager.getLogger("ClientRegisterer");

    private Producer kafkaProducer;

    private Gson gson;

    public long addNewClient(Client newClient) {
        logger.info("New client has been registered");
        clientRepository.save(newClient);
        logger.info("New bank account has been created for client " + newClient.getFirstName() + " " + newClient.getLastName());

        CrediramaEvent eventMessage = CrediramaEvent.builder()
                .eventName(EventName.CLIENT_SUBSCRITION)
                .eventPhase(EventPhase.PRODUCTION)
                .payload(newClient)
                .timestamp(setRandomTime())
                .build();
        try {
            String message = gson.toJson(eventMessage);
            this.kafkaProducer.sendMessage("client", message);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return newClient.getId();
    }

    public List<Client> getClients() {
        List<Client> res = new ArrayList<>();
        for (Client client : clientRepository.findAll()) {
            res.add(client);
        }
        return res;
    }

    private String setRandomTime() {

        long offset = Timestamp.valueOf("2019-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2019-11-01 00:00:00").getTime();
        long diff = end - offset + 1;
        Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));

        // Conversion
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String date_iso = sdf.format(rand);
        return date_iso;
    }

    @PostConstruct
    public void onInit() {
        String kafkaBrokerAddress = "kafka:9093";
        this.kafkaProducer = new Producer(kafkaBrokerAddress);
        this.gson = new Gson();
    }
}
