package fi.tuni.trafficweatherapp;

import com.google.gson.*;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author Aleksi
 */
public class DataLoader {

    /**
     * Load fetched data from Json file to DataInterface
     * @param filename name of the Json file
     */
    public void load(String filename) {

        Path dir = Paths.get("trafficWeatherApp/savedData/datasets/");
        String file = "";
        try ( DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                if(Objects.equals(filename, path.getFileName().toString())){
                    file = path.toString();
                }
            }
            if (file.equals("")){
                System.out.println("FILE NOT FOUND");
            } else {
                JsonObject jsonObject = convertFileToJSON(file);
                loadToDataInterface(jsonObject);
            }

        }catch (IOException e){
            System.out.println("PATH NOT FOUND");
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

            List<Float> forecastTemp = new Gson().fromJson(jsonObject.get("ForecastTemp").getAsJsonArray(), List.class);
            DataInterface.setForecastTemperature(forecastTemp);

            List<Float> forecastWind = new Gson().fromJson(jsonObject.get("ForecastWind").getAsJsonArray(), List.class);
            DataInterface.setForecastWind(forecastWind);

            List<Float> forecastRain = new Gson().fromJson(jsonObject.get("ForecastRain").getAsJsonArray(), List.class);
            DataInterface.setForecastWind(forecastRain);

            if (jsonObject.get("Temp").toString().replaceAll("\"", "").equals("null")){
                DataInterface.setTemperature(null);
            } else {
                DataInterface.setTemperature(jsonObject.get("Temp").getAsDouble());
            }
            if (jsonObject.get("Wind").toString().replaceAll("\"", "").equals("null")){
                DataInterface.setWind(null);
            } else {
                DataInterface.setWind(jsonObject.get("Wind").toString());
            }
            if (jsonObject.get("Rain").toString().replaceAll("\"", "").equals("null")){
                DataInterface.setRain(null);
            } else {
                DataInterface.setRain(jsonObject.get("Rain").getAsDouble());
            }
            if (jsonObject.get("Cloud").toString().replaceAll("\"", "").equals("null")){
                DataInterface.setCloud(null);
            } else {
                DataInterface.setCloud(jsonObject.get("Cloud").getAsDouble());
            }

        }catch (Exception e){
            System.out.println(e);
            System.out.println("JSON FILE DOESN'T HAVE CORRECT FORMATTING");
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
            JsonElement jsonElement = parser.parse(new FileReader(fileName));
            jsonObject = jsonElement.getAsJsonObject();
        } catch (IOException e) {
            System.out.println("ERROR WHILE CONVERTING");
        }

        return jsonObject;
    }
}
