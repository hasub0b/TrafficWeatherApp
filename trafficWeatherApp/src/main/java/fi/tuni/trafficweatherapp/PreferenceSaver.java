package fi.tuni.trafficweatherapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PreferenceSaver {

    private static final String DEFAULT_PATH = "saveddata/preferences/";

    public void save( GraphViewController gc) throws IOException {
        Map<String, Object> map = new HashMap<>();
        SideMenuTitleBoxesController sc = gc.loaderSideMenuTitleBoxes.getController();
        WeatherMenuController wc = sc.loaderWeatherMenu.getController();
        CoordinatesMenuController cc = sc.loaderCoordinatesMenu.getController();
        TrafficMenuController tc = sc.loaderTrafficMenu.getController();

        // First get forecast/observation parameter from GraphViewController
        // put it to {"timeline", 0/2/6/12}, where value 0 == observation
        if (gc.buttonObservation != null) {
            //observationPressed = gc.buttonObservation.isPressed();
            map.put("timeline",0);
        }
        else {
            if(gc.button2h.isPressed()){
                map.put("timeline",2);
            }
            if(gc.button4h.isPressed()){
                map.put("timeline",4);
            }
            if(gc.button6h.isPressed()){
                map.put("timeline",6);
            }
            if(gc.button12h.isPressed()){
                map.put("timeline",12);
            }
        }

        // Get parameters from WeatherMenuController
        // {"parameter", false/true}
        map.put("temperature", wc.getTemp());
        map.put("wind", wc.getWind());
        map.put("cloud", wc.getCloud());

        // Get parameters from CoordinatesMenuController
        map.put("coordinates", cc.getCoordinates());

        // Get parameters from TrafficMenuController

        // Maintenance
        MaintenanceMenuController mmController = tc.loaderMaintenanceMenu.getController();
        map.put("maintenance",mmController.getSelectedTask());
        // Road condition
        RoadConditionForecastController rcController = tc.loaderConditionMenu.getController();
        map.put("condition", rcController.getRoadConditionSelected());
        // Messages
        MessagesMenuController msgController = tc.loaderMessageMenu.getController();
        map.put("announcement", msgController.isAnnouncement());
        map.put("transport", msgController.isTransport());
        map.put("weight",msgController.isWeightRes());
        map.put("roadwork",msgController.isRoadWork());
        // Items of interest
        itemsOfInterestMenuController ioiController = tc.loaderIoiMenu.getController();
        map.put("precipitation", ioiController.isPrecipitation());
        map.put("slipperiness", ioiController.isSlipperiness());
        map.put("overallCondition", ioiController.isCondition());



        // Get path and write to that location
        Path dir = Paths.get(DEFAULT_PATH);
        Path studentFile = dir.resolve("pref1" + ".json");
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();

        try ( BufferedWriter bw = Files.newBufferedWriter(studentFile)) {
            gson.toJson(map, bw);
        }

        /*

        // create a writer
        Writer writer = new FileWriter("preferences.json");

        // convert map to JSON File
        new Gson().toJson(map, writer);

        // close the writer
        writer.close();

        */

    }
}
