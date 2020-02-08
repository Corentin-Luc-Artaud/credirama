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
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

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

    long creationAbsoliteTime;
    LocalDateTime creationLocalTime;

    public void setCreationTime(long creationTime, String timezone) {
        creationAbsoliteTime = creationTime;
        creationLocalTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(creationTime), ZoneId.of(timezone));
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
