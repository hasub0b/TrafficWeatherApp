/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import javafx.fxml.FXML;
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
    public void initialize() {
        labelDatasets.getStyleClass().add("title");
        labelDatasets.getStyleClass().add("outlineTitle");
        labelPreferences.getStyleClass().add("title");
        labelPreferences.getStyleClass().add("outlineTitle");
    }
}
