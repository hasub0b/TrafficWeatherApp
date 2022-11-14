/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import java.util.List;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TextArea;
import org.json.JSONObject;

/**
 *
 * @author Arttu
 */
public class GraphDrawerFactory {
    
    public void fetchWeatherData(String type) throws Exception {
        
        // Get co-ordinates data
        // * x1, x2, y1, y2
        
        // Coordinates can't yet be fetched, as it'll cause issues with GUI
        //Double[] coordinates = CoordinatesMenuController.getCoordinates();
        Double[] coordinates = new Double[]{23.755090615, 23.791861827, 61.491086559, 61.509263332};
        Double x1, x2, y1, y2, x1x2, y1y2;
        x1 = coordinates[0];
        x2 = coordinates[1];
        y1 = coordinates[2];
        y2 = coordinates[3];
        x1x2 = (x1 + x2) / 2;
        y1y2 = (y1 + y2) / 2;
        
        // Forecast
        if (type == "fore") {
            JSONObject results = WeatherDataApiFetcher.getForecastData(x1x2.toString(), y1y2.toString(), "60");
            JsonParsing.parseXml(results);
            System.out.println("WENT");
        }
        // Observation
        else if (type == "obs") {
            
        }
        
        
        
        
        
        //List<Float> dataSet = DataInterface.getForecastTemperature();
        //return dataSet;
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
    
    public LineChart createPlot() throws Exception {
        //PlotDrawer plotter = new PlotDrawer(new Double[] 
        //{2.0, 5.0, 6.0, 7.0, 7.0, 7.5, 6.0, 1.1, 0.1, -0.9,-5.0}, 1);
        fetchWeatherData("fore");
        Double[] dataset = listFloatToDoubleArray(DataInterface.getForecastTemperature());
        PlotDrawer plotterTest = new PlotDrawer(dataset,1);
        //System.out.println("");
        return plotterTest.getChart();
    }
    
    /* HistogramDrawer / IconsDrawer
    public BarChart createHistogram() {
        
    }*/
    
    /*
    public TextArea createMessages() {
    
    }*/
    
    /*
    TBA: icons
    
    }*/
}
