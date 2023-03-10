package fi.tuni.trafficweatherapp;

import com.google.gson.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author Aleksi
 */
public class DataLoader {

    /**
     * Load fetched data from Json file to DataInterface
     * @param filename name of the Json file
     */
    public void load(String filename) {

        try (InputStream in = getClass().getResource("datasets").openStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;
            while ((resource = br.readLine()) != null) {
                // Check if the file is json
                if(resource.equals(filename)){
                    // add the file to comboBox
                    JsonObject jsonObject = convertFileToJSON(resource);
                    loadToDataInterface(jsonObject);
                }
            }
        } catch (Exception e){
            System.err.println("ERROR WHILE UPDATING DATASETS");
        }
    }

    /**
     * Add the values from JsonObject to DataInterface
     * @param jsonObject JsonObject returned by convertFileToJSON()
     */
    private void loadToDataInterface(JsonObject jsonObject){

        // Set values from JsonObject
        try{

            HashMap<String, Integer> averages = new Gson().fromJson(jsonObject.get("Average").getAsJsonObject(), HashMap.class);
            DataInterface.setMaintenanceMapAverage(averages);

            HashMap<String, List<String>> itemsOfInterest = new Gson().fromJson(jsonObject.get("ItemsOfInterest").getAsJsonObject(), HashMap.class);
            DataInterface.setItemsOfInterest(itemsOfInterest);

            HashMap<String, List<String>> messages = new Gson().fromJson(jsonObject.get("MessagesMap").getAsJsonObject(), HashMap.class);
            DataInterface.setMessagesMap(messages);

            HashMap<String, Integer> maintenances = new Gson().fromJson(jsonObject.get("MaintenanceMap").getAsJsonObject(), HashMap.class);
            DataInterface.setMaintenanceMap(maintenances);

            List<Double> forecastTemp = new Gson().fromJson(jsonObject.get("ForecastTemp").getAsJsonArray(), List.class);
            DataInterface.setForecastTemperature(forecastTemp);

            List<String> forecastWind = new Gson().fromJson(jsonObject.get("ForecastWind").getAsJsonArray(), List.class);
            List<Float> wind = new ArrayList<>();
            for (String str:forecastWind) {
                wind.add(Float.valueOf(str));
            }
            DataInterface.setForecastWind(wind);

            List<String> forecastRain = new Gson().fromJson(jsonObject.get("ForecastRain").getAsJsonArray(), List.class);
            List<Float> rains = new ArrayList<>();
            for (String str:forecastRain) {
                rains.add(Float.valueOf(str));
            }
            DataInterface.setForecastRain(rains);

            Double[] coordinates = new Gson().fromJson(jsonObject.get("coordinates").getAsJsonArray(), Double[].class);
            DataInterface.setCoordinates(coordinates);

            if (jsonObject.get("Temp").toString().replaceAll("\"", "").equals("null")){
                DataInterface.setTemperature(null);
            } else {
                DataInterface.setTemperature(jsonObject.get("Temp").getAsDouble());
            }
            if (jsonObject.get("Wind").toString().replaceAll("\"", "").equals("null")){
                DataInterface.setWind(null);
            } else {
                DataInterface.setWind(jsonObject.get("Wind").toString().replaceAll("\"", ""));
            }
            if (jsonObject.get("Rain").toString().replaceAll("\"", "").equals("null")){
                DataInterface.setRain(0.0);
            } else {
                DataInterface.setRain(jsonObject.get("Rain").getAsDouble());
            }
            if (jsonObject.get("Cloud").toString().replaceAll("\"", "").equals("null")){
                DataInterface.setCloud(null);
            } else {
                DataInterface.setCloud(jsonObject.get("Cloud").getAsDouble());
            }

        }catch (Exception e){
            System.err.println(e);
            System.err.println("JSON FILE DOESN'T HAVE CORRECT FORMATTING");
        }
    }

    /**
     * Convert JSON file to JsonObject
     * @param fileName Name of the JSON file
     * @return JsonObject of the file
     */
    private JsonObject convertFileToJSON(String fileName){

        // Read from File to String
        JsonObject jsonObject = new JsonObject();

        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(getClass().getResource("datasets/" + fileName).getFile()));
            jsonObject = jsonElement.getAsJsonObject();
        } catch (IOException e) {
            System.err.println("ERROR WHILE CONVERTING");
        }

        return jsonObject;
    }
}
