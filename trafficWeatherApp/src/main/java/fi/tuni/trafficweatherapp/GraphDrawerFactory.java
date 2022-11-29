/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 *
 * @author Arttu
 */
public class GraphDrawerFactory {
    
    public void update() {
        try {
            if (DataInterface.temperatureSelected) {
                createPlot();
            }
            if (DataInterface.cloudSelected) {
                createHistogram();
            }
            
        } catch (Exception e) {
            System.out.println("Error during update: \n" + e);
        }
    }
    
    public boolean isForecast() {
        boolean forecastPressed = false;
        if (!(DataInterface.isObservationSelected()) == true &&
                DataInterface.getForecastTemperature() != null) {
            forecastPressed = true;
        }
        return forecastPressed;
    }
    
    public boolean isObservation() {
        boolean observationPressed = false;
        if (DataInterface.isObservationSelected() == true) {
            observationPressed = true;
        }
        return observationPressed;
    }

    // Fetches data from DataInterface
    public List<Float> fetchData(String para1, String para2) throws Exception {
        // Old Objects
        //CoordinatesMenuController coordinatesController = new CoordinatesMenuController();
        //TrafficMenuController trafficController = new TrafficMenuController();
        //WeatherMenuController weatherController = new WeatherMenuController();
        
        // Prepare return value
        List<Float> dataset = null;
        
        // Object
        //DataInterface data = new DataInterface();
        
        // Variables
        Double x1, x2, y1, y2, x1x2, y1y2;
        // Double[] coordinates = coordinatesController.getCoordinates(); 
        // -> make it fetch from datainterface
        
        // Test coordinates
        Double[] coordinates = new Double[]{23.755090615, 23.791861827, 61.491086559, 61.509263332};
        x1 = coordinates[0];
        x2 = coordinates[1];
        y1 = coordinates[2];
        y2 = coordinates[3];
        x1x2 = (x1 + x2) / 2;
        y1y2 = (y1 + y2) / 2;
        
        if (para1 == "temp") {
            
            if (para2 == "obs") {
                System.out.println("Observing!!");
                dataset = DataInterface.getForecastTemperature();
            }
            else if (para2 == "fore") {
                System.out.println("Forecasting!!");
                dataset.add(DataInterface.getTemperature().floatValue());
            }
        }
        else if (para1 == "cloud") {
            if (para2 == "obs") {
                dataset.add(DataInterface.getCloud().floatValue());
            }
        }
        else if (para1 == "rain") {
            if (para2 == "obs") {
                dataset.add(DataInterface.getRain().floatValue());
            }
        }
        
        if (dataset == null) {
            System.out.println("Sadly we couldn't fetch data succesfully.");
            return null;
        }
        
        /* Segment by:
        * para1
        * >temp(fmi)/cloud(fmi)/wind(fmi) || TBA: /maintanancemap(dt)/messages(dt)
        * para2:
        * >forecast/observation
        */
     
    return dataset;
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
            List<Float> fetchset = null;
            if (isForecast()) {
                if (DataInterface.temperatureSelected) {
                    fetchset = fetchData("temp", "fore");
                    dataset = listFloatToDoubleArray(fetchset);
                }
                
            }
            else if (isObservation()) {
                //DataInterface.getTemperature();
                fetchset = fetchData("temp", "obs");
                // Get the first (and only element from List<Float>)
                Float fetchValue = fetchset.get(0);
                // Convert and set it as current dataset
                dataset = new Double[]{fetchValue.doubleValue()};
            }
            else {
                System.out.println("Error with booleans.");
            }

            //dataset = listFloatToDoubleArray(DataInterface.getForecastTemperature());
            //PlotDrawer plotterTest = new PlotDrawer(dataset,1);
            System.out.println("Plot Dataset: " + dataset);
            PlotDrawer plotterTest = new PlotDrawer(new Double[]{2.0,4.0,1.0,2.7},1);
            //PlotDrawer plotterTest = new PlotDrawer(dataset,1);
            //System.out.println("test");
            
            // Test
            //TrafficMessagesDrawer testMessageDrawer = new TrafficMessagesDrawer();
            //testMessageDrawer.test();
            
            return plotterTest.getChart();
            
        }
        catch (Exception e) {
            System.out.println("Error creating plot: " + e);
        }
        return null;
    }
    
    
    // HistogramDrawer / IconsDrawer
    // * returns histogram
    public XYChart.Series createHistogram() throws Exception {
        try {
            List<Float> cloudiness = null;
            List<Float> rain = null;
            List<Float> dataset = null;
            int timeInterval = 30;
            
            // If it's a forecast
            if (isObservation()) {
                dataset = fetchData("weather", "forecast");
            }
            
            // If it's an observation
            else if (isForecast()) {
                System.out.println("Sadly no forecast values for histogram.");
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
            System.out.println("Error creating histogram: " + e);
        }
        return null;
    }
    
    // Road / traffic msgs
    // * returns trafi info
    public Text createMessages() {
        try {
            TrafficMessagesDrawer tmd = new TrafficMessagesDrawer();
            /*
            *   Test
            *   tmd.test();
            */
            
            
            return tmd.getText();
        }
        catch (Exception e) {
            System.out.println("Error creating messages: " + e);
        }
        return null;
    }
    
}
