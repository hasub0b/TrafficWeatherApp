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
 * @author Arttu Lehtola
 */
public class GraphDrawerFactory {
    /**
     * Is used for checking forecast time values
     * @return returns selected forecast timewindow 2h, 4h, 6h or 12h
     */
    public int timeWindow() {
        int timeWindow = 0;
        if (!DataInterface.isObservationSelected()) {
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
        }
        else {
            // For now it'll return 2 as the expected time window,
            // if there's no selected forecast hour value
            timeWindow = 1;
        }
        //System.out.println("Forecast time: " + timeWindow);
        return timeWindow;
    }

    /**
    * Used for float list's onversion to Double[].
    * @param input for the convertable List<Float>
    * @return returns converted series of values or null, if the output was null
    */
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
    
    /**
    * Calls the PlotDrawer to draw a linechart with DataInterface data, 
    * then returns it to GraphViewController for use. 
    * The method has basic logic applied to it.
    * @return returns either a populated series or a null value
    * @throws Exception Exception, if there's problem with type or data indexing
    */
    public XYChart.Series createPlot() throws Exception {
        try {
            
            Double[] dataset = null;
            Double temp = DataInterface.getTemperature();
            Double[] foreTemp = new Double[DataInterface.getForecastTemperature().size()];
            for (int i = 0; i < foreTemp.length; i++) {
                foreTemp[i] = DataInterface.getForecastTemperature().get(i);  // java 1.4 style
                // or:
                foreTemp[i] = DataInterface.getForecastTemperature().get(i);                // java 1.5+ style (outboxing)
            }
            // Resized dataset to take into account for forecast hours
            Double[] resizedDataset = new Double[timeWindow()];

            // Checks for null/short arrays and values
            if (temp != null && DataInterface.isObservationSelected()) {
                dataset = new Double[]{temp};
                //System.out.println("Obs Data fetched[0]+[n-1]: [" + dataset[0] + "]+[" + dataset[dataset.length-1]+"]");
            }
            else if (foreTemp.length > 0 && !(DataInterface.isObservationSelected())) {
                dataset = foreTemp;
                //System.out.println("Fore Data fetched[0]+[n-1]: [" + dataset[0] + "]+[" + dataset[dataset.length-1]+"]");
            }
            else {
                System.out.println("No values to fetch!");
            }

            for (int i = 0; i < timeWindow(); i++) {
                resizedDataset[i] = dataset[i];
            }
            
            PlotDrawer plotterTest = null;
            // If we receive data, then it can be utilized in chart,
            // else it will draw from template
            if (dataset == null) {
                plotterTest = new PlotDrawer(new Double[]{0.0},1);
                System.out.println("Dataset null");
                return plotterTest.getChart();               
            }
            // If not null
            else {
                plotterTest = new PlotDrawer(resizedDataset,1); 
            }
            
            return plotterTest.getChart();
        }
        catch (Exception e) {
            System.out.println("Error creating plot: " + e);
            return null;
        }
    }
    
    
    /**
    * Calls the HistogramDrawer to draw a histogram, then returns it to
    * GraphViewController for use. The method has basic logic applied to it.
    * @return           returns either a populated series or a null value
    * @throws Exception Exception, if there's problem with type or data indexing
    */
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
                    rain = Float.valueOf(DataInterface.getRain().toString());
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
                    for (int i = 0; i < timeWindow(); i++) {
                        resizedDataset.add(forecastRain.get(i));
                    }
                    forecastRain = resizedDataset;
                }
                else {
                    System.out.println("Didn't resize rain array");
                }

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
            else {
                rain = DataInterface.getRain().floatValue();
            }

            // Observation
            if (DataInterface.isObservationSelected()) {
                hd = new HistogramDrawer(Arrays.asList(rain), 1);
            }
            // Forecast
            else if (!(DataInterface.isObservationSelected())) {
                hd = new HistogramDrawer(forecastRain, 1);
            }
            
            return hd.getChart();
        }
        catch (Exception e) {
            System.out.println("Error creating histogram: " + e);
            return null;
        }
    }
    
    /**
     * Calls the HistogramDrawer to draw a histogram, then returns it to
     * GraphViewController for use. The method has basic logic applied to it.       
     * @return returns either a populated series or a null value
     * @throws Exception Exception, if there's problem with type or data indexing
     */
    public XYChart.Series createIcons() throws Exception {
        try {
            Float wind = null;
            Float cloud = null;
            List<Float> forecastWind = null;
            List<Float> forecastCloud = null;
            IconsDrawer id = null;
            // Resized dataset to take into account the dynamic forecast hours
            List<Float> resizedDatasetWind = new ArrayList<>(timeWindow());
            List<Float> resizedDatasetCloud = new ArrayList<>(timeWindow());
            
            
            if (DataInterface.isObservationSelected()) {
                // Cloud (obs)
                if (DataInterface.getCloud() != null) {
                    cloud = Float.valueOf(DataInterface.getCloud().toString());
                }
                else {
                    System.out.println("Couldn't fetch cloud!");
                }
                
                // Wind (obs)
                if (DataInterface.getWind() != null) {
                    wind = Float.valueOf(DataInterface.getWind());
                }
                else {
                    System.out.println("Couldn't fetch wind!");
                }
                
            }
            else {
                // Wind (fore)
                if (DataInterface.getForecastWind().size() > 0) {
                    //System.out.println("Fetched forecastwind: " + DataInterface.getForecastWind());
                    forecastWind = DataInterface.getForecastWind();
                    
                    for (int i = 0; i < timeWindow(); i++) {
                        resizedDatasetWind.add(forecastWind.get(i))/*.toString())*/;

                    }
                    forecastWind = resizedDatasetWind;
                }
                else {
                    System.out.println("Couldn't values for wind forecast!");
                }
                
                // Cloud (fore)
                if (DataInterface.getForecastCloud().size() > 0) {
                    //System.out.println("Fetched forecastcloud: " + DataInterface.getForecastCloud());
                    forecastCloud = DataInterface.getForecastCloud();
                    for (int i = 0; i < timeWindow(); i++) {
                        resizedDatasetCloud.add(forecastCloud.get(i));
                    }
                    forecastCloud = resizedDatasetCloud;
                }
                else {
                    System.out.println("Couldn't values for cloud forecast!");
                }
            }
            
            // Observation
            if (DataInterface.isObservationSelected()) {
                id = new IconsDrawer(Arrays.asList(cloud), DataInterface.isCloudSelected(), Arrays.asList(wind), DataInterface.isWindSelected(), 1);
            }
            // Forecast
            // (no cloudiness forecast available)
            else if (!(DataInterface.isObservationSelected())) {
                id = new IconsDrawer(forecastCloud, DataInterface.isCloudSelected(), forecastWind, DataInterface.isWindSelected(), 1);
            }
            
            return id.getIcons();
        }
        catch (Exception e) {
            System.out.println("Error creating icons: " + e);
            return null;
        }
    }
   
}
