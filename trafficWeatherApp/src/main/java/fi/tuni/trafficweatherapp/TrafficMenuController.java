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
    
    public void initialize() {
        labelTrafficMenu.getStyleClass().add("title");
        labelTrafficMenu.getStyleClass().add("outlineTitle");
        try {

            gridMaintenance = loaderMaintenanceMenu.load();
            gridCondition = loaderConditionMenu.load();
            gridMessage = loaderMessageMenu.load();

            childAnchorPane.addEventHandler(ActionEvent.ACTION, event -> {

                System.out.println("------------------Traffic Menu Status------------------");

                System.out.println("Show: " + DataInterface.isMaintenanceSelected() + " Selected Maintenance: " + DataInterface.getSelectedMaintenance());

                System.out.println("Road Conditions:\nOverall condition: " + DataInterface.isOverallConditionSelected() +
                        " Precipitation: " + DataInterface.isPrecipitationSelected() +" Slipperiness: " + DataInterface.isSlipperinessSelected());

                System.out.println("Messages Selected:\n" + "Announcement: " + DataInterface.isAnnouncementSelected() + " / Transport: " + DataInterface.isTransportSelected()
                        + " / Weight res: " + DataInterface.isWeightSelected() + " / Road Work: " + DataInterface.isRoadworkSelected());

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
        }
    }
}
