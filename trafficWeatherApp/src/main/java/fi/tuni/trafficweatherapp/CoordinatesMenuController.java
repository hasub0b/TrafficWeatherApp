package fi.tuni.trafficweatherapp;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

// TODO: CHANGE LOCATIONS COORDINATES to match their intended locations.
public class CoordinatesMenuController {

    private Double[] coordinates;
    private Double minX = null;
    private Double maxX = null;
    private Double minY = null;
    private Double maxY = null;

    private static Map<String, Double[]> LOCATIONS = new HashMap<>() {
        {
            put("Hervanta", new Double[]{22.0, 23.0, 24.0, 25.0});
            put("Keskusta", new Double[]{22.0, 23.0, 24.0, 25.0});
            put("Kaleva", new Double[]{22.0, 23.0, 24.0, 25.0});
            put("Kauppi", new Double[]{22.0, 23.0, 24.0, 25.0});
            put("Leinola", new Double[]{22.0, 23.0, 24.0, 25.0});
            put("Lielahti", new Double[]{22.0, 23.0, 24.0, 25.0});
        }
    };

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

        // Makes background scalable
        backgroundShape.widthProperty().bind(anchorCoordinatesMenu
                .widthProperty());
        backgroundShape.heightProperty().bind(anchorCoordinatesMenu
                .heightProperty());

        comboBoxSetLocation.getItems().addAll(FXCollections.observableArrayList(
                LOCATIONS.keySet()).sorted());

        // TextField events
        fieldMinX.setOnKeyTyped(event -> this.typingInField(fieldMinX, event));
        fieldMaxX.setOnKeyTyped(event -> this.typingInField(fieldMaxX, event));
        fieldMinY.setOnKeyTyped(event -> this.typingInField(fieldMinY, event));
        fieldMaxY.setOnKeyTyped(event -> this.typingInField(fieldMaxY, event));

        buttonSetCoordinates.setDisable(true);
        buttonSetCoordinates.setOnAction(eh -> {
            coordinates = new Double[]{minX, maxX, minY, maxY};
            System.out.println("custom coordinates set!");
        });
        comboBoxSetLocation.setOnAction(eh -> {
               if (comboBoxSetLocation.getValue() != null) {
                    String locationName = (String) comboBoxSetLocation.getValue();
                    coordinates = LOCATIONS.get(locationName);
                    System.out.println("preset coordinates set!");
               }
        });
        
    }

    private void typingInField(TextField field, KeyEvent event) {
        if (!isLegalInput(field.getText())) {
            field.setText("not valid!");
            field.selectAll();
            buttonSetCoordinates.setDisable(true);
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
        if (Arrays.stream(new TextField[]{fieldMinX, fieldMaxX, fieldMinY,
            fieldMaxY}).allMatch(field1 -> isLegalInput(field1.getText()))) {
            buttonSetCoordinates.setDisable(false);
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
