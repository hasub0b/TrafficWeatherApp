package fi.tuni.trafficweatherapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


import java.io.IOException;

/**
 * @author Aleksi
 */
public class TrafficMenuController {
    public AnchorPane childAnchorPane;

    @FXML
    private Label errorLabel;

    @FXML
    private RadioButton radioButtonItemsOfInterest;

    @FXML
    private GridPane childGridPane;

    @FXML
    private ToggleGroup traffic;

    @FXML
    private RadioButton radioButtonMaintenance;

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

            MaintenanceMenuController mmController = loaderMaintenanceMenu.getController();
            RoadConditionForecastController rcController = loaderConditionMenu.getController();
            MessagesMenuController msgController = loaderMessageMenu.getController();
            itemsOfInterestMenuController ioiController = loaderIoiMenu.getController();


            childAnchorPane.addEventHandler(ActionEvent.ACTION, event -> {

                System.out.println("------------------Traffic Menu Status------------------");

                System.out.println("Selected Maintenance: " + mmController.getSelectedTask());

                System.out.println("Road Condition Selected: " + rcController.getRoadConditionSelected());

                System.out.println("Messages Selected:\n" + "Announcement: " + msgController.isAnnouncement() +" / Transport: " + msgController.isTransport()
                + " / Weight res: " + msgController.isWeightRes() + " / Road Work: " + msgController.isRoadWork());

                System.out.println("Selected Items of Interest:\n" + "Precipitation: " + ioiController.isPrecipitation() + " / Slipperiness: " + ioiController.isSlipperiness()
                + " / Condition: " + ioiController.isCondition());

                System.out.println("------------------------------------------------------");

            });

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
            case "Items of interest (forecast)":
                childAnchorPane.getChildren().clear();
                childAnchorPane.getChildren().addAll(gridIoi);
                toggle.setSelected(false);
                break;
        }
    }
}
