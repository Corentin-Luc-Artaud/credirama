package fr.unice.polytech.si5.al.analystservice;

import fr.unice.polytech.si5.al.analystservice.controllers.DashboardApi;
import fr.unice.polytech.si5.al.analystservice.controllers.IndexPatternApi;
import fr.unice.polytech.si5.al.analystservice.controllers.VisualisationApi;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        // The server is automatically started when you do something that requires
        // the server to be started (i.e. declaring a route or setting the port).
        port(9300); // Spark will run on port 9300



        path("/api", () -> {
            // before("/*", (q, a) -> log.info("Received api call"));
            get("/healthcheck", (req, res) -> "green");

            path("/visualisations", () -> {
                get("/", VisualisationApi.visualisationsListRoute);
                post("/add", VisualisationApi.addVisualisationRoute);
                post("/addhistogram", VisualisationApi.addHistogramRoute);
                delete("/remove", VisualisationApi.deleteVisualisationRoute);
            });

            path("/dashboards", () -> {
                get("/", DashboardApi.dashboardsListRoute);
                post("/add", DashboardApi.addDashboardRoute);
                post("/save", DashboardApi.saveDashboardRoute);
                delete("/remove", DashboardApi.deleteDashboardRoute);
            });

            path("/index-patterns", () -> {
                post("/add", IndexPatternApi.addIndexPattern);
            });
        });
    }

}
