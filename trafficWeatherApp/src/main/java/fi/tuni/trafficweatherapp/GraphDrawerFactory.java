/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import org.json.JSONObject;
import javafx.scene.chart.*;

/**
 *
 * @author Arttu
 */
public class GraphDrawerFactory {
    
    // Can be called for refactoring
    // Is called upon, when user input is given or changed
    public void update() throws Exception {
        try {
            // Prepare objects for checking user input booleans (from controllers)
            WeatherMenuController weatherController = new WeatherMenuController();
            CoordinatesMenuController coordinatesController = new CoordinatesMenuController();
            TrafficMenuController trafficController = new TrafficMenuController();
            
            System.out.println("Set coordinate right after(1): " + coordinatesController.getCoordinates()[0]);

            // Weathermenu booleans
            boolean wind, cloud, temp;
            wind = weatherController.getWind();
            cloud = weatherController.getCloud();
            temp = weatherController.getTemp();
            // Trafficmenu booleans (TBA)
            //boolean trafficStatus;


            // Some booleans moved from WeatherMenu to GraphViewController
            // Get Forecast / Observation boolean from here
            GraphViewController viewController = new GraphViewController();
            //boolean twoPressed = viewController.button2h.isPressed();
            //boolean fourPressed = viewController.button4h.isPressed();
            //boolean sixPressed = viewController.button6h.isPressed();
            //boolean twelvePressed = viewController.button12h.isPressed();
            
            boolean forecastPressed = false;
            boolean observationPressed = false;
            if (viewController.buttonForecast != null) {
                forecastPressed = viewController.buttonForecast.isPressed();
            }
            if (viewController.buttonObservation != null) {
                observationPressed = viewController.buttonObservation.isPressed();
            }

            // Old boolean(s)
            /*
            *   boolean isForecast = weatherController.getForecast();
            *   boolean isObservation = weatherController.getObservation();
            */
            System.out.println("updating");
            // --- testing purposes ---
            forecastPressed = true;
            // ------------------------
            // Only if coordinates are set (and/or) can be retreived
            //if (coordinatesController.getCoordinates() != null) {

                // If forecast boolean is active
            if (forecastPressed == true 
                    && observationPressed == false) {
                    // Redraw plot
                createPlot();
                
                    // Redraw ...
                createHistogram();
                    // Redraw ...
                }
                // If observation boolean is active
            else if (forecastPressed == false 
                        && observationPressed == true) {
                    // Redraw plot
                createPlot();
                    
                    // Redraw ...
                createHistogram();  
                    // Redraw ...
            }
            else {
                System.out.println("Update(): No unique boolean value.");
            }
            if (forecastPressed == false 
                    && observationPressed == false) {
                System.out.println("Values of both observation and " +
                        "forecast are either false or null.");
            }

            //createPlot();
            
            // CALL GraphViewController's initialize() -method 
            // to account the updated createPlot() value
            viewController.initialize();
        }
        catch (Error e) {
            System.out.println("Error @ Update(): \n" + e);
        }
    }
    
    public boolean isForecast() {
        GraphViewController viewController = new GraphViewController();
        boolean forecastPressed = false;
        if (viewController.buttonForecast != null 
                && viewController.buttonForecast.isPressed() == true) {
            forecastPressed = true;
        }
        return forecastPressed;
    }
    
    public boolean isObservation() {
        GraphViewController viewController = new GraphViewController();
        boolean observationPressed = false;
        if (viewController.buttonObservation != null 
                && viewController.buttonObservation.isPressed() == true) {
            observationPressed = true;
        }
        return observationPressed;
    }
    
    public void fetchTrafficData() {
        try {
            // Get co-ordinates data
            // * x1, x2, y1, y2
            CoordinatesMenuController coordinatesController = new CoordinatesMenuController();
            Double[] coordinates = coordinatesController.getCoordinates();
            String type = null;
            // Test coordinates
            //Double[] coordinates = new Double[]{23.755090615, 23.791861827, 61.491086559, 61.509263332};
            Double x1, x2, y1, y2, x1x2, y1y2;
            x1 = coordinates[0];
            x2 = coordinates[1];
            y1 = coordinates[2];
            y2 = coordinates[3];
            String starttime, endtime, taskname;
            starttime = "";
            endtime = "";
            taskname = "";
            
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
            JsonObject trafficMessages = RoadDataApiFetcher.getLatestTrafficMessages(type);
           
            
        }
        catch (Exception e) {
            System.out.println("fetchTrafficData() Error: " + e);
        }
    }
    
    public void fetchWeatherData(String type) throws Exception {
        
        try {
            // Get co-ordinates data
            // * x1, x2, y1, y2
            CoordinatesMenuController coordinatesController = new CoordinatesMenuController();
            WeatherMenuController weatherController = new WeatherMenuController();
            System.out.println("Check for Weather Boolean Temp: " + weatherController.getTemp());
            Double[] coordinates = coordinatesController.getCoordinates();
            System.out.println("fetchWatherdata(): coordinates: " + coordinates);
            // Test coordinates
            //Double[] coordinates = new Double[]{23.755090615, 23.791861827, 61.491086559, 61.509263332};
            Double x1, x2, y1, y2, x1x2, y1y2;
            x1 = coordinates[0];
            x2 = coordinates[1];
            y1 = coordinates[2];
            y2 = coordinates[3];
            x1x2 = (x1 + x2) / 2;
            y1y2 = (y1 + y2) / 2;
        
            System.out.println("enpasse");
            
            // Forecast
            if (type == "forecast") {
                JSONObject results = WeatherDataApiFetcher.getForecastData(x1x2.toString(), 
                        y1y2.toString(), "30");
                JsonParsing.parseXml(results);
                System.out.println("forecasting");
            }
            // Observation
            else if (type == "observation") {
                JSONObject results = 
                        WeatherDataApiFetcher.getObservationData(x1.toString(), 
                                x2.toString(), y1.toString(),
                                y2.toString(), "30");
                JsonParsing.parseXml(results);
                System.out.println("observing");
            }
        }
            catch(Exception e)
        {
            System.out.println("fetchWeatherData() Error: " + e);
        }
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
                fetchWeatherData("forecast");
                dataset = listFloatToDoubleArray(DataInterface.getForecastTemperature());
            }
            else if (isObservation()) {
                fetchWeatherData("observation");
                dataset = new Double[]{DataInterface.getTemperature()};
            }
            else {
                System.out.println("Error with radio booleans.");
            }

            dataset = listFloatToDoubleArray(DataInterface.getForecastTemperature());
            PlotDrawer plotterTest = new PlotDrawer(dataset,1);
            //PlotDrawer plotterTest = new PlotDrawer(new Double[]{2.0,4.0,1.0,2.7},1);
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
                fetchWeatherData("forecast");
            }
            // If it's an observation
            else if (isObservation()) {
                fetchWeatherData("observation");
                cloudiness.add(DataInterface.getCloud().floatValue());
            }
            else {
                System.out.println("Error with radio booleans.");
            }
            //HistogramDrawer barTest = new HistogramDrawer(rain, cloudiness, timeInterval);
            HistogramDrawer hd = new HistogramDrawer(Arrays.asList(20f,40f,50f,60f), Arrays.asList(20f,40f,10f,20f), 1);
            //return barTest.getChart();
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
    
    
    // TBA: icons (method type TBD)
    public TextArea createIcons() {
        return null;
    }
    
}
