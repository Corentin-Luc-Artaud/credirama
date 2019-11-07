package fr.unice.polytech.si5.al.analystservice.services;

import fr.unice.polytech.si5.al.analystservice.models.timelion.TimelionChart;
import fr.unice.polytech.si5.al.analystservice.models.timelion.TimelionParams;
import fr.unice.polytech.si5.al.analystservice.models.Visualisation;

public class VisualisationServices {

    String createTimelion(String id, String index, String title, String expression) throws Exception {
        TimelionParams timelionParams = new TimelionParams(expression);
        TimelionChart timelionChart = new TimelionChart(title, timelionParams);
        Visualisation visualisation = new Visualisation(id, timelionChart);
        return visualisation.toString();
    }
}
