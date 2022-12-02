/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
/**
 *
 * @author Arttu Lehtola
 */
public class TrafficMessagesDrawer {
    
    private Text textOutput;
    
    /**
     * Builds a message string
     * @param input input map, where strings are fetched from
     * @return returns a formatted string with all wanted values
     */
    public String messageBuilder(Map<String, List<String>> input) {
        StringBuilder mapToString = new StringBuilder("\n");
        try {
        if (input != null) {
            // If there's road work selected
            if (DataInterface.isRoadworkSelected()) {
                mapToString.append("ROAD_WORK:\n" + input.get("ROAD_WORK")+"\n");
            }
            // If there's weight restrictions selected
            if (DataInterface.isWeightSelected()) {
                mapToString.append("WEIGHT_RESTRICTION:\n" + input.get("WEIGHT_RESTRICTION")+"\n");
            }
            // If there's exempted transport selected
            if (DataInterface.isTransportSelected()) {
                mapToString.append("EXEMPTED_TRANSPORT:\n" + input.get("EXEMPTED_TRANSPORT")+"\n");
            }
            // If there's traffic announcements selected
            if (DataInterface.isAnnouncementSelected()) {
                mapToString.append("TRAFFIC_ANNOUNCEMENT:\n" + input.get("TRAFFIC_ANNOUNCEMENT")+"\n");
            }
        }
        else {
            return null;
        }
        
        } catch (Exception e) {
            System.out.println("Error when building message: " + e);
            return null;
        }
        return mapToString.toString();
    }
    
    /**
     * Creates string from road condition datamap 
     * @param input input map where data is extracted from to a string
     * @return returns a full structured string ready for use
     */
    public String conditionBuilder(Map<String, List<String>> input) {
        StringBuilder mapToString = new StringBuilder("");
        List<String> inputElement = null;
        // Value index 0,1,2,3,4 is Observation,2h,4h,6h,12h
        int index = 0;
        if (DataInterface.isObservationSelected()) {
            index = 0;
        }
        else if (DataInterface.getSelectedForecast().contains("12h")) {
            index = 4;
        }
        else if (DataInterface.getSelectedForecast().contains("6h")) {
            index = 3;
        }
        else if (DataInterface.getSelectedForecast().contains("4h")) {
            index = 2;
        }
        else if (DataInterface.getSelectedForecast().contains("2h")) {
            index = 1;
        }
        else {
            System.out.println("Error building conditions due to boolean error.");
        }
        try {
            if (input != null) {
                
                // If there's road work selected
                if (DataInterface.isPrecipitationSelected()) {
                    inputElement = input.get("Precipitation");
                    mapToString.append("Precipitation:\n" + inputElement.get(index)+"\n");
                }
                // If there's weight restrictions selected
                if (DataInterface.isOverallConditionSelected()) {
                    inputElement = input.get("OverallCondition");
                    mapToString.append("Overall Condition:\n" + inputElement.get(index)+"\n");
                }
                // If there's exempted transport selected
                if (DataInterface.isSlipperinessSelected()) {
                    inputElement = input.get("WinterSlipperiness");
                    mapToString.append("Winter Slipperiness:\n" + inputElement.get(index)+"\n");
                }
            }
            else {
                return null;
            }

        } catch (Exception e) {
            System.out.println("Error when building message: " + e);
            return null;
        }
        return mapToString.toString();
    }
    
    /**
     * Fetches a message string from builder
     * @return returns a string parsed by the messageBuilder
     */
    public String getMessageString() {
        return messageBuilder(DataInterface.getMessagesMap());
        //return mapConverter(DataInterface.getMessagesMap());
    }

    /**
     * Fetches a road conditions string for use
     * @return returns a fully structured string with road data
     */
    public String getRoadConditionString() {
        return conditionBuilder(DataInterface.getItemsOfInterest());
    }
    
    /**
     * Counts the number of messages in a map
     * @return returns count, integer for the number of messages counted
     */
    public String messageCounter() {
        Map<String, List<String>> input = DataInterface.getMessagesMap();
        StringBuilder strBuild = new StringBuilder("");
        int count = 0;
        
        if (input != null) {
            // If there's road work selected
            if (DataInterface.isRoadworkSelected()) {
                count += input.get("ROAD_WORK").size();
            }
            // If there's weight restrictions selected
            if (DataInterface.isWeightSelected()) {
                count += input.get("WEIGHT_RESTRICTION").size();
            }
            // If there's exempted transport selected
            if (DataInterface.isTransportSelected()) {
                count += input.get("EXEMPTED_TRANSPORT").size();
            }
            // If there's traffic announcements selected
            if (DataInterface.isAnnouncementSelected()) {
                count += input.get("TRAFFIC_ANNOUNCEMENT").size();
            }
        }
        else {
            System.out.println("Message counter received an empty map.");
            return null;
        }

        strBuild.append("Traffic Messages Amount: " + count);
        return strBuild.toString();
    }
}