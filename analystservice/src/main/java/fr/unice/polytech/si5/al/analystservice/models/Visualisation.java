package fr.unice.polytech.si5.al.analystservice.models;

import com.google.gson.Gson;
import fr.unice.polytech.si5.al.analystservice.models.timelion.TimelionChart;

public class Visualisation {
    TimelionChart attributes;

    public Visualisation(TimelionChart timelionChart) throws Exception {
        this.attributes = timelionChart;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
