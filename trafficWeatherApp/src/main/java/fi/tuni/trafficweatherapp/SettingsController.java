/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 *
 * @author mikko
 */
public class SettingsController {

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
    }
}
