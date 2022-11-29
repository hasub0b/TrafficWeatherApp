/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import java.io.File;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
/**
 *
 * @author Arttu
 */
public class TrafficMessagesDrawer {
    
    private Text textOutput;
    
    public Text messageTest (Map<String, List<String>> input) {
        textOutput.setText(mapConverter(input));
        return textOutput;
    }
    
    public String mapConverter(Map<String, List<String>> input) {
        StringBuilder mapToString = new StringBuilder("");
        for (String key : input.keySet()) {
            mapToString.append(key + ":" + input.get(key) + "\n ");
        }
        mapToString.delete(mapToString.length()-2, 
                mapToString.length()).append("");
        String convertedMap = mapToString.toString();
        return convertedMap;
    }
    
    public Text getText() {
        Map<String, List<String>> messageMap = DataInterface.getMessagesMap();
        textOutput.setText(mapConverter(messageMap));
        return textOutput;
    }
}