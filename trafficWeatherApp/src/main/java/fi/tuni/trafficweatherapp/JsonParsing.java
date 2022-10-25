package fi.tuni.trafficweatherapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONObject;
import org.json.XML;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


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

    public JsonObject xmlToJson(String xmlString){
        try {


            JSONObject xmlJSONObj = XML.toJSONObject(xmlString);
            System.out.println(xmlJSONObj);
            JsonObject jsonObject = new JsonParser().parse(xmlJSONObj.toString()).getAsJsonObject();
            return jsonObject;
        } catch (JSONException je) {
            System.out.println(je.toString());

        }
        return null;
    }
    public void parseXml(String xmlString) throws ParserConfigurationException, IOException, SAXException {

        // Convert xml string to gson JsonObject
        JSONObject json = XML.toJSONObject(xmlString);
        String jsonString = json.toString(4);
        System.out.println(jsonString);
        JsonObject xmlJson = new JsonParser().parse(json.toString()).getAsJsonObject();

        xmlJson.getAsJsonObject("wfs:FeatureCollection").keySet().removeIf(k -> !k.equals("wfs:member"));
        System.out.println(xmlJson);



    }

    public String getXml() throws IOException {
        URL url = new URL("https://opendata.fmi.fi/wfs?request=getFeature&version=2.0.0&storedquery_id=fmi::observations::weather::simple&bbox=23,61,24,62&timestep=30&parameters=t2m,ws_10min,n_man");

        HttpURLConnection request1 = (HttpURLConnection) url.openConnection();
        request1.setRequestMethod("GET");
        InputStream is = request1.getInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
            //System.out.println(line);
        }
        return sb.toString();
    }



}
