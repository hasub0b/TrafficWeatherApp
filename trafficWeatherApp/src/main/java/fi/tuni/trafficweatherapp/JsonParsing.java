package fi.tuni.trafficweatherapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


/**
 *
 * @author Aleksi
 */
public class JsonParsing {

    /**
     * Parse traffic data JsonObject
     * {"features":[]}
     *
     */
    public JsonObject parseTrafficData(JsonObject obj) {
        JsonObject parsedJsonObj = new JsonObject();
        JsonArray newJsonArray = new JsonArray();

        if (obj.has("weatherData")){
            return parseRoadConditions(obj);
        }

        JsonArray features = obj.getAsJsonArray("features");
        for (JsonElement element:features) {

            element.getAsJsonObject().keySet().removeIf(k -> !k.equals("properties"));

            JsonObject feature = element.getAsJsonObject().get("properties").getAsJsonObject();
            feature.keySet().removeIf(k -> (!k.equals("id") && !k.equals("tasks") && !k.equals("situationId") && !k.equals("situationType") && !k.equals("announcements")));
            if (feature.has("announcements")){
                feature.get("announcements").getAsJsonArray().get(0).getAsJsonObject().keySet().removeIf(k -> !k.equals("title") && !k.equals("description"));
            }
            newJsonArray.add(feature);

        }
        parsedJsonObj.add("features",newJsonArray);
        System.out.println(parsedJsonObj);
        return parsedJsonObj;

    }

    /**
     * Parse maintenance task
     * @param obj
     */
    public void parseTasks(JsonObject obj){
        JsonObject newJsonObj = new JsonObject();
        JsonArray tasks = obj.get("features").getAsJsonArray();
        for (JsonElement task:tasks) {
            task.getAsJsonObject().remove("nameFi");
            task.getAsJsonObject().remove("nameSv");
            task.getAsJsonObject().add("name", task.getAsJsonObject().get("nameEn"));
            task.getAsJsonObject().remove("nameEn");
        }
    }

    /**
     * Parse weatherData
     *
     */
    public JsonObject parseRoadConditions(JsonObject obj){
        JsonObject parsedJsonObj = new JsonObject();
        JsonArray newJsonArray = new JsonArray();
        JsonArray tasks = obj.get("weatherData").getAsJsonArray();
        for (JsonElement task:tasks){

            JsonArray roadConds = task.getAsJsonObject().get("roadConditions").getAsJsonArray();
            for (JsonElement cond:roadConds) {
                cond.getAsJsonObject().keySet().removeIf(k -> (k.equals("type") || k.equals("daylight") || k.equals("weatherSymbol") || k.equals("reliability")));
                newJsonArray.add(cond);

            }

        }
        parsedJsonObj.add("roadConditions",newJsonArray);

        return parsedJsonObj;

    }


}
