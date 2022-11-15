package fi.tuni.trafficweatherapp;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

public class HistogramDrawer {
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final BarChart<Number,Number> chart = new BarChart<>(xAxis, yAxis);
}
