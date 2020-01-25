package fr.unice.polytech.creditrama.transactions;

import fr.unice.polytech.creditrama.clients.Client;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
    long transactionTime;

    public Transaction(long accountID, long clientID, double amount, LocalDateTime time) {
        this.accountID = accountID;
        this.clientID = clientID;
        this.amount = amount;
        this.transactionTime = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public Transaction(Client client, double amount, LocalDateTime time) {
        this.amount = amount;
        this.transactionTime = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        this.accountID = client.getAccountID();
        this.clientID = client.getClientID();
    }

    public boolean isPast() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(transactionTime), ZoneId.systemDefault());
        return localDateTime.isBefore(now());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "accountID=" + accountID +
                ", clientID=" + clientID +
                ", amount=" + amount +
                ", transactionTime=" + transactionTime +
                '}';
    }
}
