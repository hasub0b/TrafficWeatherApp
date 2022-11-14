package fi.tuni.trafficweatherapp;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

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
    
    // Boolean variables
    private boolean TempVis;
    private boolean ObsWindVis;
    private boolean ObsCloudVis;
    private boolean PredWindVis;
    private boolean PredTempVis;
    
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
        
        System.out.println("---Status---");
        System.out.println("TempVis: " + TempVis);
        System.out.println("ObsWindVis: " + ObsWindVis);
        System.out.println("ObsCloudVis: " + ObsCloudVis);
        System.out.println("PredWindVis: " + PredWindVis);
        System.out.println("PredTempVis: " + PredTempVis);
        
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
