package fi.tuni.trafficweatherapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * @author Aleksi
 */
public class RoadConditionForecastController {

    @FXML
    private CheckBox checkBoxRoadCond;

    public boolean getRoadConditionSelected() {return checkBoxRoadCond.isSelected();}
    public void setRoadConditionSelected(boolean selected) {this.checkBoxRoadCond.setSelected(selected);}

}
