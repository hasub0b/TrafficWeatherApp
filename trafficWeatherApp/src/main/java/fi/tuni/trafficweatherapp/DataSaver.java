package fi.tuni.trafficweatherapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
 * @author Aleksi
 */
public class DataSaver {

    /**
     * Save the fetched data from DataInterface to Json file
     */
    public void save(){
        Map<String, Object> map = new HashMap<>();

        map.put("ItemsOfInterest",DataInterface.getItemsOfInterest());
        map.put("MessagesMap",DataInterface.getMessagesMap());
        map.put("MaintenanceMap",DataInterface.getItemsOfInterest());
        map.put("ForecastTemp",DataInterface.getForecastTemperature());
        map.put("Temp",DataInterface.getTemperature());
        map.put("ForecastWind",DataInterface.getForecastWind());
        map.put("Wind",DataInterface.getWind());
        map.put("ForecastRain",DataInterface.getForecastRain());
        map.put("Rain",DataInterface.getRain());
        map.put("Cloud",DataInterface.getCloud());
        map.put("Average",DataInterface.getMaintenanceMapAverage());

        int dataNumber = 1;
        Path dir = Paths.get("trafficWeatherApp/savedData/datasets/");
        try ( DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                dataNumber +=1;
            }
            String filename = String.format("DataSet%d",dataNumber);

            // create a writer
            Writer writer = new FileWriter("trafficWeatherApp/savedData/datasets/" + filename + ".json");

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
    }
}
