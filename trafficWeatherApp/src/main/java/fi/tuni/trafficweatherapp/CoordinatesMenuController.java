package fi.tuni.trafficweatherapp;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;


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
    
    public void initialize() throws IOException {
        comboBoxSetLocation.getStylesheets().add(getClass()
                .getResource("comboBoxTextStyle.css").toExternalForm());
        
             // Weekdays
        String week_days[] =
                   { "Hervanta", "Keskusta", "Kaleva",
                                    "Kauppi", "Leinola", "Lielahti" };
        
        comboBoxSetLocation.getItems().addAll(FXCollections.observableArrayList(week_days).sorted());
    }
}
