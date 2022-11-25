package fi.tuni.trafficweatherapp;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

/**
 * @author Aleksi
 */
public class RoadConditionForecastController {

    @FXML
    private CheckBox checkBoxRoadCond;

    public boolean getRoadConditionSelected() {return checkBoxRoadCond.isSelected();}
    public void setRoadConditionSelected(boolean selected) {this.checkBoxRoadCond.setSelected(selected);}

}
