package fr.unice.polytech.creditrama.transactions;

import fr.unice.polytech.creditrama.clients.Client;
import lombok.*;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Transaction {
    long accountID;
    long clientID;
    double amount;
    LocalDateTime time;

    public Transaction withClient(Client client){
        this.accountID = client.getAccountID();
        this.clientID = client.getClientID();
        return this;
    }

    public boolean isPast(){
        return time.isBefore(now());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "accountID=" + accountID +
                ", clientID=" + clientID +
                ", amount=" + amount +
                ", time=" + time +
                '}';
    }
}
