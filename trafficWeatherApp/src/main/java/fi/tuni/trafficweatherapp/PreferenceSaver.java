package fi.tuni.trafficweatherapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
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

    /**
     * Save the user selections from DataInterface to JSON file
     */
    public void save() {
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
        map.put("rain", DataInterface.isRainSelected());

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


        int prefNumber = 1;
        try (InputStream in = getClass().getResource("preferences").openStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;

            while ((resource = br.readLine()) != null) {
                // Check if the file is json
                int lastIndexOf = resource.lastIndexOf(".");
                String extension = "";
                if (lastIndexOf != -1) {
                    extension = resource.substring(lastIndexOf);
                }
                if(extension.equals(".json")){
                    // add the file to comboBox
                    prefNumber += 1;
                }
            }
            String filename = String.format("Pref%d.json",prefNumber);
            String targetPath = getClass().getResource("preferences").getPath();

            try {
                Writer fileWriter = new FileWriter(targetPath + "/" + filename);

                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();
                gson.toJson(map, fileWriter);

                fileWriter.close();
                System.out.println("WROTE TO");
            } catch (Exception e){
                System.out.println("error");
                System.out.println(e);
            }

        } catch (Exception e){
            System.err.println("ERROR WHILE UPDATING DATASETS");
            System.out.println(e);
        }

        /*
        Path dir = Paths.get("trafficWeatherApp/savedData/preferences/");
        try ( DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {

            for (Path path : stream) {
                prefNumber +=1;
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

        } catch (IOException e){
            System.out.println("PATH NOT FOUND");
        }

         */
    }
}
