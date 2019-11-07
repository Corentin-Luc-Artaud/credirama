package fr.unice.polytech.si5.al.analystservice.services;

import fr.unice.polytech.si5.al.analystservice.models.timelion.TimelionChart;
import fr.unice.polytech.si5.al.analystservice.models.timelion.TimelionParams;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.unice.polytech.si5.al.analystservice.models.Visualisation;

public class VisualisationServices {
    private static final Logger logger = LogManager.getLogger(VisualisationServices.class);


    public String createTimelion(String title, String expression) throws Exception {
        TimelionParams timelionParams = new TimelionParams(expression);
        TimelionChart timelionChart = new TimelionChart(title, timelionParams);
        Visualisation visualisation = new Visualisation(timelionChart);
        logger.info(visualisation.toString());
        return visualisation.toString();
    }
}
