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
public class ApiTest {

    static String trafficData = "https://tie.digitraffic.fi/api/maintenance/v1/"
            + "tracking/routes?endFrom=2022-01-19T09%3A00%3A00Z&endBefore="
            + "2022-01-19T14%3A00%3A00Z&xMin=21&yMin=61&xMax=22&yMax=62&taskId="
            + "&domain=state-roads";
    static String messagesUrl = "https://tie.digitraffic.fi/api/traffic-message/v1/messages?inactiveHours=0&includeAreaGeometry=true&xMin=22&yMin=62&xMax=23&yMax=63&taskId=&domain=state-roads&endFrom=2022-01-19&endBefore=2022-01-19";

    public ApiTest() {

    }

    public static void main(String[] args) throws IOException {
        JsonObject js = getData();
        System.out.println(js.get("features"));
    }

    public static JsonObject getData() throws MalformedURLException,
            IOException 
    {
        var url = (new URL(messagesUrl));
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        
        //myURLConnection.setRequestProperty("content-type", "application/json;charset=UTF-8");
        urlConnection.setRequestProperty("Accept-Encoding", "gzip header");
        urlConnection.connect();
        System.out.println(urlConnection.getResponseCode());
        
        JsonReader reader = new JsonReader(new InputStreamReader(urlConnection.getInputStream()));
        JsonObject jsonObject = JsonParser.parseReader​(reader).getAsJsonObject();
        
        return jsonObject;
    }

}
