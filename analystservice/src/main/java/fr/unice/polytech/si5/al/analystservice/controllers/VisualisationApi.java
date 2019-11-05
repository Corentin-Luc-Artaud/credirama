package fr.unice.polytech.si5.al.analystservice.controllers;

import fr.unice.polytech.si5.al.analystservice.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Response;
import spark.Route;

public class VisualisationApi {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static Route addVisualisationRoute = new Route() {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            logger.info("VisualisationApi::addVisualisationRoute called");
            return null;
        }
    };

    public static Route saveVisualisationRoute = new Route() {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            logger.info("VisualisationApi::saveVisualisationRoute called");
            return null;
        }
    };

    public static Route deleteVisualisationRoute = new Route() {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            logger.info("VisualisationApi::deleteVisualisationRoute called");
            return null;
        }
    };

    public static Route visualisationsListRoute = new Route() {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            logger.info("VisualisationApi::visualisationsListRoute called");
            return null;
        }
    };
}
