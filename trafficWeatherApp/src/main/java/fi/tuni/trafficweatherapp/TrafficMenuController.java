package fi.tuni.trafficweatherapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * @author Aleksi
 */
public class TrafficMenuController {
    public AnchorPane childAnchorPane;
    @FXML
    private GridPane childGridPane;

    @FXML
    private ToggleGroup traffic;

    @FXML
    private RadioButton radioButtonMaintenance;

    boolean init = false;
    private Stage stage = new Stage();
    @FXML
    FXMLLoader loaderMaintenanceMenu = new FXMLLoader(
            getClass().getResource("maintenanceMenu.fxml"));
    @FXML
    FXMLLoader loaderConditionMenu = new FXMLLoader(
            getClass().getResource("roadConditionForecastMenu.fxml"));
    @FXML
    FXMLLoader loaderMessageMenu = new FXMLLoader(
            getClass().getResource("messagesMenu.fxml"));
    @FXML
    FXMLLoader loaderIoiMenu = new FXMLLoader(
            getClass().getResource("itemsOfInterestMenu.fxml"));

    AnchorPane gridMaintenance;
    AnchorPane gridCondition;
    AnchorPane gridMessage;
    AnchorPane gridIoi;

    public void initialize() {
        try {
            gridMaintenance = loaderMaintenanceMenu.load();
            gridCondition = loaderConditionMenu.load();
            gridMessage = loaderMessageMenu.load();
            gridIoi = loaderIoiMenu.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Open correct menu from corresponding radiobutton
    public void handleRadioButtonEvent(ActionEvent actionEvent) {

        RadioButton toggle = (RadioButton) traffic.getSelectedToggle();
        System.out.println(toggle);

        switch (toggle.getText()) {
            case "Maintenance":
                childAnchorPane.getChildren().clear();
                childAnchorPane.getChildren().addAll(gridMaintenance);
                toggle.setSelected(false);
                break;
            case "Road condition forecast":
                childAnchorPane.getChildren().clear();
                childAnchorPane.getChildren().addAll(gridCondition);
                toggle.setSelected(false);
                break;
            case "Messages":
                childAnchorPane.getChildren().clear();
                childAnchorPane.getChildren().addAll(gridMessage);
                toggle.setSelected(false);
                break;
            case "Items of interest":
                childAnchorPane.getChildren().clear();
                childAnchorPane.getChildren().addAll(gridIoi);
                toggle.setSelected(false);
                break;
        }
    }
}
