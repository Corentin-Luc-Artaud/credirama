package fr.unice.polytech.si5.al.analystservice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class CreateVisualizationService {
    private static final Logger logger = LogManager.getLogger(CreateVisualizationService.class);

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

    public void createHistogram(String title, String variable, String indexPatternId){
        JSONObject migrationVersion = new JSONObject();
        migrationVersion.put("visualization", "7.3.1");

        JSONObject referenceElement = new JSONObject();
        referenceElement.put("name", "kibanaSavedObjectMeta.searchSourceJSON.index");
        referenceElement.put("type", "index-pattern");
        referenceElement.put("id", indexPatternId);

        JSONArray references = new JSONArray();
        references.put(referenceElement);

        JSONObject kibanaSavedObjectMeta = new JSONObject();
        kibanaSavedObjectMeta.put("searchSourceJSON", "{\"query\":{\"query\":\"\",\"language\":\"kuery\"},\"filter\":[],\"indexRefName\":\"kibanaSavedObjectMeta.searchSourceJSON.index\"}");

        JSONObject attributes = new JSONObject();
        attributes.put("title", title);
        attributes.put("visState", "{\"title\":\"" + title + "\",\"type\":\"histogram\",\"params\":{\"type\":\"histogram\",\"grid\":{\"categoryLines\":false},\"categoryAxes\":[{\"id\":\"CategoryAxis-1\",\"type\":\"category\",\"position\":\"bottom\",\"show\":true,\"style\":{},\"scale\":{\"type\":\"linear\"},\"labels\":{\"show\":true,\"filter\":true,\"truncate\":100},\"title\":{}}],\"valueAxes\":[{\"id\":\"ValueAxis-1\",\"name\":\"LeftAxis-1\",\"type\":\"value\",\"position\":\"left\",\"show\":true,\"style\":{},\"scale\":{\"type\":\"linear\",\"mode\":\"normal\"},\"labels\":{\"show\":true,\"rotate\":0,\"filter\":false,\"truncate\":100},\"title\":{\"text\":\"Count\"}}],\"seriesParams\":[{\"show\":\"true\",\"type\":\"histogram\",\"mode\":\"stacked\",\"data\":{\"label\":\"Count\",\"id\":\"1\"},\"valueAxis\":\"ValueAxis-1\",\"drawLinesBetweenPoints\":true,\"showCircles\":true}],\"addTooltip\":true,\"addLegend\":true,\"legendPosition\":\"right\",\"times\":[],\"addTimeMarker\":false,\"labels\":{\"show\":false},\"dimensions\":{\"x\":null,\"y\":[{\"accessor\":0,\"format\":{\"id\":\"number\"},\"params\":{},\"aggType\":\"count\"}]}},\"aggs\":[{\"id\":\"1\",\"enabled\":true,\"type\":\"count\",\"schema\":\"metric\",\"params\":{}},{\"id\":\"2\",\"enabled\":true,\"type\":\"terms\",\"schema\":\"segment\",\"params\":{\"field\":\"" + variable + "\",\"orderBy\":\"1\",\"order\":\"desc\",\"size\":20,\"otherBucket\":false,\"otherBucketLabel\":\"Other\",\"missingBucket\":false,\"missingBucketLabel\":\"Missing\",\"customLabel\":\"Cities\"}}]}");
        attributes.put("uiStateJSON", "{}");
        attributes.put("description", "");
        attributes.put("version", 1);
        attributes.put("kibanaSavedObjectMeta", kibanaSavedObjectMeta);

        JSONObject global = new JSONObject();
        global.put("attributes", attributes);
        global.put("references", references);
        global.put("migrationVersion", migrationVersion);

        logger.info(global.toString());
        Kibana kibana = new Kibana();
        kibana.addVisualisation(global.toString());
    }

}
