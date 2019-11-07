package fr.unice.polytech.si5.al.analystservice.services;

import org.json.JSONObject;

public class DashboardServices {

    public JSONObject createTimelion(String query, String title) {
        String wrapper_str = "{\"attributes\": {\"title\": \"!title\",\"visState\": \"!vistate\",\"uiStateJSON\": \"{}\",\"description\": \"\",\"version\": 1,\"kibanaSavedObjectMeta\": {\"searchSourceJSON\": \"!searchSourceJSON\"}},\"references\": [{\"name\": \"kibanaSavedObjectMeta.searchSourceJSON.index\",\"typeþ\": \"index-pattern\",\"id\": \"creditrama\"}],\"migrationVersion\": {\"visualization\": \"7.3.1\"}}}";
        String vistate = "{\"title\":\"!title\",\"type\":\"histogram\",\"params\":{\"type\":\"histogram\",\"grid\":{\"categoryLines\":false},\"categoryAxes\":[{\"id\":\"CategoryAxis-1\",\"type\":\"category\",\"position\":\"bottom\",\"show\":true,\"style\":{},\"scale\":{\"type\":\"linear\"},\"labels\":{\"show\":true,\"filter\":true,\"truncate\":100},\"title\":{}}],\"valueAxes\":[{\"id\":\"ValueAxis-1\",\"name\":\"LeftAxis-1\",\"type\":\"value\",\"position\":\"left\",\"show\":true,\"style\":{},\"scale\":{\"type\":\"linear\",\"mode\":\"normal\"},\"labels\":{\"show\":true,\"rotate\":0,\"filter\":false,\"truncate\":100},\"title\":{\"text\":\"Count\"}}],\"seriesParams\":[{\"show\":\"true\",\"type\":\"histogram\",\"mode\":\"stacked\",\"data\":{\"label\":\"Count\",\"id\":\"1\"},\"valueAxis\":\"ValueAxis-1\",\"drawLinesBetweenPoints\":true,\"showCircles\":true}],\"addTooltip\":true,\"addLegend\":true,\"legendPosition\":\"right\",\"times\":[],\"addTimeMarker\":false,\"labels\":{\"show\":false},\"dimensions\":{\"x\":null,\"y\":[{\"accessor\":0,\"format\":{\"id\":\"number\"},\"params\":{},\"aggType\":\"count\"}]}},\"aggs\":[{\"id\":\"1\",\"enabled\":true,\"type\":\"count\",\"schema\":\"metric\",\"params\":{}},{\"id\":\"2\",\"enabled\":true,\"type\":\"terms\",\"schema\":\"segment\",\"params\":{\"field\":\"payload.city.keyword\",\"orderBy\":\"1\",\"order\":\"desc\",\"size\":20,\"otherBucket\":false,\"otherBucketLabel\":\"Other\",\"missingBucket\":false,\"missingBucketLabel\":\"Missing\",\"customLabel\":\"Cities\"}}]}";
        String searchSourceJSON = "{\"query\":{\"query\":\"!query\",\"language\":\"kuery\"},\"filter\":[],\"indexRefName\":\"kibanaSavedObjectMeta.searchSourceJSON.index\"}";
        
        String real = wrapper_str;
        real = real.replaceAll("!vistate", vistate);
        real = real.replaceAll("!searchSourceJSON", searchSourceJSON);
        real = real.replaceAll("!title", title);
        real = real.replaceAll("!query", query);


        JSONObject wrapper = new JSONObject(real);
        return wrapper;
    }
}
