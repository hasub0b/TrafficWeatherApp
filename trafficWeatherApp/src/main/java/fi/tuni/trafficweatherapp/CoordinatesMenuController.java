package fi.tuni.trafficweatherapp;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CoordinatesMenuController {

    /*
    @FXML
    FXMLLoader loaderCoordinatesMenu = new FXMLLoader(
            getClass().getResource("coordinatesMenu.fxml"));
     */
    @FXML
    ComboBox comboBoxSetLocation;
    @FXML
    Button buttonSetCoordinates;
    @FXML
    TextField fieldMinY;
    @FXML
    TextField fieldMinX;
    @FXML
    TextField fieldMaxY;
    @FXML
    TextField fieldMaxX;

    public void initialize() throws IOException {
        comboBoxSetLocation.getStylesheets().add(getClass()
                .getResource("comboBoxTextStyle.css").toExternalForm());

        // Weekdays
        String week_days[]
                = {"Hervanta", "Keskusta", "Kaleva",
                    "Kauppi", "Leinola", "Lielahti"};

        comboBoxSetLocation.getItems().addAll(FXCollections.observableArrayList(week_days).sorted());
    }
    

    public Double[] getCoordinates() {
        
        return null;
        
    }
    
    /*
    Checks if given coordinate input is legal.
    */
    private boolean isLegalInput(String input) {
        try {
           double coordinate = Double.valueOf(input);
        }
        catch (NumberFormatException error) {
            
        }
        return false;
    }
}
