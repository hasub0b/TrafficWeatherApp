/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author mikko
 */
public class PrimaryController {
    @FXML
    Rectangle menuBar;
    @FXML
    AnchorPane painPane;
    @FXML
    Rectangle shapeMainBackground;
    @FXML
    AnchorPane anchorMenu;
    
    public void init() {
        menuBar.widthProperty().bind(painPane.widthProperty());
        menuBar.heightProperty().bind(painPane.heightProperty());
        shapeMainBackground.widthProperty().bind(painPane.widthProperty());
        shapeMainBackground.heightProperty().bind(painPane.heightProperty());
        
    }
}
