package fi.tuni.trafficweatherapp;

import com.google.gson.*;
import org.json.JSONObject;
import org.json.XML;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

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

    public JsonObject parseXml(String xmlString) throws ParserConfigurationException, IOException, SAXException {

        // Convert xml string to Json.JSONObject
        JSONObject json = XML.toJSONObject(xmlString);

        //pretty print for testing purposes only
        String jsonString = json.toString(4);
        //System.out.println(jsonString);

        // Convert json.JSONObject to gson.JsonObject
        JsonObject xmlJson = new JsonParser().parse(json.toString()).getAsJsonObject();

        // Remove unnecessary info
        xmlJson.getAsJsonObject("wfs:FeatureCollection").keySet().removeIf(k -> !k.equals("wfs:member"));

        // combine elements with same id under one object and add them to new JsonObject
        JsonArray members = xmlJson.getAsJsonObject("wfs:FeatureCollection").getAsJsonArray("wfs:member");
        String[] previousId = {"", "", "", ""};
        JsonArray memberArray = new JsonArray();
        JsonObject newele = new JsonObject();
        for (JsonElement member:members) {
            String name = member.getAsJsonObject().getAsJsonObject("BsWfs:BsWfsElement").getAsJsonPrimitive("BsWfs:ParameterName").toString().split("\"")[1];
            float value = member.getAsJsonObject().getAsJsonObject("BsWfs:BsWfsElement").getAsJsonPrimitive("BsWfs:ParameterValue").getAsFloat();

            String[] id = member.getAsJsonObject().getAsJsonObject("BsWfs:BsWfsElement").getAsJsonPrimitive("gml:id").toString().split("\\.");
            if (Objects.equals(id[1], previousId[1]) && Objects.equals(id[2], previousId[2])) {
                newele.addProperty(name,value);
            } else {
                newele = new JsonObject();
                previousId = id;

                // get the location and time for new id
                String time = member.getAsJsonObject().getAsJsonObject("BsWfs:BsWfsElement").get("BsWfs:Time").toString().split("\"")[1];
                JsonObject location = member.getAsJsonObject().getAsJsonObject("BsWfs:BsWfsElement").getAsJsonObject("BsWfs:Location");

                newele.addProperty("Time",time);
                newele.add("Location",location);
                newele.addProperty(name, value);

            }
            memberArray.add(newele);
        }

        JsonObject finalJson = new JsonObject();
        finalJson.add("data",memberArray);

        return finalJson;

    }

    // Only here to get test data for parseXML
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
