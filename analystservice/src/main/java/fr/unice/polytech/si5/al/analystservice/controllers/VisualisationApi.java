package fr.unice.polytech.si5.al.analystservice.controllers;

import fr.unice.polytech.si5.al.analystservice.Main;
import fr.unice.polytech.si5.al.analystservice.services.CreateVisualizationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import spark.Route;

public class VisualisationApi {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static Route addVisualisationRoute = (request, response) -> {
        logger.info("VisualisationApi::addVisualisationRoute called");

        // get body
        JSONObject body = new JSONObject(request.body());

        // call create timelion service
        CreateVisualizationService createVisualizationService = new CreateVisualizationService();
        createVisualizationService.createTimelion(body.get("title").toString(), body.get("expression").toString());

        return "ok";
    };

    public static Route addHistogramRoute = (request, response) -> {
        logger.info("VisualisationApi::addHistogram called");

        // get body
        JSONObject body = new JSONObject(request.body());

        // call create timelion service
        CreateVisualizationService createVisualizationService = new CreateVisualizationService();
        createVisualizationService.createHistogram(body.get("title").toString(), body.get("variable").toString(), body.get("indexPatternId").toString());


        return "ok";
    };

    public static Route deleteVisualisationRoute = (request, response) -> {
        logger.info("VisualisationApi::deleteVisualisationRoute called");
        return null;
    };

    public static Route visualisationsListRoute = (request, response) -> {
        logger.info("VisualisationApi::visualisationsListRoute called");
        return null;
    };
}
