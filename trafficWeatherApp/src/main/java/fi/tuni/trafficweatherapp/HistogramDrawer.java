package fi.tuni.trafficweatherapp;

import javafx.scene.Node;
import javafx.scene.chart.*;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author Aleksi
 */
public class HistogramDrawer {
    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final BarChart barChart = new BarChart(xAxis, yAxis);


    /**
     * @param rain getForecastRain list from DataInterface
     * @param timeInterval time interval in minutes, default 30
     * @throws Exception
     */
    public HistogramDrawer(List<Float> rain, int timeInterval) throws Exception {

        xAxis.setLabel("Time (hh:mm)");
        yAxis.setLabel("Precipitation (mm)");
        barChart.setTitle("Precipitation amount");
        barChart.setBarGap(-4);

        XYChart.Series series = new XYChart.Series();

        // Get current time for x axis
        LocalTime now = LocalTime.now();
        for (int i = 0; i < rain.size(); i++) {
            series.getData().add(new XYChart.Data(now.truncatedTo(ChronoUnit.HOURS).plusMinutes((long) timeInterval * i).toString(), rain.get(i)));
        }

        // Change bar color to blue
        for(Node n:barChart.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: #0077FF;");
        }

        // Uniform look with PlotDrawer
        barChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        barChart.lookup(".chart-plot-background").setStyle("-fx-background-color: #C8B6E2;");
        barChart.lookup(".chart-vertical-grid-lines").setStyle("-fx-stroke: transparent;");
        barChart.lookup(".chart-horizontal-grid-lines").setStyle("-fx-stroke: transparent;");
        barChart.setLegendVisible(false);


    }

    public BarChart getChart() {
        return barChart;
    }
}
