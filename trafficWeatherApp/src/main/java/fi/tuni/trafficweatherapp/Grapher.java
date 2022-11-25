/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.util.*;



/**
 *
 * @author Arttu
 */
public class Grapher extends Application {
    
    public  Grapher () {
    
    }
     
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        primaryStage.setTitle("Grapher Test");
        
        // axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        // label
        xAxis.setLabel("Time (hr)");
        yAxis.setLabel("Temperature (celcius)");
        
        //creating the chart
        final LineChart<Number,Number> lineChart = 
            new LineChart<>(xAxis,yAxis);
        
        lineChart.setTitle("Weather graph");
        
        XYChart.Series series = new XYChart.Series();
        series.setName("Weather datapoints");
        
        // populate series with weather data
        // access DataInterface.java (forecast for now)
        //JSONObject results = WeatherDataApiFetcher.getForecastData("23.78712", "61.49911", "30");
        //JsonParsing.parseXml(results);
        //System.out.println("datainterfacetest: " + DataInterface.getForecastTemperature());
        List<Float> dataSet = DataInterface.getForecastTemperature();
        
        // !-- testset --!
        List<Float> dummySet = new ArrayList() {{
           add(23.5);
           add(20.4);
           add(15.2);
           add(20.3);
           add(26.1);
        }};
        for (int i = 0; i < dataSet.size(); i++) {
            int j = i+1;
            series.getData().add(new XYChart.Data(j*.5, dataSet.get(i)));
            
            System.out.println("i: " + i + " dummySetVal: " + dataSet.get(i));
        }
        
        
        // prepare scene & show
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
       
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        /*JSONObject results = WeatherDataApiFetcher.getForecastData("23.78712", "61.49911", "30");
        JsonParsing.parseXml(results);
        System.out.println("datainterfacetest: " + DataInterface.getForecastTemperature());*/
        //beginnings of temp graph implementation
        launch(args);
        
        // errors running javafx through maven ide
    }
    
}
