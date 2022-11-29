/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import java.util.Arrays;
import java.util.List;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;

/**
 *
 * @author Arttu
 */
public class GraphDrawerFactory {
    
    public boolean isForecast() {
        DataInterface data = new DataInterface();
        boolean forecastPressed = false;
        if (!(data.isObservationSelected()) == true) {
            forecastPressed = true;
        }
        return forecastPressed;
    }
    
    public boolean isObservation() {
        DataInterface data = new DataInterface();
        boolean observationPressed = false;
        if (data.isObservationSelected() == true) {
            observationPressed = true;
        }
        return observationPressed;
    }
    
    // Fetches most recent data into (JsonParsing, then) DataInterface 
    public void fetchData(String para1, String para2) throws Exception {
        // Objects
        CoordinatesMenuController coordinatesController = new CoordinatesMenuController();
        TrafficMenuController trafficController = new TrafficMenuController();
        WeatherMenuController weatherController = new WeatherMenuController();
        // Variables
        Double x1, x2, y1, y2, x1x2, y1y2;
        Double[] coordinates = coordinatesController.getCoordinates();
        // Test coordinates
        //Double[] coordinates = new Double[]{23.755090615, 23.791861827, 61.491086559, 61.509263332};
        x1 = coordinates[0];
        x2 = coordinates[1];
        y1 = coordinates[2];
        y2 = coordinates[3];
        x1x2 = (x1 + x2) / 2;
        y1y2 = (y1 + y2) / 2;
        String starttime, endtime, taskname;
        starttime = "";
        endtime = "";
        taskname = "";
        /*
        if (para1 == "traffic") {
            // type: TRAFFIC_ANNOUNCEMENT, EXEMPTED_TRANSPORT, WEIGHT_RESTRICTION or ROAD_WORK.
            String type = null;
            RoadDataApiFetcher.getRoadConditions(x1.toString(), 
                    y1.toString(), x2.toString(), y2.toString());
            
            // api --> parser
            // getRoadConditions() --> parseRoadConditions()
            JsonObject roadData = RoadDataApiFetcher.getRoadConditions(x1.toString(), 
                    y1.toString(), x2.toString(), y2.toString());
            // getRoadMaintenanceData() --> parseTasks()
            JsonObject maintenanceData = RoadDataApiFetcher.getRoadMaintenanceData(starttime, endtime, x1.toString(), 
                    y1.toString(), x2.toString(), y2.toString(), taskname);
            // getLatestTrafficMessages() --> parseTrafficData()
            // type: TRAFFIC_ANNOUNCEMENT, EXEMPTED_TRANSPORT, WEIGHT_RESTRICTION or ROAD_WORK.
            //JsonObject trafficMessages = RoadDataApiFetcher.getLatestTrafficMessages(type);
        }
        // Forecast
        else if (para1 == "weather" && para2 == "forecast") {
                WeatherDataApiFetcher.getForecastData(
                        x1x2.toString(), y1y2.toString(), "30");
                System.out.println("forecasting");
        }
        // Observation
        else if (para1 == "weather" && para2 == "observation") {
                WeatherDataApiFetcher.getObservationData(x1.toString(), 
                                x2.toString(), y1.toString(),
                                y2.toString(), "30");
                
                System.out.println("observing");
        }
        else {
            System.out.println("Error with parameters");
        }*/
        
        
        
    }

    public static Double[] listFloatToDoubleArray(List<Float> input) {
        if (input == null) {
            return null;
        }
        // First convert float into array
        Double[] inputArray = new Double[input.size()];
        for (int j = 0; j < input.size(); j++) inputArray[j] = Double.valueOf(input.get(j));

        // Then convert float to double
        Double[] output = new Double[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            output[i] = inputArray[i];
        }
        return output;
    }
    
    // Linechart
    // * returns linechart
    public XYChart.Series createPlot() throws Exception {
        try {
            Double[] dataset = null;
            if (isForecast()) {
                fetchData("weather", "forecast");
                dataset = listFloatToDoubleArray(DataInterface.getForecastTemperature());
            }
            else if (isObservation()) {
                fetchData("weather", "observation");
                dataset = new Double[]{DataInterface.getTemperature()};
            }
            else {
                System.out.println("Error with radio booleans.");
            }

            dataset = listFloatToDoubleArray(DataInterface.getForecastTemperature());
            //PlotDrawer plotterTest = new PlotDrawer(dataset,1);
            PlotDrawer plotterTest = new PlotDrawer(new Double[]{2.0,4.0,1.0,2.7},1);
            //System.out.println("test");
            return plotterTest.getChart();
            
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
    
    
    // HistogramDrawer / IconsDrawer
    // * returns histogram
    public XYChart.Series createHistogram() throws Exception {
        try {
            List<Float> cloudiness = null;
            List<Float> rain = null;
            int timeInterval = 30;
            // If it's a forecast
            if (isForecast()) {
                // (?) no cloud/rain data in forecast
                // TBD
                fetchData("weather", "forecast");
            }
            // If it's an observation
            else if (isObservation()) {
                fetchData("weather","observation");
                cloudiness.add(DataInterface.getCloud().floatValue());
            }
            else {
                System.out.println("Error with radio booleans.");
            }
            //HistogramDrawer barTest = new HistogramDrawer(rain, cloudiness, timeInterval);
            HistogramDrawer hd = new HistogramDrawer(Arrays.asList(20f,40f,50f,60f), Arrays.asList(20f,40f,10f,20f), Arrays.asList(20f,40f,10f,20f), 1);
            //return barTest.getChart();
            System.out.println("barTest");
            return hd.getChart();
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
    
    // Road / traffic msgs
    // * returns trafi info
    public TextArea createMessages() {
        return null;
    }
    
}
