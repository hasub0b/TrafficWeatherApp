package fi.tuni.trafficweatherapp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import javax.swing.*;

/**
 * Controller for the coordinates menu fxml javafx elements.
 * @author Mikko Moisio
 */
public class CoordinatesMenuController {

    static int MAX_COORDINATE_LENGHT = 9;

    private Double[] coordinates;
    private Double minX = null;
    private Double maxX = null;
    private Double minY = null;
    private Double maxY = null;

    // [minX, maxX, minY, maxY]
    private static final Map<String, Double[]> LOCATIONS = new HashMap<>() {
        {
            put("Hervanta", new Double[]{
                23.823851106, 23.883117728, 61.439102891, 61.461094956});
            put("Rovaniemi", new Double[]{
                25.625783754, 25.793732120, 66.485785029, 66.528515011});
            put("Kokkola", new Double[]{
                22.924080888, 23.320209865, 63.770231699, 63.906950358});
            put("Tampere region", new Double[]{
                23.427829283, 24.200473208, 61.284686787, 61.603632612});
            put("Lielahti", new Double[]{
                23.650701132, 23.693609775, 61.511433333, 61.524749936});
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
    @FXML
    Button buttonSetLocation;

    ContextMenu menuErrorMessage = new ContextMenu();

    /**
     * Initializes coordinates menu's elements.
     */
    public void initialize(){

        labelCoordinatesTitle.getStyleClass().add("title");
        labelCoordinatesTitle.getStyleClass().add("outlineTitle");

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

            setCoordinates();

            buttonSetCoordinates.setDisable(true);
        });
        comboBoxSetLocation.setOnAction(eh -> {
            if (comboBoxSetLocation.getValue() != null) {
                buttonSetLocation.setDisable(false);
            }
        });
        buttonSetLocation.setOnAction(eh -> {
            if (comboBoxSetLocation.getValue() != null) {
                String locationName = (String) comboBoxSetLocation.getValue();
                coordinates = LOCATIONS.get(locationName);

                setCoordinates();

                buttonSetLocation.setDisable(true);
            }
        });

    }

    /**
     * Typing in a coordinate field event handler.
     * @hidden 
     */
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
            buttonSetCoordinates.setDisable(true);
        } else {
            setCoordinate(field);
        }
    }
    
    /**
     * Setting coordinates if they are legal.
     * @hidden
     */
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
            if (minX > maxX) {
                setErrorMessage(field, "Min x needs to be lower than Max x!");
            } else if (minY > maxY) {
                setErrorMessage(field, "Min y needs to be lower than Max y!");
            } else {
                buttonSetCoordinates.setDisable(false);
            }
        }
    }

    /**
     * Creates error messages under the coordinate input TextField.
     * @hidden
     */
    private void setErrorMessage(TextField field, String message) {
        MenuItem errorMessage = new MenuItem(message);
        menuErrorMessage.getItems().add(errorMessage);

        field.setContextMenu(menuErrorMessage);
        menuErrorMessage.show(field, Side.BOTTOM, 0, 0);
    }

    //xMin=19, yMin=59, xMax=32, yMax=72
    /**
     * Checks if given coordinate input is legal.
     * @hidden
     */
    private boolean isLegalInput(TextField field) {
        String input = field.getText();
        if (input.length() > 0) {
            try {
                double coordinate = Double.valueOf(input);
                String text = Double.toString(coordinate);
                int integers = text.indexOf('.');
                int decimals = text.length() - integers - 1;

                if (decimals > 6) {
                    setErrorMessage(field, "Max 6 decimals!");
                    return false;
                } else if (integers > 2) {
                    setErrorMessage(field, "Max 2 integers!");
                    return false;
                } else if (field.equals(fieldMinX) && coordinate < 19) {
                    setErrorMessage(field, "Min 19!");
                    return false;
                } else if (field.equals(fieldMaxX) && coordinate > 32) {
                    setErrorMessage(field, "Max 32!");
                    return false;
                } else if (field.equals(fieldMinY) && coordinate < 59) {
                    setErrorMessage(field, "Min 59!");
                    return false;
                } else if (field.equals(fieldMaxY) && coordinate > 72) {
                    setErrorMessage(field, "Max 72!");
                    return false;
                }
                return true;

            } catch (NumberFormatException error) {
                setErrorMessage(field, "Not a number!");
            }
        }
        return false;
    }

    /**
     * Gets coordinates
     * @return Double[] coordinates
     */
    public Double[] getCoordinates() {
        return coordinates;
    }

    /**
     * Fetches data for datainterface with the coordinates.
     */
    public void setCoordinates() {
        // DataFetcher() ->update Datainterface

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        // Show LoadingScreen
                        LoadingScreen loadingScreen = new LoadingScreen();

                        DataFetcher dataFetcher = new DataFetcher(coordinates);
                        dataFetcher.fetchRoadData();
                        dataFetcher.fetchWeatherData();

                        // Close the LoadingScreen
                        loadingScreen.dispose();

                    }
                });
                return null;
            }
        };
        worker.execute();



    }
}
