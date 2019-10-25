package fr.unice.polytech.si5.al.clientService.services;

import fr.unice.polytech.si5.al.clientService.components.AccountCreator;
import fr.unice.polytech.si5.al.clientService.components.ClientRegisterer;
import fr.unice.polytech.si5.al.clientService.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRegisterer clientRegisterer;

    @Autowired
    private AccountCreator accountCreator;

    @Autowired
    private TimeService timeService;

    public String addNewClient(Client client) {
        client.setCreationTime(timeService.getCurrentTime());
        long idNewClient = clientRegisterer.addNewClient(client);
        accountCreator.createNewAccount(idNewClient);
        return "You have been registered successfully";
    }
}
