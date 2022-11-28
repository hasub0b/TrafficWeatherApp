/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static javafx.fxml.FXMLLoader.load;

/**
 *
 * @author mikko
 */
public class SettingsController {

    @FXML
    AnchorPane settingsAnchor;
    @FXML
    Label labelDatasets;
    @FXML
    Label labelPreferences;
    @FXML
    Button buttonPreferencesSave;
    @FXML
    Button buttonPreferencesLoad;
    @FXML
    Button buttonDatasetSave;
    @FXML
    Button buttonDatasetLoad;
    @FXML
    ComboBox comboBoxPreferences;
    @FXML
    ComboBox comboBoxDataset;


    @FXML
    public void initialize() {
        labelDatasets.getStyleClass().add("title");
        labelDatasets.getStyleClass().add("outlineTitle");
        labelPreferences.getStyleClass().add("title");
        labelPreferences.getStyleClass().add("outlineTitle");

        updatePreferenceBox();
    }

    public void savePreference(ActionEvent actionEvent) throws IOException {

        // save to json
        PreferenceSaver preferenceSaver = new PreferenceSaver();
        preferenceSaver.save();

        // update combobox
        updatePreferenceBox();

    }

    private void updatePreferenceBox() {
        comboBoxPreferences.getItems().clear();
        Path dir = Paths.get("trafficWeatherApp/savedData/preferences/");
        try ( DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                comboBoxPreferences.getItems().add(path.getFileName().toString());
            }
            comboBoxPreferences.getSelectionModel().selectFirst();
        } catch (IOException e){
            System.out.println("ERROR WHILE UPDATING PREFERENCES");
        }
    }

    public void loadPreferences(ActionEvent actionEvent) throws IOException {
        PreferenceLoader preferenceLoader = new PreferenceLoader();
        if (!comboBoxPreferences.getSelectionModel().isEmpty()){
            preferenceLoader.load(comboBoxPreferences.getValue().toString(),settingsAnchor.getScene().getRoot());
        }

    }

    public void saveData(ActionEvent actionEvent) {
        DataSaver dataSaver = new DataSaver();
        dataSaver.save();
    }
}
