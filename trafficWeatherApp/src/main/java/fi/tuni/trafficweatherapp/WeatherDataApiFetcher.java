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
    
    static String weatherData = "https://opendata.fmi.fi/wfs?request"
            + "=getFeature&version=2.0.0&storedquery_id"
            + "=fmi::observations::weather::simple&bbox=23,61,24,62&timestep"
            + "=30&parameters=t2m,ws_10min,n_man";
    
    /* 
    * We want to fetch: 
    *   - observed temperatures (t2m)
    *   - observed windspeeds (ws_10min)
    *   - observed cloudcover (n_man)
    */
    static String dynamicWeatherObservationDataString = "https://opendata.fmi.fi/wfs?request"
            + "=getFeature&version=2.0.0&storedquery_id"
            + "=fmi::observations::weather::simple&bbox=<X1>,<Y1>,<X2>,<Y2>&timestep"
            + "=<TS>&parameters=t2m,ws_10min,n_man";
    
    
    /* 
    * We want to fetch: 
    *   - temperatures (temperature)
    *   - predicted windspeeds (windspeedms)
    */
    static String dynamicWeatherForecastDataString = "https://opendata.fmi.fi/wfs?request"
            + "=getFeature&version=2.0.0&storedquery_id=fmi::forecast::harmonie::surface::point::simple&latlon"
            + "=<Y>,<X>&timestep=<TS>&starttime=2022-08-23T06:00:00Z&endtime=2022-08-24T06:00:00Z&parameters"
            + "=temperature,windspeedms";
    
    /* ! Forecast by default 24h  ! */
    // observations vs. forecast - <OFT>
    // co-ordinates - <X1><X2>, <Y1><Y2>
    // forecast co-ordinates - <X>, <Y>
    // timestep (minutes) - <TS>
    // parameters 
    
    // https://opendata.fmi.fi/wfs?request=getFeature&version=2.0.0&storedquery_id=fmi::observations::weather::simple&bbox=23,61,24,62&timestep=30&parameters=t2m,ws_10min,n_man
    // https://opendata.fmi.fi/wfs?request=getFeature&version=2.0.0&storedquery_id=fmi::observations::weather::hourly::simple&bbox=23,61,24,62&starttime=2021-01-19T09:00:00Z&endtime=2021-01-19T14:00:00Z&parameters=TA_PT1H_AVG,TA_PT1H_MAX,TA_PT1H_MIN
    
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
    
    public static void main(String[] args) throws IOException {
        JSONObject jsobj = getData();
        //setJso(jsobj);
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
