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

        JsonArray features = obj.getAsJsonArray("features");
        List<String> data = new ArrayList<>();
        Map<String, Integer> maintenanceMap = new HashMap<>();
        String taskType = "";

        boolean isMessage = false;
        for (JsonElement element:features) {
            int i = 0;

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
                //total = features.size();
            }

            // Previous
            /*

            if (feature.has("tasks")){
                
                
                taskType = feature.get("tasks").getAsJsonArray().get(0).toString();

                // Start and end times
                String startTime = feature.get("startTime").toString().replace("\"","");
                String endTime = feature.get("endTime").toString().replace("\"","");

                // Coordinates -- LineString is array of multiple coordinates, Point is single coordinates
                String coordinates = "";
                try {
                    JsonObject geometry = element.getAsJsonObject().get("geometry").getAsJsonObject();
                    String type = geometry.get("type").toString().replace("\"","");
                    if (Objects.equals(type, "LineString")){
                        coordinates = geometry.get("coordinates").getAsJsonArray().toString();
                    } else {
                        // Point
                        coordinates = geometry.get("coordinates").toString();
                    }
                } catch (Exception e){
                    System.out.println("No coordinates for " + feature);
                }

                String finalString = String.format("Start time: %s, End time: %s, At: %s",startTime,endTime,coordinates);
                data.add(finalString);
            }
            
             */
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

    }

    /**
     * Parse maintenance task
     * @param obj JsonObject returned by getRoadMaintenanceTasks()
     */
    public static void parseTasks(JsonObject obj){
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

        // reset map
        DataInterface.setItemsOfInterest(new HashMap<>());

        // Array to store every condition object
        JsonArray newJsonArray = new JsonArray();

        // get JsonObjects and loop through
        JsonArray tasks = obj.get("weatherData").getAsJsonArray();

        for (JsonElement task:tasks){
            JsonArray roadConds = task.getAsJsonObject().get("roadConditions").getAsJsonArray();
            for (JsonElement cond:roadConds) {
                cond.getAsJsonObject().keySet().removeIf(k -> (k.equals("type") || k.equals("daylight") || k.equals("weatherSymbol") || k.equals("reliability")));
                newJsonArray.add(cond);
            }
        }

        // Get info from first coordinates (first 4 elements in array) , if there are multiple received (array longer than 4)
        for (int i = 0; i <= 4; i++) {

            String condition = newJsonArray.get(i).getAsJsonObject().get("overallRoadCondition").toString().replace("\"","");

            // Set observation values
            if (i == 0 ){
                DataInterface.addItemOfInterest("OverallCondition",condition);
                DataInterface.addItemOfInterest("Precipitation","ONLY AVAILABLE FOR FORECAST");
                DataInterface.addItemOfInterest("WinterSlipperiness","ONLY AVAILABLE FOR FORECAST");
            }

            // Get forecast values
            if (i != 0) {
                String forecastConditin = newJsonArray.get(i).getAsJsonObject().get("forecastConditionReason").getAsJsonObject().get("roadCondition").toString().replace("\"","");
                String roadCondition = condition + ": " + forecastConditin;

                DataInterface.addItemOfInterest("OverallCondition",roadCondition);

                String precipitation = newJsonArray.get(i).getAsJsonObject().get("forecastConditionReason").getAsJsonObject().get("precipitationCondition").toString().replace("\"","");
                DataInterface.addItemOfInterest("Precipitation",precipitation);

                // Winter slipperiness is not always included
                if (newJsonArray.get(i).getAsJsonObject().get("forecastConditionReason").getAsJsonObject().has("frictionCondition")){
                    String winterSlipperiness = newJsonArray.get(i).getAsJsonObject().get("forecastConditionReason").getAsJsonObject().get("frictionCondition").toString().replace("\"","");
                    DataInterface.addItemOfInterest("WinterSlipperiness",winterSlipperiness);
                } else {
                    DataInterface.addItemOfInterest("WinterSlipperiness","NOT AVAILABLE");
                }
            }
        }
    }

    /**
     * Parse data fetched from FMI to DataInterface
     *
     * @param json JSONObject returned by getForecastData() or getObservationData()
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static void parseXml(JSONObject json) throws ParserConfigurationException, IOException, SAXException {

        // Convert json.JSONObject to gson.JsonObject
        JsonObject xmlJson = new JsonParser().parse(json.toString()).getAsJsonObject();

        // Remove unnecessary info
        xmlJson.getAsJsonObject("wfs:FeatureCollection").keySet().removeIf(k -> !k.equals("wfs:member"));

        // combine elements with same id under one object and add them to new JsonObject
        JsonArray members = xmlJson.getAsJsonObject("wfs:FeatureCollection").getAsJsonArray("wfs:member");
        String[] previousId = {"", "", "", ""};
        JsonArray memberArray = new JsonArray();
        JsonObject newElement = new JsonObject();

        // Booleans used in for loop to check various statuses
        boolean firstElementAdded = false;
        boolean observation = true;

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

            //TODO: remove if loop when api calls have r_1h
            if (memberArray.get(memberArray.size()-1).getAsJsonObject().has("r_1h")){
                DataInterface.setRain(memberArray.get(memberArray.size()-1).getAsJsonObject().get("r_1h").getAsDouble());
            }

            DataInterface.setTemperature(memberArray.get(memberArray.size()-1).getAsJsonObject().get("t2m").getAsDouble());
            DataInterface.setWind(memberArray.get(memberArray.size()-1).getAsJsonObject().get("ws_10min").toString());
            DataInterface.setCloud(memberArray.get(memberArray.size()-1).getAsJsonObject().get("n_man").getAsDouble());
            //dataInterface.setRain(memberArray.get(memberArray.size()-1).getAsJsonObject().get("r_1h").getAsDouble());

        } else {
           // forecast

            List<Float> rainList = new ArrayList<>();
            List<Float> temperatureList = new ArrayList<>();
            List<Float> windList = new ArrayList<>();

            // Get next 24h forecast, assuming 30min steps
            for (int i = 0; i < 48; i++) {
                // TODO: delete if loop when api calls have precipitation
                if (memberArray.get(i).getAsJsonObject().has("precipitationamount")){
                    Float rain = memberArray.get(i).getAsJsonObject().get("precipitationamount").getAsFloat();
                    rainList.add(rain);
                    //DataInterface.addForecastRain(rain);
                }

                Float temp = memberArray.get(i).getAsJsonObject().get("temperature").getAsFloat();
                Float wind = memberArray.get(i).getAsJsonObject().get("windspeedms").getAsFloat();
                //Double rain = memberArray.get(i).getAsJsonObject().get("precipitationamount").getAsDouble();

                temperatureList.add(temp);
                windList.add(wind);
                //rainList.add(rain);

                //DataInterface.addForecastTemperature(temp);
                //DataInterface.addForecastWind(wind);
                //dataInterface.addForecastRain(rain);

            }
            DataInterface.setForecastTemperature(temperatureList);
            DataInterface.setForecastWind(windList);
            DataInterface.setForecastRain(rainList);
        }
        //System.out.println("Cloud: " + DataInterface.getCloud() + "  Wind: " + DataInterface.getWind() + "  Temp: " + DataInterface.getForecastTemperature());

    }

}
