package fi.tuni.trafficweatherapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import fi.tuni.trafficweatherapp.*;

/**
 * @author Aleksi
 */
public class MessagesMenuController {

    @FXML
    private CheckBox checkBoxAnnouncement;
    @FXML
    private CheckBox checkBoxExTransport;
    @FXML
    private CheckBox checkBoxWeightRest;
    @FXML
    private CheckBox checkBoxRoadWork;
    @FXML
    Label labelMessagesMenu;

    private boolean announcement;
    private boolean transport;
    private boolean weightRes;
    private boolean roadWork;
    
    public void initialize() {
        labelMessagesMenu.getStyleClass().add("title");
        labelMessagesMenu.getStyleClass().add("outlineTitle");
    }


    public void checkBoxSelected(ActionEvent actionEvent) {
        setAnnouncement(checkBoxAnnouncement.isSelected());
        DataInterface.setAnnouncementSelected(announcement);
        setTransport(checkBoxExTransport.isSelected());
        DataInterface.setTransportSelected(transport);
        setWeightRes(checkBoxWeightRest.isSelected());
        DataInterface.setWeightSelected(weightRes);
        setRoadWork(checkBoxRoadWork.isSelected());
        DataInterface.setRoadworkSelected(roadWork);

    }

    public boolean isAnnouncement() {
        return announcement;
    }
    public void setAnnouncement(boolean announcement) {
        this.announcement = announcement;
        checkBoxAnnouncement.setSelected(announcement);
    }

    public boolean isTransport() {
        return transport;
    }
    public void setTransport(boolean transport) {
        this.transport = transport;
        checkBoxExTransport.setSelected(transport);
    }

    public boolean isWeightRes() {
        return weightRes;
    }
    public void setWeightRes(boolean weightRes) {
        this.weightRes = weightRes;
        checkBoxWeightRest.setSelected(weightRes);
    }

    public boolean isRoadWork() {
        return roadWork;
    }
    public void setRoadWork(boolean roadWork) {
        this.roadWork = roadWork;
        checkBoxRoadWork.setSelected(roadWork);
    }
}
