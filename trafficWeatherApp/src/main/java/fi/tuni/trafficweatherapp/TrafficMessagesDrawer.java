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
    /*
    Can output:
    - Maintenance
    - Road conditions
    - Messages
    */
    private Text textOutput;
    
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
    
    
    public String getMessageString() {
        return messageBuilder(DataInterface.getMessagesMap());
        //return mapConverter(DataInterface.getMessagesMap());
    }

    // Builds road_condition string for use
    // conditionBuilder does the parsing
    public String getRoadConditionString() {
        return conditionBuilder(DataInterface.getItemsOfInterest());
    }
    
    public String messageCounter() {
        Map<String, List<String>> input = DataInterface.getMessagesMap();
        StringBuilder strBuild = new StringBuilder("");
        int count = 0;
        
        if (input != null) {
            // If there's road work selected
            if (DataInterface.isRoadworkSelected()) {
                count += input.get("ROAD_WORK").size();
                System.out.println("RW Size: " + input.get("ROAD_WORK").size());
            }
            // If there's weight restrictions selected
            if (DataInterface.isWeightSelected()) {
                count += input.get("WEIGHT_RESTRICTION").size();
                System.out.println("WR Size: " + input.get("WEIGHT_RESTRICTION").size());
            }
            // If there's exempted transport selected
            if (DataInterface.isTransportSelected()) {
                count += input.get("EXEMPTED_TRANSPORT").size();
                System.out.println("ET Size: " + input.get("EXEMPTED_TRANSPORT").size());
            }
            // If there's traffic announcements selected
            if (DataInterface.isAnnouncementSelected()) {
                count += input.get("TRAFFIC_ANNOUNCEMENT").size();
                System.out.println("TA Size: " + input.get("TRAFFIC_ANNOUNCEMENT").size());
            }
        }
        else {
            System.out.println("Message counter received an empty map.");
            return null;
        }

        strBuild.append("Traffic Messages Amount: " + count);
        return strBuild.toString();
    }
    
    // Counts the elements from a hashmap
    public int getMessageSize() {
        int count = 0;
        try {
            Map<String, List<String>> input = DataInterface.getMessagesMap();
            //return DataInterface.getMessagesMap().size();
            for (List<String> values : input.values()){
                count =+ values.size();
                //System.out.println(values.size());
            }
            return count;
        }
        catch (Exception e) {
            System.out.println("Error during message measuring: " + e);
            return 0;
        }
    }
}