package fi.tuni.trafficweatherapp;

import com.google.gson.JsonObject;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mikko
 */
public class PlotDrawer {
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<Number,Number> chart = new LineChart<>(xAxis, yAxis);
    
    public PlotDrawer(Double[] temperature, int timeInterval) {
        
        yAxis.setLabel("Temperature (C\u00B0)");
        xAxis.setLabel("Time (h)");
        yAxis.setSide(Side.RIGHT);

        XYChart.Series series = new XYChart.Series();
        //series.setName("Graph");
        
        //final LineChart<Number,Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("GraphView");
        
        for (int i = 0; i < temperature.length; ++i) {
            series.getData().add(new XYChart.Data(i * timeInterval, temperature[i]));
        }
        chart.getData().add(series);
        
        //C8B6E2
        
        chart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        chart.lookup(".chart-plot-background").setStyle("-fx-background-color: #C8B6E2;");
        chart.lookup(".chart-vertical-grid-lines").setStyle("-fx-stroke: transparent;");
        chart.lookup(".chart-horizontal-grid-lines").setStyle("-fx-stroke: transparent;");
        chart.setLegendVisible(false);

        
    }
    public LineChart getChart() {
        return chart;
    }
}
