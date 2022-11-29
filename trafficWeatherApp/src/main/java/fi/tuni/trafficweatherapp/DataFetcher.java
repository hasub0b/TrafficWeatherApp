/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import java.io.IOException;
import java.text.ParseException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author mikko
 */
class DataFetcher {

    Double x1, x2, y1, y2, x1x2, y1y2;

    public DataFetcher(Double[] coordinates) {
        x1 = coordinates[0];
        x2 = coordinates[1];
        y1 = coordinates[2];
        y2 = coordinates[3];
        x1x2 = (x1 + x2) / 2;
        y1y2 = (y1 + y2) / 2;
    }

    public void fetchWeatherData() {

        try {
            WeatherDataApiFetcher.getForecastData(x1x2.toString(), y1y2.toString(), "60");
            WeatherDataApiFetcher.getObservationData(x1.toString(), x2.toString(), y1.toString(), y2.toString(), "60");
        } catch (IOException ex) {
            System.err.println("Error: Cannot get data from API");
        } catch (ParserConfigurationException | SAXException ex) {
            System.err.println("Error: Cannot parse data from fmi API");
        }

    }

    public void fetchRoadData() {

        try {
            RoadDataApiFetcher.getRoadMaintenanceData(x1.toString(), y1.toString(), x2.toString(), y2.toString());
            RoadDataApiFetcher.getRoadConditions(x1.toString(), y1.toString(), x2.toString(), y2.toString());
            RoadDataApiFetcher.getLatestTrafficMessages();
        } catch (IOException ex) {
            System.err.println("Error: Cannot get data from Digitraffic API");
        } catch (ParseException ex) {
            System.err.println("Error: Cannot parse data from Digitraffic API");
        }

    }
}
