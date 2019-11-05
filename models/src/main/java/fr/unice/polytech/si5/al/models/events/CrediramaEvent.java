package fr.unice.polytech.si5.al.models.events;

import com.google.gson.Gson;
import lombok.Builder;

@Builder
public class CrediramaEvent {
    private EventName eventName;
    private EventPhase eventPhase;
    private Object payload;
    private long timestamp;

    public CrediramaEvent(EventName eventName, EventPhase eventPhase, Object payload, long timestamp) {
        this.eventName = eventName;
        this.eventPhase = eventPhase;
        this.payload = payload;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
