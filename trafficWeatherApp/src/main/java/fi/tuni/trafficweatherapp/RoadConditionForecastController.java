package fi.tuni.trafficweatherapp;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

/**
 * @author Aleksi
 */
public class RoadConditionForecastController {

    @FXML
    private CheckBox checkBoxRoadCond;
    @FXML Label labelRoadConditionMenu;
    
    public boolean getRoadConditionSelected() {return checkBoxRoadCond.isSelected();}
    public void setRoadConditionSelected(boolean selected) {this.checkBoxRoadCond.setSelected(selected);}
    public void initialize() {
        labelRoadConditionMenu.getStyleClass().add("title");
        labelRoadConditionMenu.getStyleClass().add("outlineTitle");
    }
}
