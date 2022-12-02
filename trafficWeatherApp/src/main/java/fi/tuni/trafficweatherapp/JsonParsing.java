package fi.tuni.trafficweatherapp;

import com.google.gson.*;
import org.json.JSONObject;
import java.io.IOException;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


/**
 * Parse data from API fetchers to DataInterface
 *
 * @author Aleksi
 */
public class JsonParsing {

    /**
     * Parse traffic data to DataInterface
     * Used for messages, maintenance
     *
     * @param obj JsonObject returned by getRoadMaintenanceData() or getLatestTrafficMessages()
     */
    public static void parseTrafficData(JsonObject obj) {


        if (!obj.has("features") || obj.get("features").getAsJsonArray().size() == 0){
            System.err.println("No data received from DigiTraffic / parseTrafficData!");
            return;
        }

        JsonArray features = obj.getAsJsonArray("features");
        List<String> data = new ArrayList<>();
        Map<String, Integer> maintenanceMap = new HashMap<>();
        String taskType = "";

        boolean isMessage = false;
        try{
            for (JsonElement element:features) {

                element.getAsJsonObject().keySet().removeIf(k -> !k.equals("properties") && !k.equals("geometry"));
                JsonObject feature = element.getAsJsonObject().get("properties").getAsJsonObject();
                feature.keySet().removeIf(k -> (!k.equals("id") && !k.equals("tasks") && !k.equals("situationId") && !k.equals("situationType") && !k.equals("announcements")
                        && !k.equals("startTime") && !k.equals("endTime")));

                // if message
                if (feature.has("announcements")){
                    String title = feature.get("announcements").getAsJsonArray().get(0).getAsJsonObject().get("title").toString().replaceAll("\"","");
                    String description = feature.get("announcements").getAsJsonArray().get(0).getAsJsonObject().get("location").getAsJsonObject().get("description").toString().replaceAll("\"","");
                    String message = title + description;
                    String trimmedMessage = message.replaceAll("\\\\n"," ").replaceAll("\"","");
                    taskType = feature.get("situationType").toString().replaceAll("\"","");

                    data.add(trimmedMessage);
                    isMessage = true;
                }

                // if task
                if (feature.has("tasks")){
                    taskType = feature.get("tasks").getAsJsonArray().get(0).toString().replaceAll("\"","");
                    if (maintenanceMap.containsKey(taskType)){
                        int current = maintenanceMap.get(taskType);
                        maintenanceMap.put(taskType,current + 1);
                    } else {
                        maintenanceMap.put(taskType, 1);
                    }
                }
            }


            if (isMessage){
                if (!Objects.equals(taskType, "")){
                    DataInterface.addMessageList(taskType,data);
                }
            } else {
                if (!Objects.equals(taskType, "")){
                    DataInterface.setMaintenanceMap(maintenanceMap);
                }
            }
        } catch (Exception e){
            System.err.println("Error while reading DigiTraffic data, some values may not have been set correctly");
        }
    }

    /**
     * Get the amount of tasks for a day and store to DataInterface
     * @param obj JsonObject from RoadDataFetcher.getRoadMaintenanceDataAverage()
     */
    public static void parseAverage(JsonObject obj){

        if (!obj.has("features") || obj.get("features").getAsJsonArray().size() == 0){
            System.err.println("No data received from DigiTraffic! / parseAverage");
            return;
        }

        JsonArray features = obj.getAsJsonArray("features");
        Map<String, Integer> maintenanceMap = new HashMap<>();
        String taskType = "";

        try{
            for (JsonElement element:features) {

                element.getAsJsonObject().keySet().removeIf(k -> !k.equals("properties") && !k.equals("geometry"));
                JsonObject feature = element.getAsJsonObject().get("properties").getAsJsonObject();
                feature.keySet().removeIf(k -> (!k.equals("id") && !k.equals("tasks") && !k.equals("situationId") && !k.equals("situationType") && !k.equals("announcements")
                        && !k.equals("startTime") && !k.equals("endTime")));

                // if task
                if (feature.has("tasks")){
                    taskType = feature.get("tasks").getAsJsonArray().get(0).toString().replaceAll("\"","");
                    if (maintenanceMap.containsKey(taskType)){
                        int current = maintenanceMap.get(taskType);
                        maintenanceMap.put(taskType,current + 1);
                    } else {
                        maintenanceMap.put(taskType, 1);
                    }
                }
            }
            if (!Objects.equals(taskType, "")){
                for (String key:maintenanceMap.keySet()) {
                    DataInterface.addMaintenanceMapAverageValue(key,maintenanceMap.get(key));
                }
            }
        }catch (Exception e){
            System.err.println("Error while reading DigiTraffic data, some values may not have been set correctly");
        }

    }

    /**
     * Parse maintenance task
     * @param obj JsonObject returned by getRoadMaintenanceTasks()
     */
    public static void parseTasks(JsonObject obj){

        if (!obj.has("features") || obj.get("features").getAsJsonArray().size() == 0){
            System.err.println("No data received from DigiTraffic! / parseTasks");
            return;
        }

        List<String> data = new ArrayList<>();
        JsonArray tasks = obj.get("features").getAsJsonArray();
        for (JsonElement task:tasks) {
            data.add(task.getAsJsonObject().get("id").toString().replaceAll("\"",""));
        }
        DataInterface.setAllTaskTypes(data);
    }

    /**
     * Parse road conditions to DataInterface
     * @param obj JsonObject returned by getRoadConditions()
     */
    public static void parseRoadConditions(JsonObject obj){
        if (!obj.has("weatherData") || obj.get("weatherData").getAsJsonArray().size() == 0){
            System.err.println("No data received from DigiTraffic! / parseRoadConditions");
            return;
        }

        // reset map
        DataInterface.setItemsOfInterest(new HashMap<>());

        // Array to store every condition object
        JsonArray newJsonArray = new JsonArray();

        // get JsonObjects and loop through
        JsonArray tasks = obj.get("weatherData").getAsJsonArray();

        try{
            for (JsonElement task:tasks){
                JsonArray roadConds = task.getAsJsonObject().get("roadConditions").getAsJsonArray();
                for (JsonElement cond:roadConds) {
                    cond.getAsJsonObject().keySet().removeIf(k -> (k.equals("type") || k.equals("daylight") || k.equals("weatherSymbol") || k.equals("reliability")));
                    newJsonArray.add(cond);
                }
            }

            // Get info from first coordinates (first 4 elements in array) , if there are multiple received (array longer than 4)
            for (int i = 0; i <= 4; i++) {

                String condition = newJsonArray.get(i).getAsJsonObject().get("overallRoadCondition").toString().replaceAll("\"","");

                // Set observation values
                if (i == 0 ){
                    DataInterface.addItemOfInterest("OverallCondition",condition);
                    DataInterface.addItemOfInterest("Precipitation","ONLY AVAILABLE FOR FORECAST");
                    DataInterface.addItemOfInterest("WinterSlipperiness","ONLY AVAILABLE FOR FORECAST");
                }

                // Get forecast values
                if (i != 0) {
                    String forecastConditin = newJsonArray.get(i).getAsJsonObject().get("forecastConditionReason").getAsJsonObject().get("roadCondition").toString().replaceAll("\"","");
                    String roadCondition = condition + ": " + forecastConditin;

                    DataInterface.addItemOfInterest("OverallCondition",roadCondition);

                    String precipitation = newJsonArray.get(i).getAsJsonObject().get("forecastConditionReason").getAsJsonObject().get("precipitationCondition").toString().replaceAll("\"","");
                    DataInterface.addItemOfInterest("Precipitation",precipitation);

                    // Winter slipperiness is not always included
                    if (newJsonArray.get(i).getAsJsonObject().get("forecastConditionReason").getAsJsonObject().has("frictionCondition")){
                        String winterSlipperiness = newJsonArray.get(i).getAsJsonObject().get("forecastConditionReason").getAsJsonObject().get("frictionCondition").toString().replaceAll("\"","");
                        DataInterface.addItemOfInterest("WinterSlipperiness",winterSlipperiness);
                    } else {
                        DataInterface.addItemOfInterest("WinterSlipperiness","NOT AVAILABLE");
                    }
                }
            }
        }catch (Exception e){
            System.err.println("Error while reading DigiTraffic data, some values may not have been set correctly");
        }
    }

    /**
     * Parse data fetched from FMI to DataInterface
     *
     * @param json JSONObject returned by getForecastData() or getObservationData()
     */
    public static void parseXml(JSONObject json){

        // Convert json.JSONObject to gson.JsonObject
        JsonObject xmlJson = new JsonParser().parse(json.toString()).getAsJsonObject();

        // Remove unnecessary info
        xmlJson.getAsJsonObject("wfs:FeatureCollection").keySet().removeIf(k -> !k.equals("wfs:member"));

        // combine elements with same id under one object and add them to new JsonObject
        JsonArray members = xmlJson.getAsJsonObject("wfs:FeatureCollection").getAsJsonArray("wfs:member");
        if (members.size() == 0){
            System.err.println("No data received from FMI");
            return;
        }
        String[] previousId = {"", "", "", ""};
        JsonArray memberArray = new JsonArray();
        JsonObject newElement = new JsonObject();

        // Booleans used in for loop to check various statuses
        boolean firstElementAdded = false;
        boolean observation = true;

        try {
            for (JsonElement member:members) {

                // Get the parameter name and value
                String name = member.getAsJsonObject().getAsJsonObject("BsWfs:BsWfsElement").getAsJsonPrimitive("BsWfs:ParameterName").toString().split("\"")[1];
                float value = member.getAsJsonObject().getAsJsonObject("BsWfs:BsWfsElement").getAsJsonPrimitive("BsWfs:ParameterValue").getAsFloat();
                //System.out.println(name + value);

                // check if fetch was for forecast or observation (should make own function for each...)
                if (Objects.equals(name, "temperature") || Objects.equals(name, "windspeedms") && observation){
                    observation = false;
                }

                // Check if previous member had same id and combine them
                String[] id = member.getAsJsonObject().getAsJsonObject("BsWfs:BsWfsElement").getAsJsonPrimitive("gml:id").toString().split("\\.");
                if (Objects.equals(id[1], previousId[1]) && Objects.equals(id[2], previousId[2])) {
                    newElement.addProperty(name,value);
                } else {
                    if (firstElementAdded){
                        memberArray.add(newElement);
                    }
                    newElement = new JsonObject();
                    previousId = id;
                    firstElementAdded = true;

                    // get the location and time for new id
                    String time = member.getAsJsonObject().getAsJsonObject("BsWfs:BsWfsElement").get("BsWfs:Time").toString().split("\"")[1];
                    JsonObject location = member.getAsJsonObject().getAsJsonObject("BsWfs:BsWfsElement").getAsJsonObject("BsWfs:Location");

                    newElement.addProperty("Time",time);
                    newElement.add("Location",location);
                    newElement.addProperty(name, value);

                }

            }


            /*   Add observation values to dataInterface, choose last element to get the latest observation
             *   - observed temperatures (t2m)
             *   - observed windspeeds (ws_10min)
             *   - observed cloudcover (n_man)
             *   - observed rain (r_1h)
             */
            if (observation){

                if(Objects.equals(memberArray.get(memberArray.size() - 1).getAsJsonObject().get("r_1h").toString(), "NaN")){
                    DataInterface.setRain(0.0);
                } else {
                    DataInterface.setRain(memberArray.get(memberArray.size()-1).getAsJsonObject().get("r_1h").getAsDouble());
                }
                if (memberArray.get(memberArray.size()-1).getAsJsonObject().get("t2m").toString() == "NaN"){
                    DataInterface.setTemperature(0.0);
                } else {
                    DataInterface.setTemperature(memberArray.get(memberArray.size()-1).getAsJsonObject().get("t2m").getAsDouble());
                }
                if (memberArray.get(memberArray.size()-1).getAsJsonObject().get("n_man").toString() == "NaN"){
                    DataInterface.setCloud(0.0);
                } else {
                    DataInterface.setCloud(memberArray.get(memberArray.size()-1).getAsJsonObject().get("n_man").getAsDouble());
                }

                DataInterface.setWind(memberArray.get(memberArray.size()-1).getAsJsonObject().get("ws_10min").toString());


            } else {
                // forecast

                List<Float> rainList = new ArrayList<>();
                List<Double> temperatureList = new ArrayList<>();
                List<Float> windList = new ArrayList<>();
                List<Float> cloudList = new ArrayList<>();

                // Get next 24h forecast, assuming 60min steps
                for (int i = 0; i < 24; i++) {



                    Float rain = memberArray.get(i).getAsJsonObject().get("precipitationamount").getAsFloat();
                    Double temp = memberArray.get(i).getAsJsonObject().get("temperature").getAsDouble();
                    Float wind = memberArray.get(i).getAsJsonObject().get("windspeedms").getAsFloat();
                    Float cloud = memberArray.get(i).getAsJsonObject().get("TotalCloudCover").getAsFloat();

                    rainList.add(rain);
                    temperatureList.add(temp);
                    windList.add(wind);
                    cloudList.add(cloud);

                }
                DataInterface.setForecastTemperature(temperatureList);
                DataInterface.setForecastWind(windList);
                DataInterface.setForecastRain(rainList);
                DataInterface.setForecastCloud(cloudList);
            }

        }catch (Exception e){
            System.err.println("Error while reading FMI data, some values may not have been set correctly");
        }

    }
}
