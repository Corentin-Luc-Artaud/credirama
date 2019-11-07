package fr.unice.polytech.si5.al.analystservice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class CreateTimelionService {
    private static final Logger logger = LogManager.getLogger(CreateTimelionService.class);

    public void createTimelion(String title, String expression) {
        JSONObject request = new JSONObject();

        JSONObject params = new JSONObject();
        params.put("expression", expression);
        params.put("interval", "auto");

        JSONObject visState = new JSONObject();
        visState.put("title", title);
        visState.put("type", "timelion");
        visState.put("params", params);
        visState.put("aggs", new JSONArray());

        JSONObject query = new JSONObject();
        query.put("query", "");
        query.put("language", "kuery");

        JSONArray filter = new JSONArray();

        JSONObject searchSourceJSON = new JSONObject();
        searchSourceJSON.put("filter", filter);
        searchSourceJSON.put("query", query);

        JSONObject kibanaSavedObjectMeta = new JSONObject();
        kibanaSavedObjectMeta.put("searchSourceJSON", searchSourceJSON.toString());

        JSONObject visualizion;
        visualizion = new JSONObject();
        visualizion.put("title", title);
        visualizion.put("visState", visState.toString());
        visualizion.put("uiStateJSON", "{}");
        visualizion.put("description", "");
        visualizion.put("version", 1);
        visualizion.put("kibanaSavedObjectMeta", kibanaSavedObjectMeta);

        request.put("attributes", visualizion);
        logger.info(request.toString());
        Kibana kibana = new Kibana();
        kibana.addVisualisation(request.toString(), "");
    }


}
