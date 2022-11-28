package fi.tuni.trafficweatherapp;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DataLoader {

    public void load(String filename) {

        Path dir = Paths.get("trafficWeatherApp/savedData/preferences/");
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


    private void loadToDataInterface(JsonObject jsonObject){


        // Set values from JsonObject
        try{

            HashMap<String, List<String>> itemsOfInterest = new Gson().fromJson(jsonObject.get("ItemsOfInterest").getAsJsonObject(), HashMap.class);
            DataInterface.setItemsOfInterest(itemsOfInterest);

            HashMap<String, List<String>> messages = new Gson().fromJson(jsonObject.get("MessagesMap").getAsJsonObject(), HashMap.class);
            DataInterface.setMessagesMap(messages);

            HashMap<String, Integer> maintenances = new Gson().fromJson(jsonObject.get("MaintenanceMap").getAsJsonObject(), HashMap.class);
            DataInterface.setMaintenanceMap(maintenances);

            List<Float> forecastTemp = new Gson().fromJson(jsonObject.get("ForecastTemp").getAsJsonObject(), List.class);
            DataInterface.setForecastTemperature(forecastTemp);

            List<Float> forecastWind = new Gson().fromJson(jsonObject.get("ForecastWind").getAsJsonObject(), List.class);
            DataInterface.setForecastWind(forecastWind);

            List<Float> forecastRain = new Gson().fromJson(jsonObject.get("ForecastRain").getAsJsonObject(), List.class);
            DataInterface.setForecastWind(forecastRain);

            DataInterface.setTemperature(jsonObject.get("Temp").getAsDouble());
            DataInterface.setWind(jsonObject.get("Wind").toString());
            DataInterface.setRain(jsonObject.get("Rain").getAsDouble());
            DataInterface.setCloud(jsonObject.get("Cloud").getAsDouble());


        }catch (Exception e){
            System.out.println("JSON FILE DOESN'T HAVE CORRECT FORMATTING");
        }


    }

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
