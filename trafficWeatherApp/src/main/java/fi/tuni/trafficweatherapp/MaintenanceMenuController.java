package fi.tuni.prog3.trafficDataApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;


public class MaintenanceMenuController {

    @FXML
    private ComboBox comboBoxSetMaintenance;
    @FXML
    private Button closeButton;

    public void handleCloseButton(ActionEvent actionEvent) {

        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }



}
