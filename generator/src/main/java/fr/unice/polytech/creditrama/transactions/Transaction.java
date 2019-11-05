package fr.unice.polytech.creditrama.transactions;

import lombok.*;

import java.time.LocalDateTime;

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
