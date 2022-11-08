package fi.tuni.trafficweatherapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * @author Aleksi
 */
public class RoadConditionForecastController {

    @FXML
    private RadioButton TwoH;
    @FXML
    private RadioButton FourH;
    @FXML
    private RadioButton SixH;
    @FXML
    private RadioButton TwelveH;
    @FXML
    private RadioButton NoForecast;

    // 0, 2, 4, 6 or 12
    private int forecast;

    private boolean forecastSelected = false;

    public void handleCloseButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void handleRadioButtonEvent(ActionEvent actionEvent) {
        if (TwoH.isSelected()){
            setForecast(2);
            setForecastSelected(true);
        } else if (FourH.isSelected()){
            setForecast(4);
            setForecastSelected(true);
        } else if (SixH.isSelected()){
            setForecast(6);
            setForecastSelected(true);
        } else if (TwelveH.isSelected()){
            setForecast(12);
            setForecastSelected(true);
        } else if (NoForecast.isSelected()){
            setForecast(0);
            setForecastSelected(false);
        }
    }

    public int getForecast() {return forecast;}
    public void setForecast(int forecast) {this.forecast = forecast;}

    public boolean isForecastSelected() {return forecastSelected;}
    public void setForecastSelected(boolean forecastSelected) {this.forecastSelected = forecastSelected;}
}
