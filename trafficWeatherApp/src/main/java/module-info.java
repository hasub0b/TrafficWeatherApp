module fi.tuni.trafficweatherapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens fi.tuni.trafficweatherapp to javafx.fxml;
    opens fi.tuni.trafficweatherapp.controllers to javafx.fxml;
    opens fi.tuni.trafficweatherapp.drawers to javafx.fxml;
    exports fi.tuni.trafficweatherapp;
    exports fi.tuni.trafficweatherapp.controllers;
    exports fi.tuni.trafficweatherapp.drawers;
    requires com.google.gson;
    requires org.json;
    requires javafx.base;
    requires javafx.graphics;
    requires java.xml;
    requires javafx.graphicsEmpty;
    requires java.desktop;
}
