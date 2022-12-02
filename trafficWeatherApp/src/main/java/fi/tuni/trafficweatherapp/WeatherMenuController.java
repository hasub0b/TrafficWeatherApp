package fi.tuni.trafficweatherapp;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

/**
 * 
 * @author Arttu Lehtola
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
    @FXML
    Label labelForecast;

    // Boolean variables
    private boolean temp;
    private boolean wind;
    private boolean cloud;
    private boolean rain;

    /**
     * Sets styles and default values 
     * @throws Exception Exception, if there's problem with type or data indexing
     */
    public void initialize() throws Exception {
        labelForecast.getStyleClass().add("title");
        labelForecast.getStyleClass().add("outlineTitle");

        // Temperature is selected
        DataInterface.setTemperatureSelected(true);
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
            } else {
                cloudbox.setDisable(false);
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
        } catch (Error e) {
            System.out.println("Error: " + e);
        }
    }

    
    // Accessors
    // Temp
    public void setTemp(boolean newTemp) {
        tempbox.setSelected(newTemp);
        temp = newTemp;
        DataInterface.setTemperatureSelected(newTemp);
    }

    public boolean getTemp() {
        return temp;
    }

    // Wind
    public void setWind(boolean newWind) {
        windbox.setSelected(newWind);
        wind = newWind;
        DataInterface.setWindSelected(newWind);
    }

    public boolean getWind() {
        return wind;
    }

    // Cloud
    public void setCloud(boolean newCloud) {
        cloudbox.setSelected(newCloud);
        cloud = newCloud;
        DataInterface.setCloudSelected(newCloud);
    }

    public boolean getCloud() {
        return cloud;
    }

    // Rain
    public void setRain (boolean newRain) {
        rainBox.setSelected(newRain);
        rain = newRain;
        DataInterface.setRainSelected(rain);
    }
    public boolean getRain () {
        return rain;
    }

    /**
     * Reports boolean status of some checkboxes
     */
    public void status() {
        System.out.println("---Status---");
        System.out.println("Temp: " + tempbox.isPressed());
        System.out.println("Wind: " + windbox.isPressed());
        System.out.println("Cloud: " + cloudbox.isPressed());
    }

}
