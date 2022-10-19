package fi.tuni.prog3.trafficDataApp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class TrafficMenuController {
    @FXML
    private ToggleGroup traffic;

    @FXML
    private RadioButton radioButtonMaintenance;

    boolean init = false;
    private Stage stage = new Stage();


    public void handleRadioButtonEvent(ActionEvent actionEvent) throws IOException {

        RadioButton toggle = (RadioButton) traffic.getSelectedToggle();
        System.out.println(toggle);

        switch (toggle.getText()) {
            case "Maintenance":
                openWindow("maintenanceMenu", actionEvent);
                break;
            case "Road condition forecast":
                openWindow("roadConditionForecastMenu", actionEvent);
                break;
            case "Messages":
                openWindow("messagesMenu", actionEvent);
                break;
            case "Items of interest":
                openWindow("itemsOfInterestMenu", actionEvent);
                break;
        }
    }

    public void openWindow(String file, ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(file+".fxml"));

        Scene scene = new Scene(root);

        stage.setTitle(file);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);

        if (!init) {
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            init = true;
        }

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                traffic.getSelectedToggle().setSelected(false);
            }
        });
        stage.show();
    }


}
