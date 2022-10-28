package fi.tuni.trafficweatherapp;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

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
    
    /*
    @FXML
    private void tempButtonPressed() throws IOException {
        if (tempbox.isSelected()) {
            setTempVis(true);
            //System.out.println("It's value is true");
            //System.out.println("System reflects: " + TempVis);
        }
        else {
            setTempVis(false);
            //System.out.println("It's value is false");
            //System.out.println("System reflects: " + TempVis);
        }
        System.out.println("---Status---");
        System.out.println("TempVis: " + TempVis);
        System.out.println("ObsWindVis: " + ObsWindVis);
        System.out.println("ObsCloudVis: " + ObsCloudVis);
        System.out.println("PredWindVis: " + PredWindVis);
        System.out.println("PredTempVis: " + PredTempVis);
        
        //System.out.println("Temperature checkbox has been (un)checked");
        //System.out.println("It's value is: " + TempVis);
        // Update checkbox value
        //setTempVis(!(getTempVis()));
    }
    
    
    public void obsWindButtonPressed() {
        if (obswindbox.isSelected()) {
            setObsWindVis(true);
            //System.out.println("It's value is true");
            //System.out.println("System reflects: " + ObsWindVis);
        }
        else {
            setObsWindVis(false);
            //System.out.println("It's value is false");
            //System.out.println("System reflects: " + ObsWindVis);
        }
        System.out.println("---Status---");
        System.out.println("TempVis: " + TempVis);
        System.out.println("ObsWindVis: " + ObsWindVis);
        System.out.println("ObsCloudVis: " + ObsCloudVis);
        System.out.println("PredWindVis: " + PredWindVis);
        System.out.println("PredTempVis: " + PredTempVis);
    }
    public void obsCloudButtonPressed() {
        if (obscloudbox.isSelected()) {
            setObsCloudVis(true);
            //System.out.println("It's value is true");
            //System.out.println("System reflects: " + ObsCloudVis);
        }
        else {
            setObsCloudVis(false);
            //System.out.println("It's value is false");
            //System.out.println("System reflects: " + ObsCloudVis);
        }
        System.out.println("---Status---");
        System.out.println("TempVis: " + TempVis);
        System.out.println("ObsWindVis: " + ObsWindVis);
        System.out.println("ObsCloudVis: " + ObsCloudVis);
        System.out.println("PredWindVis: " + PredWindVis);
        System.out.println("PredTempVis: " + PredTempVis);
    }
    public void predWindButtonPressed() {
        if (predwindbox.isSelected()) {
            setPredWindVis(true);
            //System.out.println("It's value is true");
            //System.out.println("System reflects: " + PredWindVis);
        }
        else {
            setPredWindVis(false);
            //System.out.println("It's value is false");
            //System.out.println("System reflects: " + PredWindVis);
        }
        System.out.println("---Status---");
        System.out.println("TempVis: " + TempVis);
        System.out.println("ObsWindVis: " + ObsWindVis);
        System.out.println("ObsCloudVis: " + ObsCloudVis);
        System.out.println("PredWindVis: " + PredWindVis);
        System.out.println("PredTempVis: " + PredTempVis);
    }
    public void predTempButtonPressed() {
        if (predtempbox.isSelected()) {
            setPredTempVis(true);
            //System.out.println("It's value is true");
            //System.out.println("System reflects: " + PredTempVis);
        }
        else {
            setPredTempVis(false);
            //System.out.println("It's value is false");
            //System.out.println("System reflects: " + PredTempVis);
        }
        System.out.println("---Status---");
        System.out.println("TempVis: " + TempVis);
        System.out.println("ObsWindVis: " + ObsWindVis);
        System.out.println("ObsCloudVis: " + ObsCloudVis);
        System.out.println("PredWindVis: " + PredWindVis);
        System.out.println("PredTempVis: " + PredTempVis);
    }
    */
    
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
