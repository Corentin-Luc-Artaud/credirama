package fr.unice.polytech.si5.al.analystservice.models.timelion;

import com.google.gson.Gson;

public class TimelionChart {
    public String title;
    public String type;
    TimelionParams params;

    public TimelionChart(String title, TimelionParams params) {
        this.title = title;
        this.type = "timelion";
        this.params = params;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
