package fr.unice.polytech.si5.al.clientService.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.*;

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

    long transactionTime; //TODO UTC from time service
    ZonedDateTime transactionLocalTime;


    public Transaction(long accountID, long clientID, double amount, long creationTime, String zone) {
        this.accountID = accountID;
        this.clientID = clientID;
        this.amount = amount;
        this.transactionTime = creationTime;
        this.transactionLocalTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(creationTime), ZoneId.of(zone));

        //time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public Transaction(long accountID, long clientID, double amount, LocalDateTime creationTime, String zone) {
        this.accountID = accountID;
        this.clientID = clientID;
        this.amount = amount;
        this.transactionTime = creationTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        this.transactionLocalTime = creationTime.atZone(ZoneId.of(zone));

        //time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public LocalDateTime localDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(transactionTime), ZoneId.systemDefault());
    }
}
