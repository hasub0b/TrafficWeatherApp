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

            RoadConditionForecastController rcController = loaderConditionMenu.getController();
            itemsOfInterestMenuController ioiController = loaderIoiMenu.getController();

            radioButtonItemsOfInterest.setDisable(true);
            String error = "To select items of interests\n please select a forecast";
            errorLabel.setText(error);

            childAnchorPane.addEventHandler(MouseEvent.MOUSE_MOVED, (mouseEvent) -> {
                        if (rcController.isForecastSelected()) {
                            radioButtonItemsOfInterest.setDisable(false);
                            errorLabel.setText("");
                        } else {
                            radioButtonItemsOfInterest.setDisable(true);
                            errorLabel.setText(error);
                            ioiController.setCondition(false);
                            ioiController.setSlipperiness(false);
                            ioiController.setPrecipitation(false);
                        }
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
