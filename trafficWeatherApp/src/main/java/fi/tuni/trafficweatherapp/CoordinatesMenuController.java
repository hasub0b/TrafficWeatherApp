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
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

// TODO: CHANGE LOCATIONS COORDINATES to match their intended locations.
public class CoordinatesMenuController {

    static int MAX_COORDINATE_LENGHT = 9;

    private Double[] coordinates;
    private Double minX = null;
    private Double maxX = null;
    private Double minY = null;
    private Double maxY = null;
    
    // [minX, maxX, minY, maxY]
    private static Map<String, Double[]> LOCATIONS = new HashMap<>() {
        {
            put("Hervanta", new Double[]{23.823851106, 23.883117728, 61.439102891, 61.461094956});
            put("Keskusta", new Double[]{23.755090615, 23.791861827, 61.491086559, 61.509263332});
            put("Kaleva", new Double[]{23.791861827, 23.829465010, 61.491643025, 61.502932821});
            put("Tampereen seutu", new Double[]{23.427829283, 24.200473208, 61.284686787, 61.603632612});
            put("Lielahti", new Double[]{23.650701132, 23.693609775, 61.511433333, 61.524749936});
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

    ContextMenu menuErrorMessage = new ContextMenu();

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
        menuErrorMessage.getItems().clear();
        menuErrorMessage.hide();
        if (field.getText().length() > MAX_COORDINATE_LENGHT) {
            String input = field.getText().substring(0, MAX_COORDINATE_LENGHT);
            field.setText(input);
            field.selectEnd();
            field.deselect();
        }
        if (!isLegalInput(field)) {
            field.selectAll();
            buttonSetCoordinates.setDisable(true);
        } else {
            setCoordinate(field);
            //field.selectForward();
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
            fieldMaxY}).allMatch(field1 -> isLegalInput(field1))) {
            buttonSetCoordinates.setDisable(false);
        }
    }
    private void setErrorMessage(TextField field, String message) {
        
        //menuErrorMessage.setStyle("-fx-background-color: red;");

        MenuItem errorMessage = new MenuItem(message);
        //errorMessage.setDisable(true);
        //errorMessage.setStyle("-fx-background-color: red;");
        
        menuErrorMessage.getItems().add(errorMessage);
        
        field.setContextMenu(menuErrorMessage);
        menuErrorMessage.show(field, Side.BOTTOM, 0, 0);
        
    }

    /*
    Checks if given coordinate input is legal.
    xMin=19, yMin=59, xMax=32, yMax=72
     */
    private boolean isLegalInput(TextField field) {
        String input = field.getText();
        if (input.length() > 0) {
            try {
                double coordinate = Double.valueOf(input);
                String text = Double.toString(coordinate);
                int integers = text.indexOf('.');
                int decimals = text.length() -  integers - 1;

                if (decimals > 6) {
                    setErrorMessage(field, "Max 6 decimals!");
                    return false;
                }
                else if (integers > 2) {
                    setErrorMessage(field, "Max 2 integers!");
                    return false;
                }

                return true;
            } catch (NumberFormatException error) {
                setErrorMessage(field, "Not a number!");
            }
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
