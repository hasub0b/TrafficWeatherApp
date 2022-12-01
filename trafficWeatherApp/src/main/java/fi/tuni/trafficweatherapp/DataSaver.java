package fi.tuni.trafficweatherapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
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
        try (InputStream in = getClass().getResource("datasets").openStream();
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
                    dataNumber += 1;
                }
            }
            String filename = String.format("DataSet%d.json",dataNumber);
            String targetPath = getClass().getResource("datasets").getPath();

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
    }
}
