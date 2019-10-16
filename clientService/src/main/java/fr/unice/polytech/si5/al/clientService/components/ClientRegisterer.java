package fr.unice.polytech.si5.al.clientService.components;

import fr.unice.polytech.si5.al.clientService.models.Client;
import fr.unice.polytech.si5.al.clientService.repositories.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientRegisterer {

    @Autowired
    private ClientRepository clientRepository;

    private static Logger logger =  LoggerFactory.getLogger(ClientRegisterer.class);

    public long addNewClient(Client newClient){
        logger.info("New client has been registered");
        clientRepository.save(newClient);
        return newClient.getId();
    }
}
