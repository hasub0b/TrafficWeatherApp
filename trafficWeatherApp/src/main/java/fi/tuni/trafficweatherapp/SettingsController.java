/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Controller for the settings view fxml javafx elements.
 * @author Mikko
 * @author Aleksi
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

    /**
     * Initializes settings view's elements.
     */
    @FXML
    public void initialize() {
        labelDatasets.getStyleClass().add("title");
        labelDatasets.getStyleClass().add("outlineTitle");
        labelPreferences.getStyleClass().add("title");
        labelPreferences.getStyleClass().add("outlineTitle");

        updatePreferenceBox();
        updateDatasetBox();
    }

    public void savePreference(ActionEvent actionEvent) throws IOException {

        // save to json
        PreferenceSaver preferenceSaver = new PreferenceSaver();
        preferenceSaver.save();

        // update comboBox
        updatePreferenceBox();

    }

    public void loadPreferences(ActionEvent actionEvent){
        PreferenceLoader preferenceLoader = new PreferenceLoader();
        if (!comboBoxPreferences.getSelectionModel().isEmpty()){
            preferenceLoader.load(comboBoxPreferences.getValue().toString(),settingsAnchor.getScene().getRoot());
        }

    }

    private void updatePreferenceBox() {
        comboBoxPreferences.getItems().clear();
        Path dir = Paths.get("trafficWeatherApp/savedData/preferences/");
        try ( DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                // Check if the file is json
                String name = path.getFileName().toString();
                int lastIndexOf = name.lastIndexOf(".");
                String extension = "";
                if (lastIndexOf != -1) {
                    extension = name.substring(lastIndexOf);
                }
                if(extension.equals(".json")){
                    // add the file to comboBox
                    comboBoxPreferences.getItems().add(path.getFileName().toString());
                }

            }
            comboBoxPreferences.getSelectionModel().selectFirst();
        } catch (IOException e){
            System.out.println("ERROR WHILE UPDATING PREFERENCES");
        }
    }


    public void saveData(ActionEvent actionEvent) {

        // Save to Json
        DataSaver dataSaver = new DataSaver();
        dataSaver.save();

        // update comboBox
        updateDatasetBox();
    }

    public void loadData(ActionEvent actionEvent) {
        DataLoader dataLoader = new DataLoader();
        if (!comboBoxDataset.getSelectionModel().isEmpty()){
            dataLoader.load(comboBoxDataset.getValue().toString());
        }
    }

    private void updateDatasetBox(){
        comboBoxDataset.getItems().clear();
        Path dir = Paths.get("trafficWeatherApp/savedData/datasets/");
        try ( DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {

                // Check if the file is json
                String name = path.getFileName().toString();
                int lastIndexOf = name.lastIndexOf(".");
                String extension = "";
                if (lastIndexOf != -1) {
                    extension = name.substring(lastIndexOf);
                }
                if(extension.equals(".json")){
                    // add the file to comboBox
                    comboBoxDataset.getItems().add(path.getFileName().toString());
                }
            }
            comboBoxDataset.getSelectionModel().selectFirst();

        } catch (IOException e){
            System.out.println("ERROR WHILE UPDATING Datasets");
        }
    }
}
