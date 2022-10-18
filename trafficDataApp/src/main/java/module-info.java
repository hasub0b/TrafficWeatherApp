module fi.tuni.prog3.trafficweatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens fi.tuni.prog3.trafficDataApp to javafx.fxml;
    exports fi.tuni.prog3.trafficDataApp;
}
