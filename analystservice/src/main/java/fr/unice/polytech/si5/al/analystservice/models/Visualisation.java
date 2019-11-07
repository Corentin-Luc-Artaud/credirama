package fr.unice.polytech.si5.al.analystservice.models;

import com.google.gson.Gson;
import fr.unice.polytech.si5.al.analystservice.models.timelion.TimelionChart;

public class Visualisation {
    String id;
    String type;
    TimelionChart attributes;

    public Visualisation(String id, TimelionChart timelionChart) throws Exception {
        this.id = id;
        this.type = "visualization";
        this.attributes = timelionChart;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
