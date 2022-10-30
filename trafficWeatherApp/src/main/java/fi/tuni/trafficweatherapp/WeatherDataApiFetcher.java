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
    *   - predicted temperatures (temperature)
    *   - predicted windspeeds (windspeedms)
    */
    static String dynamicWeatherForecastDataString = "https://opendata.fmi.fi/wfs?request"
            + "=getFeature&version=2.0.0&storedquery_id=fmi::forecast::harmonie::surface::point::simple&latlon"
            + "=<Y>,<X>&timestep=<TS>&parameters"
            + "=temperature,windspeedms";
    
    /* ! Forecast by default 24h  ! */
    // observations vs. forecast - <OFT>
    // co-ordinates - <X1><X2>, <Y1><Y2>
    // forecast co-ordinates - <X>, <Y>
    // timestep (minutes) - <TS>
    // parameters 
    
    // OBS: https://opendata.fmi.fi/wfs?request=getFeature&version=2.0.0&storedquery_id=fmi::observations::weather::simple&bbox=23,61,24,62&timestep=30&parameters=t2m,ws_10min,n_man
    // FC: https://opendata.fmi.fi/wfs?request=getFeature&version=2.0.0&storedquery_id=fmi::observations::weather::hourly::simple&bbox=23,61,24,62&starttime=2021-01-19T09:00:00Z&endtime=2021-01-19T14:00:00Z&parameters=TA_PT1H_AVG,TA_PT1H_MAX,TA_PT1H_MIN
    
    // JSONObject -variable & accessors
    private JSONObject forecastObject = null;
    private JSONObject observationsObject = null;
    
    public JSONObject getFCO() {
        return forecastObject;
    }
    public void setFCO(JSONObject newFCO) {
        forecastObject = newFCO;
    }
    
    public JSONObject getOO() {
        return observationsObject;
    }
    public void setOO(JSONObject newOO) {
        observationsObject = newOO;
    }
    
    public static void main(String[] args) throws IOException {
        
        JSONObject forecastResults = getForecastData("23.78712", "61.49911", "30");
        System.out.println("Forecast: " + forecastResults);
        
        JSONObject observationResults = getObservationData("23", "61", "24", "62", "30");
        System.out.println("Observation: " + observationResults);
    }
    
    private static HttpURLConnection connectToApi(String urlParameter) throws MalformedURLException, 
            IOException {
        var url = (new URL(urlParameter));
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.connect();
        //System.out.println(urlConnection.getResponseCode());
        
        return urlConnection;
    }
    

    public static JSONObject getForecastData(String x, String y, String timestep) throws MalformedURLException,
            IOException 
    {
        
        String urlString = dynamicWeatherForecastDataString
                    .replace("<Y>", y)
                    .replace("<X>", x)
                    .replace("<TS>", timestep);
        
        HttpURLConnection urlConnection = connectToApi(urlString);
   
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
    
    public static JSONObject getObservationData(String x1, String y1, String x2, 
            String y2, String timestep) throws MalformedURLException,
            IOException 
    {
        
        String urlString = dynamicWeatherObservationDataString
                    .replace("<Y1>", y1)
                    .replace("<X1>", x1)
                    .replace("<Y2>", y2)
                    .replace("<X2>", x2)
                    .replace("<TS>", timestep);
        
        HttpURLConnection urlConnection = connectToApi(urlString);
   
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
