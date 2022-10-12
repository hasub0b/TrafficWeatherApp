module fi.tuni.prog3.trafficweatherapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens fi.tuni.prog3.trafficweatherapp to javafx.fxml;
    exports fi.tuni.prog3.trafficweatherapp;
}
