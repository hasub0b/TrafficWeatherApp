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

public class WeatherDataApiFetcher {  
    
    /*
    *   Variables from controllers:
    *   - Coordinates
    *   - Date
    *   - Timestep/report frequency
    *   - Wanted parameter(s) (wind/cloud/temp)
    *   - ...TBA...
    */
  
    
    // Query
    /*
    *   (Query) Parameters explained:
    *   - t2m = temp
    *   - ws = windspeed
    *   n_man = cloud cover
    */
    static String weatherData = "https://opendata.fmi.fi/wfs?request"
            + "=getFeature&version=2.0.0&storedquery_id"
            + "=fmi::observations::weather::simple&bbox=23,61,24,62&timestep"
            + "=30&parameters=t2m,ws_10min,n_man";

    // JSONObject -variable & accessors
    // var
    private JSONObject jso = null;
    // get
    public JSONObject getJso() {
        return jso;
    }
    // set
    public void setJso(JSONObject newJso) {
        jso = newJso;
    }
    
    private void update(JSONObject a) {
        setJso(a);
    }
    
    public void main(String[] args) throws IOException {
        JSONObject jsobj = getData();
        update(jsobj);
        String jsos = jsobj.toString();
        
        //System.out.println("jso: " + jsos);
        
        /*
        *   Utilize:
        *   - Temperature (+forecast)
        *   - Wind (+forecast)
        *   - Cloud
        */
        
        /* 
        System.out.println("Entire JSON object: \n" + jso.getJSONObject("wfs:FeatureCollection"));
        String queryKey = "timeStamp";
        //System.out.println("Specific query with '" + queryKey + "':\n" + jso.getJSONObject("wfs:FeatureCollection").getString("timeStamp"));
        System.out.println("Specific query with: " + jso.getJSONObject("wfs:FeatureCollection").getString("timeStamp"));
        */
        
    }

    public static JSONObject getData() throws MalformedURLException,
            IOException 
    {
        
        // Connect to the API
        var url = (new URL(weatherData));
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.connect();
        
        //System.out.println(urlConnection.getResponseCode());
        
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
