package fi.tuni.trafficweatherapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Controller for the traffic menu fxml javafx elements.
 * @author Aleksi
 */
public class TrafficMenuController {

    public AnchorPane childAnchorPane;

    @FXML
    Label labelTrafficMenu;

    @FXML
    private ToggleGroup traffic;

    @FXML
    FXMLLoader loaderMaintenanceMenu = new FXMLLoader(
            getClass().getResource("maintenanceMenu.fxml"));
    @FXML
    FXMLLoader loaderConditionMenu = new FXMLLoader(
            getClass().getResource("roadConditionMenu.fxml"));
    @FXML
    FXMLLoader loaderMessageMenu = new FXMLLoader(
            getClass().getResource("messagesMenu.fxml"));

    AnchorPane gridMaintenance;
    AnchorPane gridCondition;
    AnchorPane gridMessage;

    /**
     * Initializes maintenance menu's elements.
     */
    public void initialize() {
        labelTrafficMenu.getStyleClass().add("title");
        labelTrafficMenu.getStyleClass().add("outlineTitle");
        try {

            gridMaintenance = loaderMaintenanceMenu.load();
            gridCondition = loaderConditionMenu.load();
            gridMessage = loaderMessageMenu.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Open correct menu from corresponding radiobutton
     * @hidden
     */
    public void handleRadioButtonEvent(ActionEvent actionEvent) {

        RadioButton toggle = (RadioButton) traffic.getSelectedToggle();

        switch (toggle.getText()) {
            case "Maintenance":
                childAnchorPane.getChildren().clear();
                childAnchorPane.getChildren().addAll(gridMaintenance);
                toggle.setSelected(false);
                break;
            case "Road condition":
                childAnchorPane.getChildren().clear();
                childAnchorPane.getChildren().addAll(gridCondition);
                toggle.setSelected(false);
                break;
            case "Messages":
                childAnchorPane.getChildren().clear();
                childAnchorPane.getChildren().addAll(gridMessage);
                toggle.setSelected(false);
                break;
        }
    }
}
