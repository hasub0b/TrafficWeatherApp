package fi.tuni.trafficweatherapp;

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

    private String selectedTask;

    public void handleCloseButton(ActionEvent actionEvent) {

        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    public void maintenanceSelected(ActionEvent actionEvent) {
        selectedTask = comboBoxSetMaintenance.getValue().toString();
    }

    public void allSelected(ActionEvent actionEvent) {
    }

    public String getSelectedTask() {return selectedTask;}

    public void setSelectedTask(String selectedTask) {this.selectedTask = selectedTask;}
}
