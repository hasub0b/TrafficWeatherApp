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
        
        try {
        
        // Get co-ordinates data
        // * x1, x2, y1, y2
        CoordinatesMenuController coordinatesController = new CoordinatesMenuController();
        Double[] coordinates = coordinatesController.getCoordinates();
        //Double[] coordinates = new Double[]{23.755090615, 23.791861827, 61.491086559, 61.509263332};
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
            //System.out.println(x1x2.toString() +" "+ y1y2.toString());
        }
        // Observation
        else if (type == "obs") {
            JSONObject results = WeatherDataApiFetcher.getObservationData(x1.toString(), x2.toString(), y1.toString(),y2.toString(), "60");
            JsonParsing.parseXml(results);
        }
        
        
        
        
        }catch(Exception e){
            System.out.println("Error: " + e);
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
        fetchWeatherData("fore");
        Double[] dataset = listFloatToDoubleArray(DataInterface.getForecastTemperature());
        PlotDrawer plotterTest = new PlotDrawer(dataset,1);
        System.out.println("test");
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
