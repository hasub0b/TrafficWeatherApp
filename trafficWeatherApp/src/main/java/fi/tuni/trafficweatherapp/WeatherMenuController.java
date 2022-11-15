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
    private CheckBox predwindbox;
    @FXML
    private CheckBox predtempbox;
    @FXML
    private CheckBox obscloudbox;
    @FXML
    private CheckBox obswindbox;
    @FXML
    private CheckBox tempbox;
    @FXML
    private RadioButton forecastRadio;
    @FXML
    private RadioButton observationRadio;
    
    
    
    // Boolean variables
    private boolean TempVis;
    private boolean ObsWindVis;
    private boolean ObsCloudVis;
    private boolean PredWindVis;
    private boolean PredTempVis;
    private boolean observation;
    private boolean forecast;
    
    @FXML
    private void radioButtonPressed() throws IOException {
        // If RadioButton is pressed, 
        // then update values to reflect the new state
        if (forecastRadio.isSelected()) {
            // Update GUI
            // Negation
            predwindbox.setSelected(true);
            predtempbox.setSelected(true);
            // Local
            obscloudbox.setSelected(false);
            obswindbox.setSelected(false);
            tempbox.setSelected(false);
            
            // Update Booleans
            setForecast(true);
            setTempVis(false);
            setObsWindVis(false);
            setObsCloudVis(false);
            setPredWindVis(true);
            setPredTempVis(true);
        }
        else if (observationRadio.isSelected()) {
            // Update GUI
            // Negation
            predwindbox.setSelected(false);
            predtempbox.setSelected(false);
            // Local
            obscloudbox.setSelected(true);
            obswindbox.setSelected(true);
            tempbox.setSelected(true);
            
            // Update Booleans
            setObservation(true);
            setTempVis(true);
            setObsWindVis(true);
            setObsCloudVis(true);
            setPredWindVis(false);
            setPredTempVis(false);
        }
        status();
    }
    
    @FXML
    private void buttonPressed() throws IOException {
        // Temperature
        if (tempbox.isSelected()) {
            setTempVis(true);
        }
        else {
            setTempVis(false);
        }
        // Observed Wind
        if (obswindbox.isSelected()) {
            setObsWindVis(true);
        }
        else {
            setObsWindVis(false);
        }
        // Observed Cloudiness
        if (obscloudbox.isSelected()) {
            setObsCloudVis(true);
        }
        else {
            setObsCloudVis(false);
        }
        // Predicted wind
        if (predwindbox.isSelected()) {
            setPredWindVis(true);
        }
        else {
            setPredWindVis(false);
        }
        // Predicted temperature
        if (predtempbox.isSelected()) {
            setPredTempVis(true);
        }
        else {
            setPredTempVis(false);
        }
        status();
    }
    
    public void status() {       
        System.out.println("---Status---");
        System.out.println("TempVis: " + TempVis);
        System.out.println("ObsWindVis: " + ObsWindVis);
        System.out.println("ObsCloudVis: " + ObsCloudVis);
        System.out.println("PredWindVis: " + PredWindVis);
        System.out.println("PredTempVis: " + PredTempVis);
        System.out.println("Forecast Radio: " + forecastRadio.isSelected());
        System.out.println("Observation Radio: " + observationRadio.isSelected());
    }
    // Accessors for radiobutton values
    public boolean getObservation() {
        return observation;
    }
    public void setObservation(boolean newObservation) {
        observation = newObservation;
    }
    
    public boolean getForecast() {
        return forecast;
    }
    public void setForecast(boolean newForecast) {
        forecast = newForecast;
    }
    
    // Accessors for checkbox values
    public boolean getTempVis() {
        return TempVis;
    }
    public void setTempVis(boolean newTempVis) {
        TempVis = newTempVis;
    }

    public boolean getObsWindVis() {
        return ObsWindVis;
    }
    public void setObsWindVis(boolean newObsWindVis) {
        ObsWindVis = newObsWindVis;
    }

    public boolean getObsCloudVis() {
        return ObsCloudVis;
    }
    public void setObsCloudVis(boolean newObsCloudVis) {
        ObsCloudVis = newObsCloudVis;
    }

    public boolean getPredWindVis() {
        return PredWindVis;
    }
    public void setPredWindVis(boolean newPredWindVis) {
        PredWindVis = newPredWindVis;
    }

    public boolean getPredTempVis() {
        return PredTempVis;
    }
    public void setPredTempVis(boolean newPredTempVis) {
        PredTempVis = newPredTempVis;
    }
    
}
