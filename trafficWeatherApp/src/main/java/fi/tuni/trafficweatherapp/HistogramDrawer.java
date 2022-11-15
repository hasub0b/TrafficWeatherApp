package fi.tuni.trafficweatherapp;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

    public HistogramDrawer(List<Float> rain, int timeInterval) throws Exception {

        xAxis.setLabel("Time (h)");
        yAxis.setLabel("Precipitation (mm)");
        barChart.setTitle("Precipitation");

        XYChart.Series series = new XYChart.Series();

        // Get current time for x axis
        LocalTime now = LocalTime.now();
        for (int i = 0; i < rain.size(); i++) {
            series.getData().add(new XYChart.Data(now.truncatedTo(ChronoUnit.HOURS).plusMinutes((long) timeInterval * i).toString(), rain.get(i)));
        }


    }
}
