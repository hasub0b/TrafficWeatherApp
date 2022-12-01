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
import fi.tuni.trafficweatherapp.drawers.*;
import fi.tuni.trafficweatherapp.controllers.*;


/**
 *
 * @author Arttu
 */
public class GraphDrawerFactory {
    
    public int timeWindow() {
        int timeWindow = 0;
        //System.out.println("Fetched forecast (time): " + DataInterface.getSelectedForecast());
        if (DataInterface.getSelectedForecast() != "") {
            if (DataInterface.getSelectedForecast().toString().contains("12h")) {
                timeWindow = 12;
            }
            else if (DataInterface.getSelectedForecast().toString().contains("4h")) {
                timeWindow = 4;
            }
            else if (DataInterface.getSelectedForecast().toString().contains("6h")) {
                timeWindow = 6;
            }
            else if (DataInterface.getSelectedForecast().toString().contains("2h")) {
                timeWindow = 2;
            }
            else {
                System.out.println("Couldn't find selected forecast");
            }
            //timeWindow = Integer.parseInt(DataInterface.getSelectedForecast());
        }
        else {
            // For now it'll return 2 as the expected time window,
            // if there's no selected forecast hour value
            timeWindow = 2;
        }
        //System.out.println("Forecast time: " + timeWindow);
        return timeWindow;
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
            Double temp = DataInterface.getTemperature();
            Double[] foreTemp = listFloatToDoubleArray(DataInterface.getForecastTemperature());
            // Resized dataset to take into account for forecast hours
            Double[] resizedDataset = new Double[timeWindow()];
            
            System.out.println("Plotting");
            System.out.println("Forecast: " + !(DataInterface.isObservationSelected()) + " | " 
                    + "Observation: " + DataInterface.isObservationSelected());
            // Checks for null/short arrays and values
            if (temp != null && DataInterface.isObservationSelected()) {
                dataset = new Double[]{temp};
                System.out.println("Obs Data fetched[0]+[n-1]: [" + dataset[0] + "]+[" + dataset[dataset.length-1]+"]");
            }
            else if (foreTemp.length > 0 && !(DataInterface.isObservationSelected())) {
                dataset = foreTemp;
                System.out.println("Fore Data fetched[0]+[n-1]: [" + dataset[0] + "]+[" + dataset[dataset.length-1]+"]");
            }
            else {
                System.out.println("No values to fetch!");
            }
            
            // Limit the dataset size according to forecast hour
            if (!(DataInterface.isObservationSelected())) {
                for (int i = 0; i < timeWindow(); i++) {
                    resizedDataset[i] = dataset[i];
                    System.out.println("timewindow: " + timeWindow());
                    System.out.println("array length: " + resizedDataset.length);
                }
            }
            else {
                //System.out.println("Didn't resize temp array");
            }
            
            PlotDrawer plotterTest = null;
            // If we receive data, then it can be utilized in chart,
            // else it will draw from template
            if (dataset == null) {
                //plotterTest = new PlotDrawer(new Double[]{2.0,4.0,1.0,2.7},1);
                plotterTest = new PlotDrawer(new Double[]{0.0},1);
                System.out.println("Dataset null");
                return plotterTest.getChart();               
            }
            // If not null
            else {
                //System.out.println("Plot Dataset[0]: " + dataset[0]);
                plotterTest = new PlotDrawer(resizedDataset,1); 
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
    public XYChart.Series createHistogram() throws Exception {
        try {

            Float rain = null;
            List<Float> forecastRain = null;
            HistogramDrawer hd = null;
            // Resized dataset to take into account for forecast hours
            List<Float> resizedDataset = new ArrayList<>(timeWindow());
            
            if (DataInterface.isObservationSelected()) {
                
                // Rain (obs)
                if (DataInterface.getRain() != null) {
                    rain = DataInterface.getRain().floatValue();
                }
                else {
                    System.out.println("Couldn't fetch rain");
                }                
            }
            else if (!(DataInterface.isObservationSelected())) {
                // Rain (fore)
                if (DataInterface.getForecastRain().size() > 0) {
                    // Iterates and resizes the data to fit wanted time window
                    forecastRain = DataInterface.getForecastRain();
                    System.out.println("Timewindow: "+ timeWindow());
                    for (int i = 0; i < timeWindow(); i++) {
                        resizedDataset.add(forecastRain.get(i));
                    }
                    forecastRain = resizedDataset;
                }
                else {
                    System.out.println("Didn't resize rain array");
                }
                /*else {
                    forecastRain = Arrays.asList(20f,40f,50f,60f);
                }*/
            }
            else {
                System.out.println("Didn't fetch raindata.");
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
    public XYChart.Series createIcons() throws Exception {
        try {
            Float wind = null;
            Float cloud = null;
            List<Float> forecastWind = null;
            IconsDrawer id = null;
            // Resized dataset to take into account for forecast hours
            List<Float> resizedDataset = new ArrayList<>(timeWindow());
            
            
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
                    for (int i = 0; i < timeWindow(); i++) {
                        resizedDataset.add(forecastWind.get(i));
                    }
                    forecastWind = resizedDataset;
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
    public Text createMessages() {
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
