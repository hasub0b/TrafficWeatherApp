package fi.tuni.trafficweatherapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @author Aleksi
 */
public class MessagesMenuController {

    @FXML
    private CheckBox CBAnnouncement;
    @FXML
    private CheckBox CBExTransport;
    @FXML
    private CheckBox CBWeightRest;
    @FXML
    private CheckBox CBRoadWork;
    @FXML
    Label labelMessagesMenu;

    private boolean Announcement;
    private boolean Transport;
    private boolean WeightRes;
    private boolean RoadWork;
    
    public void initialize() {
        labelMessagesMenu.getStyleClass().add("title");
        labelMessagesMenu.getStyleClass().add("outlineTitle");
    }

    public void handleCloseButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void checkBoxSelected(ActionEvent actionEvent) {

        setAnnouncement(CBAnnouncement.isSelected());
        setTransport(CBExTransport.isSelected());
        setWeightRes(CBWeightRest.isSelected());
        setRoadWork(CBRoadWork.isSelected());

    }

    public boolean isAnnouncement() {
        return Announcement;
    }

    public void setAnnouncement(boolean announcement) {
        Announcement = announcement;
    }

    public boolean isTransport() {
        return Transport;
    }

    public void setTransport(boolean transport) {
        Transport = transport;
    }

    public boolean isWeightRes() {
        return WeightRes;
    }

    public void setWeightRes(boolean weightRes) {
        WeightRes = weightRes;
    }

    public boolean isRoadWork() {
        return RoadWork;
    }

    public void setRoadWork(boolean roadWork) {
        RoadWork = roadWork;
    }
}
