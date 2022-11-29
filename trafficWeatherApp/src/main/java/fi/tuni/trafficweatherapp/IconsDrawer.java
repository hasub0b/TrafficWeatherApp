/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import java.io.File;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Vilma
 */
public class IconsDrawer {
    
    XYChart.Series series = new XYChart.Series();
    
    // halfCloudy.png <a href="https://www.flaticon.com/free-icons/cloud" title="cloud icons">Cloud icons created by Freepik - Flaticon</a>
    // cloudy.png <a href="https://www.flaticon.com/free-icons/clouds" title="clouds icons">Clouds icons created by Freepik - Flaticon</a>
    // sun.png <a href="https://www.flaticon.com/free-icons/sun" title="sun icons">Sun icons created by Freepik - Flaticon</a>
    private final ImageView sunny       = new ImageView(new Image(new File("src/main/resources/fi/tuni/trafficweatherapp/sunny.png").toURI().toString()));
    private final ImageView halfCloudy  = new ImageView(new Image(new File("src/main/resources/fi/tuni/trafficweatherapp/halfCloudy.png").toURI().toString()));
    private final ImageView cloudy      = new ImageView(new Image(new File("src/main/resources/fi/tuni/trafficweatherapp/cloudy.png").toURI().toString()));
    
    
    public IconsDrawer(List<Float> rain, List<Float> cloudiness,
            List<Float> windSpeed, int timeInterval) throws Exception {

        // Get current time for x axis
        LocalTime now = LocalTime.now();
        for (int i = 0; i < rain.size(); i++) {
            XYChart.Data data = new XYChart.Data(
                    now.truncatedTo(ChronoUnit.HOURS).plusHours(
                            (long) timeInterval * i).toString(), 5);
            series.getData().add(data);
            
            double cloudValue = cloudiness.get(i);
            double windValue = windSpeed.get(i);
            ImageView image;
            
            if(cloudValue <= 33.3) {
                image = sunny;
            } else if(cloudValue <= 66.6) {
                image = halfCloudy;
            } else {
                image = cloudy;
            }
            
            Platform.runLater(() -> {
                series.getData().forEach(seriesData -> {
                    
                    StackPane node = (StackPane) data.getNode();
                    // wind.png <a href="https://www.flaticon.com/free-icons/wind" title="wind icons">Wind icons created by Freepik - Flaticon</a>
                    ImageView windPic = new ImageView(new Image(new File("src/main/resources/fi/tuni/trafficweatherapp/wind.png").toURI().toString()));
                    Text windText = new Text(windValue + " m/s");
                    windText.setFont(Font.font(12));
                    
                    //image.fitWidthProperty().bind(node.widthProperty().multiply(.5));
                    //image.fitHeightProperty().bind(node.heightProperty().multiply(.5));
                    image.setFitHeight(30);
                    image.setPreserveRatio(true);
                    
                    windPic.setFitWidth(17);
                    windPic.setPreserveRatio(true);
                    
                    
                    node.getChildren().clear();
                    HBox hbox = new HBox(5, windPic, windText);
                    VBox vbox = new VBox(5, hbox, image);
                    hbox.setAlignment(Pos.BOTTOM_CENTER);
                    node.getChildren().add(vbox);
                    vbox.setAlignment(Pos.BOTTOM_CENTER);
                    node.setAlignment(Pos.BOTTOM_CENTER);
                });
            });
        }
    }

    public XYChart.Series getIcons() {
        return series;
    }
}