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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Mikko Moisio
 */
public class PrimaryController {

    @FXML
    Rectangle menuBar;
    @FXML
    Rectangle shapeMainBackground;
    // For displaying the menu content
    //@FXML
    //AnchorPane anchorContentView;
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
    FXMLLoader loaderGraphView = new FXMLLoader(
            getClass().getResource("graphView.fxml"));
    @FXML
    FXMLLoader loaderSettings = new FXMLLoader(
            getClass().getResource("settings.fxml"));

    AnchorPane anchorGraphView = new AnchorPane();
    AnchorPane anchorSettings = new AnchorPane();

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
            //AnchorPane anchorCoordinatesMenu = loaderCoordinatesMenu.load();
            anchorGraphView = loaderGraphView.load();
            anchorSettings = loaderSettings.load();

            AnchorPane.setLeftAnchor(anchorGraphView, 0.0);
            AnchorPane.setRightAnchor(anchorGraphView, 0.0);
            
            AnchorPane.setLeftAnchor(anchorSettings, 0.0);
            AnchorPane.setRightAnchor(anchorSettings, 0.0);
            
            // Set GraphView as default page.
            anchorContentArea.getChildren().add(anchorGraphView);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        buttonGraphView.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            buttonSettings.setSelected(false);
            anchorContentArea.getChildren().remove(1);
            anchorContentArea.getChildren().add(anchorGraphView);
            anchorGraphView.prefHeightProperty().bind(anchorContentArea.heightProperty().subtract(60));
        });
        buttonSettings.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            buttonGraphView.setSelected(false);
            anchorContentArea.getChildren().remove(1);
            anchorContentArea.getChildren().add(anchorSettings);
            anchorSettings.prefHeightProperty().bind(anchorContentArea.heightProperty().subtract(60));
        });

        //anchorMenubar.autosize();
        //anchorContentArea.autosize();
        menuBar.widthProperty().bind(anchorMain.widthProperty());

        shapeMainBackground.widthProperty().bind(anchorMain.widthProperty());
        shapeMainBackground.heightProperty().bind(anchorMain.heightProperty());
    }
}
