package fr.unice.polytech.si5.al.analystservice.models;

import com.google.gson.Gson;

public class IndexPattern {
    String title;

    public IndexPattern(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
