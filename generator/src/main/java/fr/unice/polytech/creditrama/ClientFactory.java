package fr.unice.polytech.creditrama;

import fr.unice.polytech.creditrama.clients.*;

import java.util.ArrayList;
import java.util.List;

public class ClientFactory {

    public static List<Client> createBatch(int amount) {
        List<Client> clients = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            int random = Utils.randIntBetween(1, 6);

            Client client;

            switch (random) {
                case 1:
                    client = new AdultCompulsive();
                    break;
                case 2:
                    client = new AdultSerious();
                    break;
                case 3:
                    client = new Child();
                    break;
                case 4:
                    client = new ElderSpending();
                    break;
                case 5:
                    client = new TeenageResponsible();
                    break;
                case 6:
                    client = new TeenageRich();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + random);
            }

            clients.add(client);
        }

        return clients;
    }
}
