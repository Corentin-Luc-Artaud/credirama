package fr.unice.polytech.si5.al.analystservice.controllers;

import fr.unice.polytech.si5.al.analystservice.Main;
import fr.unice.polytech.si5.al.analystservice.services.DashboardServices;
import fr.unice.polytech.si5.al.analystservice.services.Kibana;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import spark.Route;

public class DashboardApi {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static Route addDashboardRoute = (request, response) -> {
        logger.info("DashboardApi::addDashboardRoute called");
        Kibana kibana = new Kibana();
        DashboardServices vizServ = new DashboardServices();
        JSONObject jsonObject = new JSONObject(request.body());
        kibana.addVisualisation(vizServ.createTimelion(jsonObject.get("title").toString(), jsonObject.get("request").toString()).toString(), "");
        return "ok";
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
