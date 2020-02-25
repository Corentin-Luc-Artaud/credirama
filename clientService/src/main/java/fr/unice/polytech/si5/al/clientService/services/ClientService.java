package fr.unice.polytech.si5.al.clientService.services;

import fr.unice.polytech.si5.al.clientService.components.AccountCreator;
import fr.unice.polytech.si5.al.clientService.components.ClientRegisterer;
import fr.unice.polytech.si5.al.clientService.models.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Arrays;
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

            System.err.println(System.getenv().keySet());
            System.err.println(System.getenv("locale"));

            client.setCreationTime(timeService.getCurrentTime(), "UTC");
            long idNewClient = clientRegisterer.addNewClient(client);
            long idAccount = accountCreator.createNewAccount(idNewClient);
            System.out.println("You have been registered successfully : your new account is " + idAccount + " and your client id is " + idNewClient);

            return "{\"status\":\"OK\", \"accountID\":" + idAccount + ", \"clientID\":" + idNewClient + "}";

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\":\"ERROR\", \"cause\":\"" + Arrays.toString(e.getStackTrace()) + "\"}";
        }
    }

    public List<Client> getAllClients() {
        return clientRegisterer.getClients();
    }
}
