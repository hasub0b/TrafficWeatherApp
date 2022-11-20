package fi.tuni.trafficweatherapp;

import com.google.gson.JsonObject;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
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

    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);

    public PlotDrawer(Double[] temperature, int timeInterval) {

        xAxis.getStylesheets().add(getClass()
                .getResource("chartStackedStyle.css").toExternalForm());
        yAxis.getStylesheets().add(getClass()
                .getResource("chartStackedStyle.css").toExternalForm());

        yAxis.setLabel("Temperature (C\u00B0)");
        xAxis.setLabel("Local time");
        yAxis.setSide(Side.RIGHT);

        XYChart.Series series = new XYChart.Series();
        //chart.setTitle("GraphView");

        // Get current time for x axis
        // credit: Aleksi
        LocalTime now = LocalTime.now();
        for (int i = 0; i < temperature.length; ++i) {
            series.getData().add(new XYChart.Data(
                    now.truncatedTo(ChronoUnit.HOURS)
                            .plusHours((long) timeInterval * i).toString(),
                    temperature[i]));
        }
        chart.getData().add(series);

        //chart.lookup(".chart-plot-background").setStyle("-fx-background-color: #C8B6E2;");
        chart.getStylesheets().addAll(getClass().getResource("chartStackedStyle.css").toExternalForm());
    }

    public LineChart getChart() {
        return chart;
    }
}
