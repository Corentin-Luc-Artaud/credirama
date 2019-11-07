package fr.unice.polytech.si5.al.analystservice.controllers;

import fr.unice.polytech.si5.al.analystservice.Main;
import fr.unice.polytech.si5.al.analystservice.services.Kibana;
import fr.unice.polytech.si5.al.analystservice.services.VisualisationServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import spark.Route;

public class IndexPatternApi {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static Route addIndexPattern = (request, response) -> {
        logger.info("VisualisationApi::addVisualisationRoute called");

        JSONObject element = new JSONObject();
        System.out.println(request.body());
        String title = (String) new JSONObject(request.body()).get("title");
        element.put("title", title);
        element.put("timeFieldName", "timestamp");

        JSONObject attributes = new JSONObject();
        attributes.put("attributes", element);

        Kibana kibana = new Kibana();
        System.out.println(attributes);
        kibana.addIndexPattern(attributes, title);
        return "ok";
    };
}
