package fi.tuni.trafficweatherapp;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class PreferenceSaver {

    public void save( GraphViewController gc, WeatherMenuController wc, CoordinatesMenuController cc, TrafficMenuController tc) throws IOException {
        Map<String, Object> map = new HashMap<>();

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

        // create a writer
        Writer writer = new FileWriter("preferences.json");

        // convert map to JSON File
        new Gson().toJson(map, writer);
        System.out.println("WROTE!!");

        // close the writer
        writer.close();

    }
}
