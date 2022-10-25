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
import javafx.scene.layout.GridPane;
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
    AnchorPane anchorContentView;
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
    GridPane gridMenubar;
    @FXML
    FXMLLoader loaderCoordinatesMenu = new FXMLLoader(
            getClass().getResource("graphView.fxml"));

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
        labelAppTitle.getStyleClass().add("outline");
        labelAppTitle.getStylesheets().add(getClass()
                .getResource("appTitleStyle.css").toExternalForm());

        try {
            AnchorPane anchorCoordinatesMenu = loaderCoordinatesMenu.load();

            AnchorPane.setLeftAnchor(anchorCoordinatesMenu, 0.0);
            AnchorPane.setRightAnchor(anchorCoordinatesMenu, 0.0);
            anchorContentArea.getChildren().set(1,anchorCoordinatesMenu);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        anchorMenubar.autosize();
        anchorContentArea.autosize();

        menuBar.widthProperty().bind(anchorMain.widthProperty());

        shapeMainBackground.widthProperty().bind(anchorMain.widthProperty());
        shapeMainBackground.heightProperty().bind(anchorMain.heightProperty());

    }
}
