package fr.unice.polytech.si5.al.analystservice.controllers;

import fr.unice.polytech.si5.al.analystservice.services.Kibana;
import fr.unice.polytech.si5.al.analystservice.services.VisualisationServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import spark.Route;

public class IndexPatternApi {
    private static final Logger logger = LogManager.getLogger(IndexPatternApi.class);

    public static Route addIndexPattern = (request, response) -> {
        logger.info("VisualisationApi::addVisualisationRoute called");
        Kibana kibana = new Kibana();
        VisualisationServices vizServ = new VisualisationServices();
        JSONObject jsonObject = new JSONObject(request.body());
        kibana.addVisualisation(vizServ.createTimelion(jsonObject.get("title").toString(), jsonObject.get("expression").toString()), "");
        return null;
    };
}
