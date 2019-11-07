package fr.unice.polytech.si5.al.analystservice.models.timelion;

import com.google.gson.Gson;

public class TimelionParams {
    public String expression;
    public String interval;

    public TimelionParams(String expression) {
        this.expression = expression;
        this.interval = "auto";
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
