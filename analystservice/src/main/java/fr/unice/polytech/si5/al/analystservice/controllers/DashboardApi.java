package fr.unice.polytech.si5.al.analystservice.controllers;

import fr.unice.polytech.si5.al.analystservice.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Route;

public class DashboardApi {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static Route addDashboardRoute = (request, response) -> {
        logger.info("DashboardApi::addDashboardRoute called");
        return null;
    };

    public static Route saveDashboardRoute = (request, response) -> {
        logger.info("DashboardApi::saveDashboardRoute called");
        return null;
    };

    public static Route deleteDashboardRoute = (request, response) -> {
        logger.info("DashboardApi::deleteDashboardRoute called");
        return null;
    };

    public static Route dashboardsListRoute = (request, response) -> {
        logger.info("DashboardApi::dashboardsListRoute called");

        return null;
    };
}
