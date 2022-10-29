package fi.tuni.trafficweatherapp;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class CoordinatesMenuController {
    public Double[] coordinates;
    private Double minX;
    private Double maxX;
    private Double minY;
    private Double maxY;

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

        fieldMinX.setOnKeyTyped(event -> this.typingInField(fieldMinX, event));
        fieldMaxX.setOnKeyTyped(event -> this.typingInField(fieldMaxX, event));
        fieldMinY.setOnKeyTyped(event -> this.typingInField(fieldMinY, event));
        fieldMaxY.setOnKeyTyped(event -> this.typingInField(fieldMaxY, event));

        buttonSetCoordinates.setOnAction(eh -> {
            coordinates = new Double[]{minX, maxX, minY, maxY};
        });

    }

    private void typingInField(TextField field, KeyEvent event) {
        if (!isLegalInput(field.getText())) {
            field.setText("not valid!");
            field.selectAll();
        } else {
            setCoordinate(field);
        }
    }

    private void setCoordinate(TextField field) {
        Double value = Double.valueOf(field.getText());
        if (field.equals(fieldMinX)) {
            minX = value;
        } else if (field.equals(fieldMaxX)) {
            maxX = value;
        } else if (field.equals(fieldMinY)) {
            minY = value;
        } else if (field.equals(fieldMaxY)) {
            maxY = value;
        }
    }

    /*
    Checks if given coordinate input is legal.
     */
    private boolean isLegalInput(String input) {
        try {
            double coordinate = Double.valueOf(input);
            /*if (input.split(",")[1].length() > 12) {
                return false;
            }*/
            return true;
        } catch (NumberFormatException error) {

        }
        
        return false;
    }
    
    public Double[] getCoordinates() {
        if (coordinates != null) {
            return coordinates;
        }
        return null;
    }
}
