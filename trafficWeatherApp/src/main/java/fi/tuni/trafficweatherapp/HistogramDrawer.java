package fi.tuni.trafficweatherapp;

import javafx.scene.chart.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


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
    public HistogramDrawer(List<Float> rain, List<Float> cloudiness,
            List<Float> windSpeed, int timeInterval) throws Exception {

        //xAxis.setLabel("Time (hh:mm)");
        //yAxis.setLabel("Precipitation (mm)");
        //barChart.setTitle("Precipitation amount");
        //barChart.setBarGap(-4);


        // Get current time for x axis
        LocalTime now = LocalTime.now();
        for (int i = 0; i < rain.size(); i++) {
            XYChart.Data data = new XYChart.Data(
                    now.truncatedTo(ChronoUnit.HOURS).plusHours(
                            (long) timeInterval * i).toString(), rain.get(i));
            series.getData().add(data);
            
            // @author Vilma
            
            double cloudValue = cloudiness.get(i);
            double windValue = windSpeed.get(i);
            IconsDrawer icon = new IconsDrawer(cloudValue);
            
            
            Platform.runLater(() -> {
                series.getData().forEach(seriesData -> {
                    StackPane node = (StackPane) data.getNode();
                    VBox vbox = new VBox();
                    node.getChildren().add(vbox);
                    ImageView cloudImage = icon.getIcon();
                    cloudImage.fitWidthProperty().bind(vbox.widthProperty().multiply(.5));
                    cloudImage.setPreserveRatio(true);
                    Text windText = new Text(windValue + " m/s");
                    vbox.getChildren().add(windText);
                    node.getChildren().add(cloudImage);
                });
            });
        }
        
        

        // Change bar color to blue
        /*for(Node n:barChart.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: #0077FF;");
        }*/

        // Uniform look with PlotDrawer
        /*barChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        barChart.lookup(".chart-plot-background").setStyle("-fx-background-color: #C8B6E2;");
        barChart.lookup(".chart-vertical-grid-lines").setStyle("-fx-stroke: transparent;");
        barChart.lookup(".chart-horizontal-grid-lines").setStyle("-fx-stroke: transparent;");
        barChart.setLegendVisible(false);*/
        
        
    }

    public XYChart.Series getChart() {
        return series;
    }
}
