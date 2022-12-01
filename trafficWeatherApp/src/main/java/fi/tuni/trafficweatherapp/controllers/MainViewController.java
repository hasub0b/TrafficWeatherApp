/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import fi.tuni.trafficweatherapp.*;

/**
 * Controller for the main view fxml javafx elements.
 *
 * @author Mikko Moisio
 */
public class MainViewController {

    @FXML
    Rectangle menuBar;
    @FXML
    Rectangle shapeMainBackground;
    @FXML
    VBox anchorMain;
    @FXML
    AnchorPane anchorContentArea;
    @FXML
    AnchorPane anchorMenubar;
    @FXML
    Label labelAppTitle;
    @FXML
    RadioButton buttonGraphView;
    @FXML
    RadioButton buttonSettings;
    @FXML
    RadioButton buttonQuit;
    @FXML
    Rectangle shapeAppTitle;
    @FXML
    GridPane gridMenubar;
    @FXML
    FXMLLoader loaderGraphView = new FXMLLoader(
            getClass().getResource("graphView.fxml"));
    @FXML
    FXMLLoader loaderSettings = new FXMLLoader(
            getClass().getResource("settings.fxml"));

    AnchorPane anchorGraphView = new AnchorPane();
    AnchorPane anchorSettings = new AnchorPane();

    @FXML
    /**
     * Initializes main view's elements.
     */
    public void initialize() {
        ToggleGroup groupMenu = new ToggleGroup();

        buttonGraphView.setToggleGroup(groupMenu);
        buttonSettings.setToggleGroup(groupMenu);
        buttonQuit.setToggleGroup(groupMenu);

        buttonGraphView.getStyleClass().add("radioButtonMenuText");
        buttonGraphView.getStyleClass().add("radioButtonMenu");
        buttonSettings.getStyleClass().add("radioButtonMenuText");
        buttonSettings.getStyleClass().add("radioButtonMenu");
        buttonQuit.getStyleClass().add("radioButtonMenuText");
        buttonQuit.getStyleClass().add("radioButtonMenu");

        labelAppTitle.getStyleClass().add("outlineMainTitle");
        labelAppTitle.getStyleClass().add("mainTitle");

        try {
            anchorGraphView = loaderGraphView.load();
            anchorSettings = loaderSettings.load();

            AnchorPane.setLeftAnchor(anchorGraphView, 0.0);
            AnchorPane.setRightAnchor(anchorGraphView, 0.0);

            AnchorPane.setLeftAnchor(anchorSettings, 0.0);
            AnchorPane.setRightAnchor(anchorSettings, 0.0);

            // Set GraphView as default page.
            anchorContentArea.getChildren().add(anchorGraphView);
            buttonGraphView.setSelected(true);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        buttonGraphView.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            //buttonSettings.setSelected(false);
            anchorContentArea.getChildren().remove(1);
            anchorContentArea.getChildren().add(anchorGraphView);
            anchorGraphView.prefHeightProperty().bind(anchorContentArea.heightProperty().subtract(60));
        });
        buttonSettings.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            //buttonGraphView.setSelected(false);
            anchorContentArea.getChildren().remove(1);
            anchorContentArea.getChildren().add(anchorSettings);
            anchorSettings.prefHeightProperty().bind(anchorContentArea.heightProperty().subtract(60));
        });
        buttonQuit.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            Platform.exit();
            System.exit(0);
        });

        menuBar.widthProperty().bind(anchorMain.widthProperty());

        shapeMainBackground.widthProperty().bind(anchorMain.widthProperty());
        shapeMainBackground.heightProperty().bind(anchorMain.heightProperty());

        anchorGraphView.prefHeightProperty().bind(anchorContentArea.heightProperty().subtract(60));
        anchorSettings.prefHeightProperty().bind(anchorContentArea.heightProperty().subtract(60));
    }

    public FXMLLoader getLoaderGraphView() {
        return loaderGraphView;
    }
}
