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
import java.net.HttpURLConnection;
import org.json.XML;
import org.json.JSONObject;
import org.json.JSONArray;


/**
 * @author Arttu
 */

public class WeatherApiTest {  
    
    static String weatherData = "https://opendata.fmi.fi/wfs?request"
            + "=getFeature&version=2.0.0&storedquery_id"
            + "=fmi::observations::weather::simple&bbox=23,61,24,62&timestep"
            + "=30&parameters=t2m,ws_10min,n_man";
    
    public WeatherApiTest() {

    }

    public static void main(String[] args) throws IOException {
        JSONObject jso = getData();
        String jsos = jso.toString();
        //System.out.println("jso: " + jsos);
        
        /*
        *   Utilize:
        *   - Temperature (+forecast)
        *   - Wind (+forecast)
        *   - Cloud
        */
        
        System.out.println("Entire JSON object: \n" + jso.getJSONObject("wfs:FeatureCollection"));
        String queryKey = "timeStamp";
        //System.out.println("Specific query with '" + queryKey + "':\n" + jso.getJSONObject("wfs:FeatureCollection").getString("timeStamp"));
        System.out.println("Specific query with: " + jso.getJSONObject("wfs:FeatureCollection").getString("timeStamp"));
        
        //JSONArray array = jso.getJSONArray(weatherData);
        //System.out.println(jso.get("features"));
    }

    public static JSONObject getData() throws MalformedURLException,
            IOException 
    {
        
        // Connect to the API
        var url = (new URL(weatherData));
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.connect();
        
        System.out.println(urlConnection.getResponseCode());
        
        // Read the inputstream (XML)
        InputStream inputstream = urlConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputstream);
        // Into a string
        BufferedReader bufferedreader = new BufferedReader(inputStreamReader);
        
        // Convert XML to JSON (duty of parser)
        JSONObject jso = XML.toJSONObject(bufferedreader);
   
        // Disconnect the connection
        urlConnection.disconnect();
        return jso;
    }

}
