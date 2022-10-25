/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author mikko
 */
public class SideMenuTitleBoxesController {

    @FXML
    Label labelTrafficData;
    @FXML
    Label labelCoordinates;
    @FXML
    Label labelWeatherConditions;
    @FXML
    Button buttonWeatherConditions;
    @FXML
    Button buttonCoordinates;
    @FXML
    Button buttonTrafficData;
    @FXML
    AnchorPane anchorChildSideMenu;
    @FXML
    FXMLLoader loaderCoordinatesMenu = new FXMLLoader(
            getClass().getResource("coordinatesMenu.fxml"));

    AnchorPane anchorCoordinatesMenu;

    public void initialize() {

        try {
            anchorCoordinatesMenu = loaderCoordinatesMenu.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        buttonTrafficData.setOnAction((ActionEvent e) -> {
        });
        buttonCoordinates.setOnAction((ActionEvent e) -> {
            anchorChildSideMenu.getChildren().add(anchorCoordinatesMenu);
        });
        buttonWeatherConditions.setOnAction((ActionEvent e) -> {
        });
    }
}
