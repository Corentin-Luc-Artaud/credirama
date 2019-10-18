package fr.unice.polytech.si5.al.clientService.components;

import com.google.gson.Gson;
import fr.unice.polytech.si5.al.clientService.kafka.Producer;
import fr.unice.polytech.si5.al.clientService.models.Client;
import fr.unice.polytech.si5.al.clientService.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

@Component
public class ClientRegisterer {

    @Autowired
    private ClientRepository clientRepository;

    private static final Logger logger = LogManager.getLogger("ClientRegisterer");

    private Producer kafkaProducer;

    private Gson gson;

    public long addNewClient(Client newClient){
        logger.info("New client has been registered");
        clientRepository.save(newClient);
        logger.info("New bank account has been created for client " + newClient.getFirstName() + " " + newClient.getLastName());

        try {
            this.kafkaProducer.sendMessage("client", gson.toJson(newClient));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return newClient.getId();
    }

    @PostConstruct
    public void onInit(){
        String kafkaBrokerAdress = "kafka:9093";
        this.kafkaProducer = new Producer(kafkaBrokerAdress);
        this.gson = new Gson();
    }
}
