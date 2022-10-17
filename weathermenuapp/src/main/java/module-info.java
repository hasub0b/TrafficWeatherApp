module com.mycompany.weathermenuapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.weathermenuapp to javafx.fxml;
    exports com.mycompany.weathermenuapp;
}
