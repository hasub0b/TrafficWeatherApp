package fi.tuni.trafficweatherapp.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import fi.tuni.trafficweatherapp.*;
import fi.tuni.trafficweatherapp.controllers.*;

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
    @FXML
    CheckBox rainBox;
 /*@FXML
    private RadioButton forecastRadio;
    @FXML
    private RadioButton observationRadio;*/
    @FXML
    Label labelForecast;

    // Boolean variables
    private boolean temp;
    private boolean wind;
    private boolean cloud;
    private boolean rain;
    //private boolean observation;
    //private boolean forecast;

    public void initialize() throws Exception {
        labelForecast.getStyleClass().add("title");
        labelForecast.getStyleClass().add("outlineTitle");

        // Temperature is selected
        DataInterface.setTemperatureSelected(true);
        
        /*// TBA (call to factory's update if needed)
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
       }  */
    }

    @FXML
    private void buttonPressed() throws IOException, Exception {
        try {
            GraphViewController viewController = new GraphViewController();
            GraphDrawerFactory drawerFactory = new GraphDrawerFactory();
            DataInterface data = new DataInterface();

            boolean forecastPressed = false;
            if (viewController.buttonForecast != null
                    && viewController.buttonForecast.isPressed() == true) {
                forecastPressed = true;
            }

            if (forecastPressed) {
                cloudbox.setDisable(true);
                cloudbox.setSelected(false);
                //rainbox.setDisable(true);
                //rainbox.setSelected(false);
            } else {
                cloudbox.setDisable(false);
                //rainbox.setDisable(false);
            }

            // Temperature (obs&fore)
            if (tempbox.isSelected()) {
                setTemp(true);
                DataInterface.setTemperatureSelected(true);

            } else {
                setTemp(false);
                DataInterface.setTemperatureSelected(false);
            }
            // Observed Wind (obs&fore)
            if (windbox.isSelected()) {
                setWind(true);
                DataInterface.setWindSelected(true);
            } else {
                setWind(false);
                DataInterface.setWindSelected(false);
            }
            // Observed Cloudiness (obs)
            if (cloudbox.isSelected()) {
                setCloud(true);
                DataInterface.setCloudSelected(true);
            } else {
                setCloud(false);
                DataInterface.setCloudSelected(false);
            }
            
            // Rain (obs)
            if (rainBox.isSelected()) {
                setRain(true);
                DataInterface.setRainSelected(true);
            }
            else {
                setRain(false);
                DataInterface.setRainSelected(false);
            }

            // Call to update every time button is pressed
            //
            //System.out.println("Situation in datainterface (temp): " + data.getTempPressed());
            //drawerFactory.update();
        } catch (Error e) {
            System.out.println("Error: " + e);
        }
    }

    // Temp
    public void setTemp(boolean newTemp) {
        //DataInterface.setTempPressed(true);
        temp = newTemp;
        DataInterface.setTemperatureSelected(newTemp);
    }

    public boolean getTemp() {
        return temp;
    }

    // Wind
    public void setWind(boolean newWind) {
        wind = newWind;
        DataInterface.setWindSelected(newWind);
    }

    public boolean getWind() {
        return wind;
    }

    // Cloud
    public void setCloud(boolean newCloud) {
        cloud = newCloud;
        DataInterface.setCloudSelected(newCloud);
    }

    public boolean getCloud() {
        return cloud;
    }

    // Rain
    public void setRain (boolean newRain) {
        rain = newRain;
        DataInterface.setRainSelected(rain);
    }
    public boolean getRain () {
        return rain;
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