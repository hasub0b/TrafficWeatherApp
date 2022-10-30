package fi.tuni.trafficweatherapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class itemsOfInterestMenuController {

    @FXML
    private CheckBox checkBoxVisibility;
    @FXML
    private CheckBox checkBoxFriction;
    @FXML
    private CheckBox checkBoxPrecipitation;
    @FXML
    private CheckBox checkBoxSlipperiness;
    @FXML
    private CheckBox checkBoxCondition;

    private boolean Visibility;
    private boolean Friction;
    private boolean Precipitation;
    private boolean Slipperiness;
    private boolean Condition;

    public void handleCloseButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    public void checkBoxPressed(ActionEvent actionEvent) {

        setVisibility(checkBoxVisibility.isSelected());

        setFriction(checkBoxFriction.isSelected());

        setPrecipitation(checkBoxPrecipitation.isSelected());

        setSlipperiness(checkBoxSlipperiness.isSelected());

        setCondition(checkBoxCondition.isSelected());
    }

    public boolean isVisibility() {return Visibility;}
    public void setVisibility(boolean visibility) {Visibility = visibility;}

    public boolean isFriction() {return Friction;}
    public void setFriction(boolean friction) {Friction = friction;}

    public boolean isPrecipitation() {return Precipitation;}
    public void setPrecipitation(boolean precipitation) {Precipitation = precipitation;}

    public boolean isSlipperiness() {return Slipperiness;}
    public void setSlipperiness(boolean slipperiness) {Slipperiness = slipperiness;}

    public boolean isCondition() {return Condition;}
    public void setCondition(boolean condition) {Condition = condition;}

}
