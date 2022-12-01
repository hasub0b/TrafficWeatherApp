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
    
    // Most probably will end up not being used
    // Drawers will need to be called from a higher class
    /*
    public static void update() throws Exception {
        try {
            GraphDrawerFactory gdf = new GraphDrawerFactory();
            Double[] coordinates = DataInterface.getCoordinates();
            Double[] plotTemps;
            if (DataInterface.isObservationSelected()) {
                plotTemps = new Double[]{DataInterface.getTemperature()};
            }
            else {
                plotTemps = listFloatToDoubleArray(DataInterface.getForecastTemperature());
            }
            System.out.println("What coordinatesmenu returns: " 
            + testValues[0] +" "+ testValues[1] +" "+ testValues[2] +" "+ testValues[3]);
        
            
            if (DataInterface.temperatureSelected) {
                gdf.createPlot(plotTemps);
            }
            
            if (plotTemps != null) {
                //gdf.createPlot(plotTemps);
            }

            if (DataInterface.cloudSelected) {
                createHistogram();
                createIcons();
            }
            //createPlot();
            
            //if (DataInterface.cloudSelected) {
            //gdf.createHistogram();  
            
        } catch (Exception e) {
            System.out.println("Error during update: \n" + e);
        }
    }
    */
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
    public static XYChart.Series createPlot() throws Exception {
        try {
            Double[] dataset = null;
            Double temp = DataInterface.getTemperature();
            Double[] foreTemp = listFloatToDoubleArray(DataInterface.getForecastTemperature());
            System.out.println("Plotting");
            System.out.println("Forecast: " + !(DataInterface.isObservationSelected()) + " | " 
                    + "Observation: " + DataInterface.isObservationSelected());
            // Checks for null/short arrays and values
            if (temp != null) {
                dataset = new Double[]{temp};
                System.out.println("Obs Data fetched[0]: " + dataset[0]);
            }
            else if (foreTemp.length > 0) {
                dataset = foreTemp;
                System.out.println("Fore Data fetched[0]: " + dataset[0]);
            }
            else {
                System.out.println("No values to fetch!");
            }
            
            PlotDrawer plotterTest = null;
            // If we receive data, then it can be utilized in chart,
            // else it will draw from template
            if (dataset == null) {
                //plotterTest = new PlotDrawer(new Double[]{2.0,4.0,1.0,2.7},1);
                plotterTest = new PlotDrawer(new Double[]{0.0},1);
                return plotterTest.getChart();
                
            }
            else {
                System.out.println("Plot Dataset[0]: " + dataset[0]);
                plotterTest = new PlotDrawer(dataset,1);
            }
            
            return plotterTest.getChart();
            
        }
        catch (Exception e) {
            System.out.println("Error creating plot: " + e);
        }
        return null;
    }
    
    
    // HistogramDrawer
    // * returns histogram
    public static XYChart.Series createHistogram() throws Exception {
        try {

            Float rain = null;
            List<Float> forecastRain = null;
            HistogramDrawer hd = null;
            
            if (DataInterface.isObservationSelected()) {
                
                // Rain (obs)
                if (DataInterface.getRain() != null) {
                    rain = DataInterface.getRain().floatValue();
                }
                else {
                    System.out.println("Couldn't fetch rain");
                }                
            }
            else {
                // Rain (fore)
                if (DataInterface.getForecastRain().size() > 0) {
                    forecastRain = DataInterface.getForecastRain();
                }
                /*else {
                    forecastRain = Arrays.asList(20f,40f,50f,60f);
                }*/
            }
            
            // Make checks for what data to display and what not to display
            // Make unselected booleans 0 in value (?)
            if (!(DataInterface.isRainSelected())) {
                forecastRain = Arrays.asList(0f);
                rain = 0f;
            }

            // Observation
            if (DataInterface.isObservationSelected()) {
                hd = new HistogramDrawer(Arrays.asList(rain), 1);
            }
            // Forecast
            else if (!(DataInterface.isObservationSelected())) {
                hd = new HistogramDrawer(forecastRain, 1);
            }
            
            // Template for testing purposes
            //hd = new HistogramDrawer(Arrays.asList(20f,40f,50f,60f), 1);
            return hd.getChart();
        }
        catch (Exception e) {
            System.out.println("Error creating histogram: " + e);
        }
        return null;
    }
    
    // IconsDrawer
    // * returns icons
    public static XYChart.Series createIcons() throws Exception {
        try {
            Float wind = null;
            Float cloud = null;
            List<Float> forecastWind = null;
            IconsDrawer id = null;
            
            
            if (DataInterface.isObservationSelected()) {
                // Cloud (obs)
                if (DataInterface.getCloud() != null) {
                    cloud = DataInterface.getCloud().floatValue();
                    
                }
                else {
                    System.out.println("Couldn't fetch cloud");
                }
                
                // Wind (obs)
                if (DataInterface.getWind() != null) {
                    wind = Float.valueOf(DataInterface.getWind()); 
                }
                else {
                    System.out.println("Couldn't fetch wind");
                }
                
            }
            else {
                // Wind (fore)
                if (DataInterface.getForecastWind().size() > 0) {
                    forecastWind = DataInterface.getForecastWind();
                }
                else {
                    //System.out.println("Couldn't values for wind forecast");
                }
            }
            
            // Make checks for what data to display and what not to display
            // Make unselected booleans 0 in value (?)
            if (!(DataInterface.isWindSelected())) {
                forecastWind = Arrays.asList(0f);
                wind = 0f;
            }
            if (!(DataInterface.isCloudSelected())) {
                cloud = 0f;
            }

            // Observation
            if (DataInterface.isObservationSelected()) {
                id = new IconsDrawer(Arrays.asList(cloud), DataInterface.isCloudSelected(), Arrays.asList(wind), DataInterface.isWindSelected(), 1);
            }
            // Forecast
            // (no cloudiness forecast available)
            else if (!(DataInterface.isObservationSelected())) {
                id = new IconsDrawer(Arrays.asList(0f), false, forecastWind, DataInterface.isWindSelected(), 1);
            }
            
            // ! Template for testing purposes !
            // ---
            //id = new IconsDrawer(Arrays.asList(20f,40f,50f,70f), true, Arrays.asList(20f,40f,50f,60f), true, 1);
            // ---
            return id.getIcons();
        }
        catch (Exception e) {
            System.out.println("Error creating icons: " + e);
        }
        return null;
    }
    
    // Road / traffic msgs
    // * returns trafi info
    public static Text createMessages() {
        try {
            TrafficMessagesDrawer tmd = new TrafficMessagesDrawer();
            /*
            *   Test
            *   tmd.test();
            */
            
            
            return tmd.getRoadConditionData();
        }
        catch (Exception e) {
            System.out.println("Error creating messages: " + e);
        }
        return null;
    }
    
}
