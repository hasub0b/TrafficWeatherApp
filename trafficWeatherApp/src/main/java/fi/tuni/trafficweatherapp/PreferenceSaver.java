package fi.tuni.trafficweatherapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Save user selections to Json file
 *
 * @author Aleksi
 */
public class PreferenceSaver {

    private static final String DEFAULT_PATH = "saveddata/preferences/";

    public void save() throws IOException {
        Map<String, Object> map = new HashMap<>();


        // First get forecast/observation parameter from GraphViewController
        // put it to {"timeline", 2h/4h/6h/12h}, where value 0 == observation
        if (DataInterface.isObservationSelected()){
            map.put("timeline","observation");
        }
        else {
            map.put("timeline","forecast");
        }

        // forecast hour
        map.put("forecastH",DataInterface.getSelectedForecast());

        // Get parameters from WeatherMenuController
        // {"parameter", false/true}
        map.put("temperature", DataInterface.isTemperatureSelected());
        map.put("wind", DataInterface.isWindSelected());
        map.put("cloud", DataInterface.isCloudSelected());

        // Get parameters from CoordinatesMenuController
        // map.put("coordinates", cc.getCoordinates());

        // Get parameters from TrafficMenuController
        // Maintenance
        map.put("showMaintenance",DataInterface.isMaintenanceSelected());
        map.put("maintenance",DataInterface.getSelectedMaintenance());
        // Messages
        map.put("announcement", DataInterface.isAnnouncementSelected());
        map.put("transport", DataInterface.isTransportSelected());
        map.put("weight",DataInterface.isWeightSelected());
        map.put("roadwork",DataInterface.isRoadworkSelected());
        // Items of interest
        map.put("precipitation", DataInterface.isPrecipitationSelected());
        map.put("slipperiness", DataInterface.isSlipperinessSelected());
        map.put("overallCondition", DataInterface.isOverallConditionSelected());



        /*

        // Get path and write to that location
        Path dir = Paths.get(DEFAULT_PATH);
        Path file = dir.resolve("pref1" + ".json");
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();

        try ( BufferedWriter bw = Files.newBufferedWriter(dir)) {
            gson.toJson(map, bw);
        }

        */

        int prefNumber = 1;
        Path dir = Paths.get("trafficWeatherApp/savedData/preferences/");
        try ( DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                prefNumber +=1;
            }
        }

        String filename = String.format("Pref%d",prefNumber);

        // create a writer
        Writer writer = new FileWriter("trafficWeatherApp/savedData/preferences/" + filename + ".json");

        // convert map to JSON File
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        gson.toJson(map, writer);

        // close the writer
        writer.close();



    }
}
