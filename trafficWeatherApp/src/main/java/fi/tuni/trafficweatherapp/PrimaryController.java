/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author mikko
 */
public class PrimaryController {

    @FXML
    Rectangle menuBar;
    @FXML
    Rectangle shapeMainBackground;
    // For displaying the menu content
    @FXML
    AnchorPane anchorMenu;
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
    Rectangle shapeAppTitle;

    @FXML
    public void setContentArea() throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("contentArea.fxml"));
        //AnchorPane anchorContentArea = fxmlLoader.load();
        //anchorMain.getChildren().setAll(anchorContentArea);
    }
    @FXML
    public void initialize() {
        buttonGraphView.getStylesheets().add(getClass()
                .getResource("radioButtonStyle.css").toExternalForm());
        buttonSettings.getStylesheets().add(getClass()
                .getResource("radioButtonStyle.css").toExternalForm());
        
        //shapeAppTitle.setX( anchorMain.getWidth() - (shapeAppTitle.getWidth() / 2));
        shapeAppTitle.widthProperty().bind(anchorMain.widthProperty().subtract(570));
        
        labelAppTitle.getStyleClass().add("outline");
        labelAppTitle.getStylesheets().add(getClass()
                .getResource("appTitleStyle.css").toExternalForm());
        anchorMenubar.autosize();
        anchorContentArea.autosize();
        //anchorMenubar.widthProperty().bind(anchorMain.widthProperty());
        menuBar.widthProperty().bind(anchorMain.widthProperty());
        //menuBar.heightProperty().bind(anchorMain.heightProperty());
        shapeMainBackground.widthProperty().bind(anchorMain.widthProperty());
        shapeMainBackground.heightProperty().bind(anchorMain.heightProperty());

    }
}
