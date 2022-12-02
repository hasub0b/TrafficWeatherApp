package fi.tuni.trafficweatherapp;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the maintenance menu fxml javafx elements.
 * @author Aleksi
 */
public class MaintenanceMenuController implements Initializable {

    @FXML
    private CheckBox checkBoxShowMaintenance;

    @FXML
    private ComboBox comboBoxSetMaintenance;

    private String selectedTask;
    private boolean show = false;

    /**
     * Initializes maintenance menu's elements.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            RoadDataApiFetcher.getRoadMaintenanceTasks();
        } catch (IOException e) {
            e.printStackTrace();
        }

        comboBoxSetMaintenance.getItems().setAll(DataInterface.getAllTaskTypes());
        comboBoxSetMaintenance.getItems().add(0,"ALL");
        comboBoxSetMaintenance.getSelectionModel().select(0);
    }

    /**
     * checkbox event handler.
     * @hidden
     */
    public void checkBoxPressed(ActionEvent actionEvent) {
        setShow(checkBoxShowMaintenance.isSelected());
        DataInterface.setMaintenanceSelected(checkBoxShowMaintenance.isSelected());
        setSelectedTask(comboBoxSetMaintenance.getValue().toString());
        DataInterface.setSelectedMaintenance(getSelectedTask());
    }

    /**
     * combobox  event handler
     * @hidden
     */
    public void maintenanceSelected(ActionEvent actionEvent) {
        setSelectedTask(comboBoxSetMaintenance.getValue().toString());
        DataInterface.setSelectedMaintenance(selectedTask);
    }

    /**
     * Get selected task
     * @return String selected task
     */
    public String getSelectedTask() {return selectedTask;}

    /**
     * Set selected task
     * @param selectedTask String selected task
     */
    public void setSelectedTask(String selectedTask) {this.selectedTask = selectedTask;}

    /**
     * Check if checkbox is selected
     * @return boolean selection type
     */
    public boolean isShow() {return show;}

    /**
     * Set checkbox as selected
     * @param show
     */
    public void setShow(boolean show) {
        this.show = show;
        checkBoxShowMaintenance.setSelected(show);
    }
}
