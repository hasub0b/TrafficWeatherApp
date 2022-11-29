package fi.tuni.trafficweatherapp;

import javafx.scene.chart.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


/**
 * @author Aleksi
 */
public class HistogramDrawer {
    //private final CategoryAxis xAxis = new CategoryAxis();
    //private final NumberAxis yAxis = new NumberAxis();
    //private final BarChart barChart = new BarChart(xAxis, yAxis);
    XYChart.Series series = new XYChart.Series();
    

    /**
     * @param rain getForecastRain list from DataInterface
     * @param timeInterval time interval in minutes, default 30
     * @throws Exception
     */
    public HistogramDrawer(List<Float> rain, int timeInterval) throws Exception {

        // Get current time for x axis
        LocalTime now = LocalTime.now();
        for (int i = 0; i < rain.size(); i++) {
            XYChart.Data data = new XYChart.Data(
                    now.truncatedTo(ChronoUnit.HOURS).plusHours(
                            (long) timeInterval * i).toString(), rain.get(i));
            series.getData().add(data);
        }
        
    }

    public XYChart.Series getChart() {
        return series;
    }
}
