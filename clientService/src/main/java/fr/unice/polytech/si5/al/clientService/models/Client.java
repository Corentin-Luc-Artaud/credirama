package fr.unice.polytech.si5.al.clientService.models;

import com.google.gson.Gson;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Data
@Entity
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @NotNull
    String firstName;

    @NotNull
    String lastName;

    @NotNull
    String city;

    @NotNull
    double height;

    @NotNull
    double weight;


    long clientcreationTime;
    String zone;

    public void setCreationTime(long creationTime, String timezone) {
        this.clientcreationTime = creationTime;
        this.zone = timezone;
    }


    public LocalDateTime creationLocalDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(clientcreationTime), ZoneOffset.of(zone));
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
