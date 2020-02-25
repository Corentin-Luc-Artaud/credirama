package fr.unice.polytech.si5.al.clientService.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

    long transactionTime;
    String zone;

    public Transaction(long accountID, long clientID, double amount, long creationTime, String zone) {
        this.accountID = accountID;
        this.clientID = clientID;
        this.amount = amount;
        this.transactionTime = creationTime;
        this.zone = zone;
    }

    public LocalDateTime localDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(transactionTime), ZoneOffset.of(zone));
    }

}
