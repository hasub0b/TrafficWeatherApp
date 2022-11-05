/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author mikko
 */
public class GraphViewController {

    @FXML
    ImageView sideMenu;
    @FXML
    StackPane stackPaneGraph;
    @FXML
    AnchorPane anchorGraphView;
    @FXML
    AnchorPane anchorSideMenu;
    @FXML
    AnchorPane anchorSideMenuBoxes;
    
    GraphDrawerFactory graphFactory = new GraphDrawerFactory();

    @FXML
    FXMLLoader loaderSideMenuTitleBoxes = new FXMLLoader(
            getClass().getResource("sideMenuTitleBoxes.fxml"));

    public void initialize() {
        try {
            anchorSideMenuBoxes = loaderSideMenuTitleBoxes.load();
        } catch (IOException e) {
            System.out.println(e);
        }

        // Action Events
        sideMenu.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            if (anchorSideMenu.getChildren().isEmpty()) {
                anchorSideMenu.getChildren().add(anchorSideMenuBoxes);
            }
        });
        anchorSideMenuBoxes.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            anchorSideMenu.getChildren().clear();
        });

        LineChart chart = graphFactory.createPlot();
        anchorGraphView.getChildren().add(chart);
    }
}
