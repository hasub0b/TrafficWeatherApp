/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Vilma
 */
public class IconsDrawer {

    // halfCloudy.png <a href="https://www.flaticon.com/free-icons/cloud" title="cloud icons">Cloud icons created by Freepik - Flaticon</a>
    // cloudy.png <a href="https://www.flaticon.com/free-icons/clouds" title="clouds icons">Clouds icons created by Freepik - Flaticon</a>
    // sun.png <a href="https://www.flaticon.com/free-icons/sun" title="sun icons">Sun icons created by Freepik - Flaticon</a>
    
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final BarChart<Number, Number> iconChart = new BarChart<>(xAxis, yAxis);
    private final ImageView sunny       = new ImageView(new Image("sunny.png"));
    private final ImageView halfCloudy  = new ImageView(new Image("halfCloudy.png"));
    private final ImageView cloudy      = new ImageView(new Image("cloudy.png"));
    
    public IconsDrawer(Double[] cloudiness, int timeInterval) {
        
        xAxis.setLabel("Time (h)");
        
        XYChart.Series series = new XYChart.Series();
        
        for (int i = 0; i < cloudiness.length; ++i) {
            if(cloudiness[i] <= 33.3) {
                series.getData().add(new XYChart.Data(i * timeInterval, sunny));
            } else if(cloudiness[i] <= 66.6) {
                series.getData().add(new XYChart.Data(i * timeInterval, halfCloudy));
            } else {
                series.getData().add(new XYChart.Data(i * timeInterval, cloudy));
            }
        }
        iconChart.getData().add(series);
        
        iconChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
    }
    
    public BarChart getChart() {
        return iconChart;
    }
}
