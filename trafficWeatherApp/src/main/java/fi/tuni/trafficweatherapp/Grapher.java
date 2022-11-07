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

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import org.json.XML;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 * @author Arttu
 */
public class Grapher extends Application {
    
    
    @Override 
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Grapher Test");
        
        // axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        // label
        xAxis.setLabel("Time (hr)");
        yAxis.setLabel("Temperature (celcius)");
        
        //creating the chart
        final LineChart<Number,Number> lineChart = 
            new LineChart<Number,Number>(xAxis,yAxis);
        
        lineChart.setTitle("Weather graph");
        
        XYChart.Series series = new XYChart.Series();
        series.setName("Weather data items");
        
        // populate series with weather data
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 20));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 20));
        series.getData().add(new XYChart.Data(5, 26));
        
        // prepare scene & show
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
       
        primaryStage.setScene(scene);
        primaryStage.show();
         
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //beginnings of temp graph implementation
        launch(args);
        
        // errors running javafx through maven ide
    }
    
}
