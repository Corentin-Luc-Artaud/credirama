package fr.unice.polytech.si5.al.clientService.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Data
@Entity
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long idTransaction;

    long clientID;

    long accountID;

    /**
     * negative if withdrawal, positive otherwise
     */
    double amount;

    long transactionTime = System.currentTimeMillis();

    public Transaction(long accountID, long clientID, double amount, LocalDateTime time) {
        this.accountID = accountID;
        this.clientID = clientID;
        this.amount = amount;
        this.transactionTime = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public LocalDateTime localDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(transactionTime), ZoneId.systemDefault());
    }
}
