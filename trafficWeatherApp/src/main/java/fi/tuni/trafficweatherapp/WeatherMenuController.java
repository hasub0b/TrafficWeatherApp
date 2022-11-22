package fi.tuni.trafficweatherapp;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;

/**
* @author Arttu
*/

public class WeatherMenuController {

    // Checkbox objects
    @FXML
    CheckBox cloudbox;
    @FXML
    CheckBox windbox;
    @FXML
    CheckBox tempbox;
    /*@FXML
    private RadioButton forecastRadio;
    @FXML
    private RadioButton observationRadio;*/
    
    
    
    // Boolean variables
    private boolean temp;
    private boolean wind;
    private boolean cloud;
    //private boolean observation;
    //private boolean forecast;
    
    /*
    public void initialize() throws Exception {
       // TBA (call to factory's update if needed)
       try {
        GraphViewController viewController = new GraphViewController();
        boolean forecastPressed = false;
        if (viewController.buttonForecast != null 
                && viewController.buttonForecast.isPressed() == true) {
            forecastPressed = true;
        }
 

        if (forecastPressed) {
            windbox.setVisible(false);
            windbox.setSelected(false);
        }
        else {
            windbox.setVisible(true);
        }
       }
       catch (Error e) {
           System.out.println("Error: " + e);
       }
    }
    */
    @FXML
    private void buttonPressed() throws IOException, Exception {
        try {
            GraphViewController viewController = new GraphViewController();
            GraphDrawerFactory drawerFactory = new GraphDrawerFactory();
            
            boolean forecastPressed = false;
            if (viewController.buttonForecast != null 
                    && viewController.buttonForecast.isPressed() == true) {
                forecastPressed = true;
            }


            if (forecastPressed) {
                cloudbox.setDisable(true);
                cloudbox.setSelected(false);
            }
            else {
                cloudbox.setDisable(false);
            }
            
            // Temperature (obs&fore)
            if (tempbox.isSelected()) {
                setTemp(true);
            }
            else {
                setTemp(false);
            }
            // Observed Wind (obs&fore)
            if (windbox.isSelected()) {
                setWind(true);
            }
            else {
                setWind(false);
            }
            // Observed Cloudiness (obs)
            if (cloudbox.isSelected()) {
                setCloud(true);
            }
            else {
                setCloud(false);
            }
            
            // Call to update every time button is pressed
            //drawerFactory.update();
        }
        catch (Error e) {
            System.out.println("Error: " + e);
        }
    }
    
    // Temp
    public void setTemp (boolean newTemp) {
        temp = newTemp;
    }
    public boolean getTemp () {
        return temp;
    }
    // Wind
    public void setWind (boolean newWind) {
        wind = newWind;
    }
    public boolean getWind () {
        return wind;
    }
    // Cloud
    public void setCloud (boolean newCloud) {
        cloud = newCloud;
    }
    public boolean getCloud () {
        return cloud;
    }
     
    
    
    public void status() {       
        System.out.println("---Status---");
        System.out.println("Temp: " + tempbox.isPressed());
        System.out.println("Wind: " + windbox.isPressed());
        System.out.println("Cloud: " + cloudbox.isPressed());
        //System.out.println("Forecast Radio: " + forecastRadio.isSelected());
        //System.out.println("Observation Radio: " + observationRadio.isSelected());
    }
    
}
