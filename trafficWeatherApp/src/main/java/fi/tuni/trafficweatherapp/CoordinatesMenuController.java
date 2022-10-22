package fi.tuni.trafficweatherapp;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

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
    @FXML
    Label labelCoordinatesTitle;
    @FXML
    Rectangle backgroundShape;
    @FXML
    AnchorPane anchorCoordinatesMenu;
        
    // TODO: 
    // update coordinates to Model
    
    public void initialize() throws IOException {
        comboBoxSetLocation.getStylesheets().add(getClass()
                .getResource("comboBoxTextStyle.css").toExternalForm());
        labelCoordinatesTitle.getStyleClass().add("outline");
        labelCoordinatesTitle.getStylesheets().add(getClass()
                .getResource("titleLabelsTextStyle.css").toExternalForm());

        // Places
        String week_days[]
                = {"Hervanta", "Keskusta", "Kaleva",
                    "Kauppi", "Leinola", "Lielahti"};

        comboBoxSetLocation.getItems().addAll(FXCollections.observableArrayList(week_days).sorted());
        
        backgroundShape.widthProperty().bind(anchorCoordinatesMenu.widthProperty());
        backgroundShape.heightProperty().bind(anchorCoordinatesMenu.heightProperty());

        buttonSetCoordinates.setOnAction((setCoordinates) -> { 
            
        });
        
        
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
