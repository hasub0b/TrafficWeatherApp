/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Fetches data as JsonObject from Digitraffic API.
 *
 * @author Mikko Moisio
 */
public class RoadDataApiFetcher {

    static String urlRoadMaintenanceTasks
            = "https://tie.digitraffic.fi/api/maintenance/v1/tracking/tasks";

    static String urlRoadConditions = "https://tie.digitraffic.fi/api/v3/data/"
            + "road-conditions/<X_MIN>/<Y_MIN>/<X_MAX>/<Y_MAX>";

    static String urlRoadMaintenanceData = "https://tie.digitraffic.fi/api/maintenance/v1/"
            + "tracking/routes?endFrom=<START_TIME>&endBefore=<END_TIME>&"
            + "xMin=<X_MIN>&yMin=<Y_MIN>&xMax=<X_MAX>&yMax=<Y_MAX>"
            + "&taskId=<TASK_NAME>&domain=state-roads";

    // SITUATION_TYPE is a traffic message type 
    // (TRAFFIC_ANNOUNCEMENT, EXEMPTED_TRANSPORT, WEIGHT_RESTRICTION or ROAD_WORK)
    // !! This method returns only the most recent data (today) 
    // !! meaning that this query will not always return the same data as previously
    static String urlLatestTrafficMessages = "https://tie.digitraffic.fi/api/"
            + "traffic-message/v1/messages?inactiveHours=0&"
            + "includeAreaGeometry=false&situationType=<SITUATION_TYPE>";

    /**
     * The empty constructor.
     */
    public RoadDataApiFetcher() {

    }

    private static String localtimeToUrlTime() throws ParseException {
        DateTimeFormatter formatOfUrlTime
                = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH'%3A'mm'%3A'ss'Z'");

        // Adding max forecast hours (12).
        LocalDateTime now = LocalDateTime.now().plusHours(12);
        System.out.println(formatOfUrlTime.format(now));
        String formattedDate = formatOfUrlTime.format(now);
        return formattedDate;
    }

    // TODO: remove main
    public static void main(String[] args) throws IOException, ParseException {
        JsonObject roadMaintenanceTasks = getRoadMaintenanceTasks();
        System.out.println(roadMaintenanceTasks);

        getRoadConditions("21", "60", "23", "62");

        getRoadMaintenanceData("21", "61", "23", "63");

        getLatestTrafficMessages();

    }

    /**
     * Gets the road maintenance tasks from the api.
     *
     * @return JsonObeject of the road maintenance tasks
     * @throws MalformedURLException if the url is illegal.
     * @throws IOException if the url doesn't return content.
     */
    public static JsonObject getRoadMaintenanceTasks() throws MalformedURLException,
            IOException {
        HttpURLConnection urlConnection = getConnection(
                urlRoadMaintenanceTasks);

        String content = new String(urlConnection.getInputStream().readAllBytes());

        // Fixing the wrong json format
        char quote = '"';
        content = "{" + quote + "features" + quote + " : " + content + "}";
        System.out.println(content);
        JsonObject jsonObject = JsonParser.parseString​(content).getAsJsonObject();

        //JsonParsing.parseTrafficData(jsonObject);
        return jsonObject;
    }

    /**
     * Gets the HttpURLConnection connection to given url.
     *
     * @param urlString the url
     * @return HttpURLConnection the connection
     * @hidden
     */
    private static HttpURLConnection getConnection(String urlString)
            throws MalformedURLException, IOException {
        System.out.println(urlString);
        var url = (new URL(urlString));
        HttpURLConnection urlConnection
                = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestProperty("content-type", "application/json;charset=UTF-8");
        urlConnection.setRequestProperty("Accept-Encoding", "gzip header");
        urlConnection.connect();

        return urlConnection;

    }

    /**
     * Fetches road conditions data from Digitraffic API as JsonObject
     *
     * @param xMin minimum x coordinate of the query
     * @param yMin minimum y coordinate of the query
     * @param xMax maximum x coordinate of the query
     * @param yMax maximum y coordinate of the query
     * @return JsonObject of the road conditions data. Includes Current
     * situation and forecast for 2h, 4h, 6h and 12h.
     * @throws MalformedURLException if url is wrong.
     * @throws IOException if url doesn't return content.
     */
    public static void getRoadConditions(String xMin, String yMin,
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

        JsonParsing.parseRoadConditions(jsonObject);
    }

    /**
     * Fetches road conditions data from Digitraffic API as JsonObject
     *
     * @param startTime the start time of the query
     * @param endTime the end time of the query
     * @param xMin minimum x coordinate of the query
     * @param yMin minimum y coordinate of the query
     * @param xMax maximum x coordinate of the query
     * @param yMax maximum y coordinate of the query
     * @param taskName the task name of the query
     * @return JsonObject of the road conditions data. Includes Current
     * situation and forecast for 2h, 4h, 6h and 12h.
     * @throws MalformedURLException if url is wrong.
     * @throws IOException if url doesn't return content.
     */
    public static void getRoadMaintenanceData(String xMin, String yMin,
            String xMax, String yMax)
            throws MalformedURLException, IOException, ParseException {

        String urlString = urlRoadMaintenanceData
                .replace("<START_TIME>", "")
                .replace("<END_TIME>", localtimeToUrlTime())
                .replace("<X_MIN>", xMin)
                .replace("<Y_MIN>", yMin)
                .replace("<X_MAX>", xMax)
                .replace("<Y_MAX>", yMax)
                .replace("<TASK_NAME>", "");

        HttpURLConnection urlConnection = getConnection(urlString);

        System.out.println(urlConnection.getResponseCode());

        JsonReader reader = new JsonReader(new InputStreamReader(urlConnection
                .getInputStream()));
        JsonObject jsonObject = JsonParser.parseReader​(reader)
                .getAsJsonObject();

        JsonParsing.parseTrafficData(jsonObject);
    }

    /**
     * Gets the latest traffic messages from the api.
     *
     * @param situationType the situation type for this query.
     * TRAFFIC_ANNOUNCEMENT, EXEMPTED_TRANSPORT, WEIGHT_RESTRICTION or
     * ROAD_WORK.
     * @return JsonObject of the latest traffic messages.
     * @throws MalformedURLException if the url is illegal
     * @throws IOException if the url doesn't return content.
     */
    public static void getLatestTrafficMessages()
            throws MalformedURLException, IOException {
        String[] SITUATION_TYPES = new String[]{"TRAFFIC_ANNOUNCEMENT",
            "EXEMPTED_TRANSPORT", "WEIGHT_RESTRICTION", "ROAD_WORK"};

        for (String situationType : SITUATION_TYPES) {
            String urlString = urlLatestTrafficMessages
                    .replace("<SITUATION_TYPE>", situationType);

            HttpURLConnection urlConnection = getConnection(urlString);
            System.out.println(urlConnection.getResponseCode());

            JsonReader reader = new JsonReader(new InputStreamReader(urlConnection
                    .getInputStream()));
            JsonObject jsonObject = JsonParser.parseReader​(reader)
                    .getAsJsonObject();

            JsonParsing.parseTrafficData(jsonObject);
        }
    }
}
