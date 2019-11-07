package fr.unice.polytech.si5.al.analystservice.services;

import org.json.JSONObject;

public class IndexPatternService {

    public JSONObject createIndexPattern(String indexPatternName, String timeFieldName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("title", indexPatternName);
        jsonObject.append("timeFieldName", timeFieldName);

        JSONObject wrapper = new JSONObject();
        wrapper.append("attributes", jsonObject);

        return wrapper;
    }
}