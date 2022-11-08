package fi.tuni.trafficweatherapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

/**
 * @author Aleksi
 */
public class itemsOfInterestMenuController {


    @FXML
    private CheckBox checkBoxPrecipitation;
    @FXML
    private CheckBox checkBoxSlipperiness;
    @FXML
    private CheckBox checkBoxCondition;

    private boolean Precipitation;
    private boolean Slipperiness;
    private boolean Condition;

    public void handleCloseButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    public void checkBoxPressed(ActionEvent actionEvent) {

        // Set boolean

        setPrecipitation(checkBoxPrecipitation.isSelected());
        setSlipperiness(checkBoxSlipperiness.isSelected());
        setCondition(checkBoxCondition.isSelected());
    }

    public boolean isPrecipitation() {return Precipitation;}
    public void setPrecipitation(boolean precipitation) {
        Precipitation = precipitation;
        checkBoxPrecipitation.setSelected(precipitation);
    }

    public boolean isSlipperiness() {return Slipperiness;}
    public void setSlipperiness(boolean slipperiness) {
        Slipperiness = slipperiness;
        checkBoxSlipperiness.setSelected(slipperiness);
    }

    public boolean isCondition() {return Condition;}
    public void setCondition(boolean condition) {
        Condition = condition;
        checkBoxCondition.setSelected(condition);
    }

}
