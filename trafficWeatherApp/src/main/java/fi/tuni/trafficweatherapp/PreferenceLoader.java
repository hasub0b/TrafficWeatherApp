package fi.tuni.trafficweatherapp;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.*;
import java.util.Objects;
import fi.tuni.trafficweatherapp.controllers.*;

/**
 * @author Aleksi
 */
public class PreferenceLoader {

    /**
     * Load preference data from file to DataInterface
     * @param filename name of the JSON file
     * @param node Node in GraphView to give access to GraphViewController
     */
    public void load(String filename, Node node){

        try (InputStream in = getClass().getResource("preferences").openStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;
            while ((resource = br.readLine()) != null) {
                // Check if the file is json
                if(resource.equals(filename)){
                    // add the file to comboBox
                    JsonObject jsonObject = convertFileToJSON(resource);
                    loadToGraphview(jsonObject,node);
                }
            }
        } catch (Exception e){
            System.err.println("ERROR WHILE UPDATING DATASETS");
        }
    }

    /**
     * Place loaded data to controllers
     * @param jsonObject JsonObject gotten from convertFileToJSON()
     * @param node Node in GraphView to give access to GraphViewController
     */
    private void loadToGraphview(JsonObject jsonObject, Node node){
        Object controller = null;
        do {
            controller = node.getUserData();
            node = node.getParent();
        } while (controller == null && node != null);

        // Get all the controllers
        MainViewController pc = (MainViewController) controller;
        FXMLLoader graphLoader = pc.getLoaderGraphView();
        GraphViewController gc = graphLoader.getController();
        SideMenuTitleBoxesController sc = gc.loaderSideMenuTitleBoxes.getController();
        WeatherMenuController wc = sc.loaderWeatherMenu.getController();
        CoordinatesMenuController cc = sc.loaderCoordinatesMenu.getController();
        TrafficMenuController tc = sc.loaderTrafficMenu.getController();
        MaintenanceMenuController mc = tc.loaderMaintenanceMenu.getController();
        MessagesMenuController msg = tc.loaderMessageMenu.getController();
        RoadConditionController rc = tc.loaderConditionMenu.getController();

        // Set values from JsonObject

        // GraphView
        // timeline
        try{
            System.out.println(jsonObject);
            String timeline = jsonObject.get("timeline").toString().replaceAll("\"","");
            if (Objects.equals(timeline, "observation")){
                gc.buttonForecast.setSelected(false);
                gc.buttonObservation.setSelected(true);
                gc.button2h.setDisable(true);
                gc.button4h.setDisable(true);
                gc.button6h.setDisable(true);
                gc.button12h.setDisable(true);
            } else {
                gc.buttonForecast.setSelected(true);
                gc.buttonObservation.setSelected(false);
                gc.button2h.setDisable(false);
                gc.button4h.setDisable(false);
                gc.button6h.setDisable(false);
                gc.button12h.setDisable(false);
            }

            // forecast hour
            String forecastH = jsonObject.get("forecastH").toString().replaceAll("\"","");
            if (Objects.equals(forecastH, "")){
                gc.button2h.setSelected(false);
                gc.button4h.setSelected(false);
                gc.button6h.setSelected(false);
                gc.button12h.setSelected(false);
            }
            if (Objects.equals(forecastH, "2h")){
                gc.button2h.setSelected(true);
                gc.button4h.setSelected(false);
                gc.button6h.setSelected(false);
                gc.button12h.setSelected(false);
            }
            if (Objects.equals(forecastH, "4h")){
                gc.button2h.setSelected(false);
                gc.button4h.setSelected(true);
                gc.button6h.setSelected(false);
                gc.button12h.setSelected(false);
            }
            if (Objects.equals(forecastH, "6h")){
                gc.button2h.setSelected(false);
                gc.button4h.setSelected(false);
                gc.button6h.setSelected(true);
                gc.button12h.setSelected(false);
            }
            if (Objects.equals(forecastH, "12h")){
                gc.button2h.setSelected(false);
                gc.button4h.setSelected(false);
                gc.button6h.setSelected(false);
                gc.button12h.setSelected(true);
            }

            // WeatherMenu
            wc.setWind(jsonObject.get("wind").getAsBoolean());
            wc.setCloud(jsonObject.get("cloud").getAsBoolean());
            wc.setTemp(jsonObject.get("temperature").getAsBoolean());

            // TrafficMenu
            // MaintenanceMenu
            mc.setShow(jsonObject.get("showMaintenance").getAsBoolean());
            mc.setSelectedTask(jsonObject.get("maintenance").toString().replaceAll("\"",""));
            // MessagesMenu
            msg.setRoadWork(jsonObject.get("roadwork").getAsBoolean());
            msg.setTransport(jsonObject.get("transport").getAsBoolean());
            msg.setAnnouncement(jsonObject.get("announcement").getAsBoolean());
            msg.setWeightRes(jsonObject.get("weight").getAsBoolean());
            // Items of interest
            rc.setSlipperiness(jsonObject.get("slipperiness").getAsBoolean());
            rc.setCondition(jsonObject.get("overallCondition").getAsBoolean());
            rc.setPrecipitation(jsonObject.get("precipitation").getAsBoolean());
        }catch (Exception e){
            System.out.println("JSON FILE DOESN'T HAVE CORRECT FORMATTING");
            System.out.println(e);
        }
    }

    /**
     * Convert JSON file to JsonObject
     * @param fileName Name of the JSON file
     * @return JsonObject of the file
     */
    private JsonObject convertFileToJSON(String fileName){

        // Read from File to String
        JsonObject jsonObject = new JsonObject();

        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(getClass().getResource("preferences/" + fileName).getFile()));
            jsonObject = jsonElement.getAsJsonObject();
        } catch (IOException e) {
            System.out.println("ERROR WHILE CONVERTING");
        }

        return jsonObject;
    }
}
