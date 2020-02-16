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

    long transactionTime; //TODO UTC from time service
    LocalDateTime transactionLocalTime; 
    

    public Transaction(long accountID, long clientID, double amount, long creationTime, String zone) {
        this.accountID = accountID;
        this.clientID = clientID;
        this.amount = amount;
        this.transactionTime = creationTime;
        this.transactionLocalTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(creationTime), ZoneId.of(zone));
        
        //time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public LocalDateTime localDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(transactionTime), ZoneId.systemDefault());
    }

    public long getTransactionTime() {
        return this.transactionTime;
    }
}
