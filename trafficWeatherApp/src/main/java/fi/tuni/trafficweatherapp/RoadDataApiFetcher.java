/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.net.HttpURLConnection;

/**
 *
 * @author mikko
 */
public class RoadDataApiFetcher {

    static String urlRoadMaintenanceTasks
            = "https://tie.digitraffic.fi/api/maintenance/v1/tracking/tasks";

    static String urlRoadConditions = "https://tie.digitraffic.fi/api/v3/data/"
            + "road-conditions/<X_MIN>/<Y_MIN>/<X_MAX>/<Y_MAX>";

    static String urlRoadMaintenanceData = "https://tie.digitraffic.fi/api/maintenance/v1/"
            + "tracking/routes?endFrom=<START_TIME>&endBefore=<END_TIME>&"
            + "xMin=<X_MIN>&yMin=<Y_MIN>&xMax=<X_MAX>&yMax=<Y_MAX>&"
            + "taskId=<TASK_NAME>&domain=state-roads";

    // SITUATION_TYPE is a traffic message type 
    // (TRAFFIC_ANNOUNCEMENT, EXEMPTED_TRANSPORT, WEIGHT_RESTRICTION or ROAD_WORK)
    // !! This method returns only the most recent data (today) 
    // !! meaning that this query will not always return the same data as previously
    static String urlLatestTrafficMessages = "https://tie.digitraffic.fi/api/"
            + "traffic-message/v1/messages?inactiveHours=0&"
            + "includeAreaGeometry=false&situationType=<SITUATION_TYPE>";

    public RoadDataApiFetcher() {

    }

    // TODO: remove main
    public static void main(String[] args) throws IOException {
        JsonObject roadMaintenanceTasks = getRoadMaintenanceTasks();
        //System.out.println(roadMaintenanceTasks);

        JsonObject roadConditions = getRoadConditions("21", "60", "23", "62");
        //System.out.println(roadConditions);
        
        JsonObject roadMaintenanceData = getRoadMaintenanceData(
                "2022-01-19T09%3A00%3A00Z", "2022-01-19T14%3A00%3A00Z", 
                "21", "61", "22", "62", "LINE_SANDING");
        //System.out.println(roadMaintenanceData);
        
        JsonObject messages = getLatestTrafficMessages("ROAD_WORK");
        //System.out.println(messages);
    }

    public static JsonObject getRoadMaintenanceTasks() throws MalformedURLException,
            IOException {
        HttpURLConnection urlConnection = getConnection(
                urlRoadMaintenanceTasks);

        String content = new String(urlConnection.getInputStream().readAllBytes());

        // Fixing the wrong json format
        char quote = '"';
        content = "{" + quote + "features" + quote + " : " + content + "}";

        JsonObject jsonObject = JsonParser.parseString​(content).getAsJsonObject();

        return jsonObject;
    }

    private static HttpURLConnection getConnection(String urlString)
            throws MalformedURLException, IOException {
        var url = (new URL(urlString));
        HttpURLConnection urlConnection
                = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestProperty("content-type", "application/json;charset=UTF-8");
        urlConnection.setRequestProperty("Accept-Encoding", "gzip header");
        urlConnection.connect();

        return urlConnection;

    }

    public static JsonObject getRoadConditions(String xMin, String yMin,
            String xMax, String yMax) throws MalformedURLException, IOException {
        String urlString = urlRoadConditions
                .replace("<X_MIN>", xMin)
                .replace("<Y_MIN>", yMin)
                .replace("<X_MAX>", xMax)
                .replace("<Y_MAX>", yMax);

        HttpURLConnection urlConnection = getConnection(urlString);
        System.out.println(urlConnection.getResponseCode());

        JsonReader reader = new JsonReader(new InputStreamReader(urlConnection
                .getInputStream()));
        JsonObject jsonObject = JsonParser.parseReader​(reader)
                .getAsJsonObject();

        return jsonObject;
    }

    public static JsonObject getRoadMaintenanceData(String startTime,
            String endTime, String xMin, String yMin, String xMax, String yMax,
            String taskName) throws MalformedURLException, IOException {
        String urlString = urlRoadMaintenanceData
                .replace("<START_TIME>", startTime)
                .replace("<END_TIME>", endTime)
                .replace("<X_MIN>", xMin)
                .replace("<Y_MIN>", yMin)
                .replace("<X_MAX>", xMax)
                .replace("<Y_MAX>", yMax)
                .replace("<TASK_NAME>", taskName);

        HttpURLConnection urlConnection = getConnection(urlString);
        System.out.println(urlConnection.getResponseCode());

        JsonReader reader = new JsonReader(new InputStreamReader(urlConnection
                .getInputStream()));
        JsonObject jsonObject = JsonParser.parseReader​(reader)
                .getAsJsonObject();

        return jsonObject;
    }

    public static JsonObject getLatestTrafficMessages(String situationType) 
            throws MalformedURLException, IOException 
    {
        String urlString = urlLatestTrafficMessages
                .replace("<SITUATION_TYPE>", situationType);

        HttpURLConnection urlConnection = getConnection(urlString);
        System.out.println(urlConnection.getResponseCode());

        JsonReader reader = new JsonReader(new InputStreamReader(urlConnection
                .getInputStream()));
        JsonObject jsonObject = JsonParser.parseReader​(reader)
                .getAsJsonObject();

        return jsonObject;
    }
}