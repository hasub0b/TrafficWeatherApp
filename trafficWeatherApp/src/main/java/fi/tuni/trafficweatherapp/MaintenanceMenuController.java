package fi.tuni.trafficweatherapp;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Aleksi
 */
public class MaintenanceMenuController implements Initializable {

    @FXML
    private ComboBox comboBoxSetMaintenance;
    @FXML
    private Button closeButton;

    private String selectedTask;
    private boolean allSelected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            JsonParsing.parseTasks(RoadDataApiFetcher.getRoadMaintenanceTasks());
        } catch (IOException e) {
            e.printStackTrace();
        }

        comboBoxSetMaintenance.getItems().setAll(DataInterface.getAllTaskTypes());
        comboBoxSetMaintenance.getItems().add(0,"ALL");
    }

    public void handleCloseButton(ActionEvent actionEvent) {

        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    public void maintenanceSelected(ActionEvent actionEvent) {
        selectedTask = comboBoxSetMaintenance.getValue().toString();
    }

    public void allSelected(ActionEvent actionEvent) {
        setAllSelected(!allSelected);
    }

    public String getSelectedTask() {
        if (isAllSelected()){
            return "ALL";
        } else {
            return selectedTask;
        }
    }
    public void setSelectedTask(String selectedTask) {this.selectedTask = selectedTask;}

    public boolean isAllSelected() {return allSelected;}
    public void setAllSelected(boolean allSelected) {this.allSelected = allSelected;}

}
