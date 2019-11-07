package fr.unice.polytech.si5.al.analystservice.services;

import org.json.JSONArray;
import org.json.JSONObject;

public static class CreateTimelionRequest {

    static JSONObject createTimelionRequest(String title, String expression) {
        JSONObject params = new JSONObject();
        params.append("expression", expression);
        params.append("interval", "auto");
        params.append("aggs", new JSONArray());

        JSONObject visState = new JSONObject();
        visState.append("title", title);
        visState.append("type", "timelion");
        visState.append("params", params);

        JSONObject query = new JSONObject();
        query.append("query", "");
        query.append("language", "kuery");

        JSONArray filter = new JSONArray();

        JSONObject searchSourceJSON = new JSONObject();
        searchSourceJSON.append("query", query);
        searchSourceJSON.append("filter", filter);

        JSONObject kibanaSavedObjectMeta = new JSONObject();
        kibanaSavedObjectMeta.append("searchSourceJSON", searchSourceJSON.toString());

        JSONObject visualizion;
        visualizion = new JSONObject();
        visualizion.append("title", title);
        visualizion.append("visState", visState.toString());
        visualizion.append("uiStateJSON", "{}");
        visualizion.append("description", "");
        visualizion.append("version", 1);
        visualizion.append("kibanaSavedObjectMeta", kibanaSavedObjectMeta);

        return visualizion;
    }


}
