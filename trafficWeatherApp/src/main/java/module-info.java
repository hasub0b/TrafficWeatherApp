module fi.tuni.trafficweatherapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens fi.tuni.trafficweatherapp to javafx.fxml;
    exports fi.tuni.trafficweatherapp;
    requires com.google.gson;
    requires org.json;
    requires javafx.base;
    requires javafx.graphics;
    requires java.xml;
}
