package fr.unice.polytech.creditrama.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientCreationResponse {
    private String status;
    private long accountID;
    private long clientID;

    public ClientCreationResponse(@JsonProperty("status") String status,
                                  @JsonProperty("accountID") long accountID,
                                  @JsonProperty("clientID") long clientID) {
        this.status = status;
        this.accountID = accountID;
        this.clientID = clientID;
    }
}
