/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

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

    
    private static String localtimeToUrlTime() throws ParseException {
        DateTimeFormatter formatOfUrlTime
                = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH'%3A'mm'%3A'ss'Z'");

        // Adding max forecast hours (12).
        LocalDateTime now = LocalDateTime.now().plusHours(12);
        String formattedDate = formatOfUrlTime.format(now);
        return formattedDate;
    }

    private static String localtimeToUrlTimeMinusDays(int days) throws ParseException {
        DateTimeFormatter formatOfUrlTime
                = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH'%3A'mm'%3A'ss'Z'");

        // Adding max forecast hours (12).
        LocalDateTime now = LocalDateTime.now().minusDays(days);
        String formattedDate = formatOfUrlTime.format(now);
        return formattedDate;
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
        JsonObject jsonObject = JsonParser.parseString(content).getAsJsonObject();

        JsonParsing.parseTasks(jsonObject);
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
            throws IOException {
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
     * @throws IOException if url doesn't return content.
     */
    public static void getRoadConditions(String xMin, String yMin,
            String xMax, String yMax) throws IOException {
        String urlString = urlRoadConditions
                .replace("<X_MIN>", xMin)
                .replace("<Y_MIN>", yMin)
                .replace("<X_MAX>", xMax)
                .replace("<Y_MAX>", yMax);

        HttpURLConnection urlConnection = getConnection(urlString);

        JsonReader reader = new JsonReader(new InputStreamReader(urlConnection
                .getInputStream()));
        JsonObject jsonObject = JsonParser.parseReader(reader)
                .getAsJsonObject();

        JsonParsing.parseRoadConditions(jsonObject);
    }

    /**
     * Fetches road conditions data from Digitraffic API as JsonObject
     *
     * @param xMin minimum x coordinate of the query
     * @param yMin minimum y coordinate of the query
     * @param xMax maximum x coordinate of the query
     * @param yMax maximum y coordinate of the query
     * @throws ParseException if cannot parse data.
     * @throws IOException if url doesn't return content.
     */
    public static void getRoadMaintenanceData(String xMin, String yMin,
            String xMax, String yMax)
            throws IOException, ParseException {

        String urlString = urlRoadMaintenanceData
                .replace("<START_TIME>", "")
                .replace("<END_TIME>", localtimeToUrlTime())
                .replace("<X_MIN>", xMin)
                .replace("<Y_MIN>", yMin)
                .replace("<X_MAX>", xMax)
                .replace("<Y_MAX>", yMax)
                .replace("<TASK_NAME>", "");

        HttpURLConnection urlConnection = getConnection(urlString);

        JsonReader reader = new JsonReader(new InputStreamReader(urlConnection
                .getInputStream()));
        JsonObject jsonObject = JsonParser.parseReader(reader)
                .getAsJsonObject();

        JsonParsing.parseTrafficData(jsonObject);
    }

    public static void getRoadMaintenanceDataAverage(String xMin, String yMin, String xMax, String yMax)
            throws MalformedURLException, IOException, ParseException {

        // clear map of previous values
        DataInterface.setMaintenanceMapAverage(new HashMap<>());

        // get each day of past week
        for (int i = 0; i < 7; i++) {
            String urlString = urlRoadMaintenanceData
                    .replace("<START_TIME>", localtimeToUrlTimeMinusDays(i+1))
                    .replace("<END_TIME>",localtimeToUrlTimeMinusDays(i) )
                    .replace("<X_MIN>", xMin)
                    .replace("<Y_MIN>", yMin)
                    .replace("<X_MAX>", xMax)
                    .replace("<Y_MAX>", yMax)
                    .replace("<TASK_NAME>", "");

            HttpURLConnection urlConnection = getConnection(urlString);

            JsonReader reader = new JsonReader(new InputStreamReader(urlConnection
                    .getInputStream()));
            JsonObject jsonObject = JsonParser.parseReader(reader)
                    .getAsJsonObject();

            JsonParsing.parseAverage(jsonObject);
        }
    }

    /**
     * Gets the latest traffic messages from the API.
     *
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

            JsonReader reader = new JsonReader(new InputStreamReader(urlConnection
                    .getInputStream()));
            JsonObject jsonObject = JsonParser.parseReader(reader)
                    .getAsJsonObject();

            JsonParsing.parseTrafficData(jsonObject);
        }
    }
}
