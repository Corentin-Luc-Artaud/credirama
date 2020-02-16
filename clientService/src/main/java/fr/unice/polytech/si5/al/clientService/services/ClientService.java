package fr.unice.polytech.si5.al.clientService.services;

import fr.unice.polytech.si5.al.clientService.components.AccountCreator;
import fr.unice.polytech.si5.al.clientService.components.ClientRegisterer;
import fr.unice.polytech.si5.al.clientService.models.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;

@Service
public class ClientService {
    private static final Logger root_logger = LogManager.getLogger(ClientService.class);

    private final ClientRegisterer clientRegisterer;
    private final AccountCreator accountCreator;
    private final TimeService timeService;

    public ClientService(ClientRegisterer clientRegisterer, AccountCreator accountCreator, TimeService timeService) {
        this.clientRegisterer = clientRegisterer;
        this.accountCreator = accountCreator;
        this.timeService = timeService;
    }

    public String addNewClient(Client client) {
        try {
            root_logger.info(client);
            client.setCreationTime(timeService.getCurrentTime(), System.getProperty("locale"));
            long idNewClient = clientRegisterer.addNewClient(client);
            long idAccount = accountCreator.createNewAccount(idNewClient);
            return "{\"status\":\"OK\", \"accountID\":" + idAccount + ", \"clientID\":" + idNewClient + "}";
            //return "You have been registered successfully : your new account is " + idAccount + " and your client id is " + idNewClient;
        } catch (Exception e) {
            return "{\"status\":\"ERROR\", \"cause\":\"" + e.getMessage() + "\"}";
        }
    }

    public List<Client> getAllClients() {
        return clientRegisterer.getClients();
    }
}
