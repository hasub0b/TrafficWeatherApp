/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import java.io.File;
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
    
    private final ImageView sunny       = new ImageView(new Image(new File("sunny.png").toURI().toString()));
    private final ImageView halfCloudy  = new ImageView(new Image(new File("halfCloudy.png").toURI().toString()));
    private final ImageView cloudy      = new ImageView(new Image(new File("cloudy.png").toURI().toString()));
    private ImageView image;
    
    public IconsDrawer(double cloudiness) {
        
        if(cloudiness <= 33.3) {
            image = sunny;
        } else if(cloudiness <= 66.6) {
            image = halfCloudy;
        } else {
            image = cloudy;
        }
    }
    
    public ImageView getIcon() {
        return image;
    }
}