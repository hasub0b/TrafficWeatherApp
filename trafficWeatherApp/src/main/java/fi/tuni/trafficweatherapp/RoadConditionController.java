package fi.tuni.trafficweatherapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for the road condition menu fxml javafx elements.
 * @author Aleksi
 */
public class RoadConditionController {
    @FXML
    private CheckBox checkBoxPrecipitation;
    @FXML
    private CheckBox checkBoxSlipperiness;
    @FXML
    private CheckBox checkBoxRoadCond;
    @FXML Label labelRoadConditionMenu;

    private boolean Precipitation;
    private boolean Slipperiness;
    private boolean Condition;

    /**
     * Initializes maintenance menu's elements.
     */
    public void initialize() {
        labelRoadConditionMenu.getStyleClass().add("title");
        labelRoadConditionMenu.getStyleClass().add("outlineTitle");
    }

    /**
     * checkbox event handler.
     * @hidden
     */
    public void checkBoxPressed(ActionEvent actionEvent) {
        // Set boolean
        setPrecipitation(checkBoxPrecipitation.isSelected());
        DataInterface.setPrecipitationSelected(checkBoxPrecipitation.isSelected());
        setSlipperiness(checkBoxSlipperiness.isSelected());
        DataInterface.setSlipperinessSelected(checkBoxSlipperiness.isSelected());
        setCondition(checkBoxRoadCond.isSelected());
        DataInterface.setOverallConditionSelected(checkBoxRoadCond.isSelected());
    }

    // Basic getters/setters

    public boolean isPrecipitation() {return Precipitation;}
    public void setPrecipitation(boolean precipitation) {
        Precipitation = precipitation;
        checkBoxPrecipitation.setSelected(precipitation);
    }

    public boolean isSlipperiness() {return Slipperiness;}
    public void setSlipperiness(boolean slipperiness) {
        Slipperiness = slipperiness;
        checkBoxSlipperiness.setSelected(slipperiness);}

    public boolean isCondition() {return Condition;}
    public void setCondition(boolean condition) {
        Condition = condition;
        checkBoxRoadCond.setSelected(condition);
    }

}
