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
import javax.xml.parsers.ParserConfigurationException;
import org.json.XML;
import org.json.JSONObject;
import org.xml.sax.SAXException;


/**
 * 
 * @author Arttu Lehtola
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
            + "=<TS>&parameters=t2m,ws_10min,n_man,r_1h";

    /* 
    * We want to fetch: 
    *   - temperatures (temperature)
    *   - predicted windspeeds (windspeedms)
    */
    static String dynamicWeatherForecastDataString = "https://opendata.fmi.fi/wfs?request"
            + "=getFeature&version=2.0.0&storedquery_id=fmi::forecast::harmonie::surface::point::simple&latlon"
            + "=<Y>,<X>&timestep=<TS>&parameters"
            + "=temperature,windspeedms,precipitationamount,TotalCloudCover";
    
    /**
    * Makes connection to the API
    * @param url URL used for the API source
    * @throws IOException IOException on read/write errors
    * @throws MalformedURLException MalformedURLException, on URL issue(s)
    */
    private static HttpURLConnection connectToApi(String urlParameter) throws MalformedURLException, 
            IOException {
        var url = (new URL(urlParameter));
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.connect();
        //System.out.println(urlConnection.getResponseCode());
        
        return urlConnection;
    }
    
    /**
    * Fetches forecast data from the API and then passes it to parser.
    * @param x          x-coordinate used in the fetch
    * @param y          y-coordinate used in the fetch
    * @param timestep   timestep is the chosen time frequency of the data
    * @throws MalformedURLException MalformedURLException, on URL issue(s)
    * @throws IOException IOException on read/write errors
    * @throws ParserConfigurationException ParserConfigurationException on parser issues
    * @throws SAXException SAXException problems faced while parsing a XML file
    */
    public static void getForecastData(String x, String y, String timestep) throws MalformedURLException,
            IOException, 
            ParserConfigurationException, 
            SAXException 
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
        
        JsonParsing.parseXml(jso);
        //return jso;
    }
    
    /**
    * Fetches observation data from the API and then passes it to parser.
    * The four coordinates can be used to form a rectangle, an area of sorts
    * @param x1          first x-coordinate used in the fetch
    * @param y1          first y-coordinate used in the fetch
    * @param x2          second x-coordinate used in the fetch
    * @param y2          second y-coordinate used in the fetch
    * @param timestep   timestep is the chosen time frequency of the data
    * @throws MalformedURLException MalformedURLException, on URL issue(s)
    * @throws IOException IOException on read/write errors
    * @throws ParserConfigurationException ParserConfigurationException on parser issues
    * @throws SAXException SAXException problems faced while parsing a XML file
    */
    public static void getObservationData(String x1, String y1, String x2, 
            String y2, String timestep) throws MalformedURLException,
            IOException, 
            ParserConfigurationException, 
            SAXException 
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
        
        JsonParsing.parseXml(jso);
        
    }


}
