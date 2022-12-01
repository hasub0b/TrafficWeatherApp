/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.trafficweatherapp.drawers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.text.Text;
import fi.tuni.trafficweatherapp.*;

/**
 *
 * @author Arttu
 */
public class TrafficMessagesDrawer {
    
    private Text textOutput;
    
    public Text directDraw (Map<String, List<String>> input) {
        textOutput.setText(mapConverter(input));
        return textOutput;
    }
    
    public void test () {
        Map<String, List<String>> testMessageMap = new HashMap<>();
        List<String> alkioListaYksi = new ArrayList<>();
        List<String> alkioListaKaksi = new ArrayList<>();
        alkioListaYksi.add("alkio1.1");
        alkioListaYksi.add("alkio1.2");
        alkioListaYksi.add("alkio1.3");
        alkioListaKaksi.add("alkio2.1");
        alkioListaKaksi.add("alkio2.2");
        alkioListaKaksi.add("alkio2.3");
        testMessageMap.put("Avain 1", alkioListaYksi);
        testMessageMap.put("Avain 2", alkioListaKaksi);
        String convertedMap = mapConverter(testMessageMap);
        System.out.println("Test map: " + convertedMap);
        //return testMessageMap;
    }
    
    public String mapConverter(Map<String, List<String>> input) {
        StringBuilder mapToString = new StringBuilder("\n");
        for (String key : input.keySet()) {
            mapToString.append(key + ": " + input.get(key) + "\n");
        }
        mapToString.delete(mapToString.length()-2, 
                mapToString.length()).append("\n");
        String convertedMap = mapToString.toString();
        return convertedMap;
    }
    
    public Text getRoadConditionData() {
        // what exactly should I fetch
        //DataInterface.get
        //getMessagesMap
        //getMessagesMap
        return getMessageText();
    }
    
    public float getMessageSize() {    
        return DataInterface.getMessagesMap().size();
    }
    public String getMessageString() {    
        return mapConverter(DataInterface.getMessagesMap());
    }
    
    public Text getMessageText() {
        Map<String, List<String>> messageMap = DataInterface.getMessagesMap();
        textOutput.setText(mapConverter(messageMap));
        return textOutput;
    }
}