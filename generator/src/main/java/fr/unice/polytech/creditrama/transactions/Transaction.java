package fr.unice.polytech.creditrama.transactions;

import fr.unice.polytech.creditrama.clients.Client;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

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
    String zone = "+01:00";

    public Transaction(long accountID, long clientID, double amount, LocalDateTime time) {
        this.amount = amount;
        this.clientID = clientID;
        this.accountID = accountID;
        this.transactionTime = toLong(time);
    }

    public Transaction(Client client, double amount, LocalDateTime time) {
        this(client.getAccountID(), client.getClientID(), amount, time);
    }

    public boolean isPast() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(transactionTime), ZoneId.systemDefault());
        return localDateTime.isBefore(now());
    }

    private long toLong(LocalDateTime ldt) {
        return ldt.toInstant(ZoneOffset.ofHours(0)).toEpochMilli();
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
