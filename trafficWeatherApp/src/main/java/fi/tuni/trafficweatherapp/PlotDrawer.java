package fi.tuni.trafficweatherapp;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import javafx.scene.chart.XYChart;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Creates chart data from temperature data.
 *
 * @author Mikko Moisio
 */
public class PlotDrawer {

    XYChart.Series series = new XYChart.Series();

    /**
     * Creates the series data for chart.
     *
     * @param temperature list of temperature data.
     * @param timeInterval of the temperature data.
     */
    public PlotDrawer(Double[] temperature, int timeInterval) {
        
        // Get current time for x axis
        // checks if forecast or observation, different starttime by +1hour.
        // credit: Aleksi for localtime x-axis
        LocalTime now = LocalTime.now();
        if (DataInterface.isObservationSelected()) {
            for (int i = 0; i < temperature.length; ++i) {
                series.getData().add(new XYChart.Data(
                        now.truncatedTo(ChronoUnit.HOURS)
                                .plusHours((long) timeInterval * i).toString(),
                        temperature[i]));
            }
        } else {
            for (int i = 0; i < temperature.length; ++i) {
                series.getData().add(new XYChart.Data(
                        now.truncatedTo(ChronoUnit.HOURS)
                                .plusHours((long) timeInterval * i + 1).toString(),
                        temperature[i]));
            }
        }
    }

    /**
     * Gets the chart data.
     *
     * @return XYChart.Series chart data.
     */
    public XYChart.Series getChart() {
        return series;
    }
}
