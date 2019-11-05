package fr.unice.polytech.si5.al.analystservice.controllers;

import fr.unice.polytech.si5.al.analystservice.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Response;
import spark.Route;

public class DashboardApi {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static Route addDashboardRoute = new Route() {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            logger.info("DashboardApi::addDashboardRoute called");
            return null;
        }
    };

    public static Route saveDashboardRoute = new Route() {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            logger.info("DashboardApi::saveDashboardRoute called");
            return null;
        }
    };

    public static Route deleteDashboardRoute = new Route() {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            logger.info("DashboardApi::deleteDashboardRoute called");
            return null;
        }
    };

    public static Route dashboardsListRoute = new Route() {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            logger.info("DashboardApi::dashboardsListRoute called");

            return null;
        }
    };
}
