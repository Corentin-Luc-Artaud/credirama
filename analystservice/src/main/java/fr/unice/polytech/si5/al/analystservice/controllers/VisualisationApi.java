package fr.unice.polytech.si5.al.analystservice.controllers;

import fr.unice.polytech.si5.al.analystservice.Main;
import fr.unice.polytech.si5.al.analystservice.services.Kibana;
import fr.unice.polytech.si5.al.analystservice.services.VisualisationServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Route;

public class VisualisationApi {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static Route addVisualisationRoute = (request, response) -> {
        logger.info("VisualisationApi::addVisualisationRoute called");
            Kibana kibana = new Kibana();
            VisualisationServices vizServ = new VisualisationServices();
            request.params("title");
            kibana.addVisualisation(vizServ.createTimelion(request.params("id"), request.params("index"), request.params("title"), request.params("expression")));
        return null;
    };

    public static Route saveVisualisationRoute = (request, response) -> {
        logger.info("VisualisationApi::saveVisualisationRoute called");
        return null;
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
