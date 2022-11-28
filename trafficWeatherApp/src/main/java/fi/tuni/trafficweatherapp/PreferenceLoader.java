package fi.tuni.trafficweatherapp;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class PreferenceLoader {

    public void load(String filename, Node node) throws IOException {

        Path dir = Paths.get("trafficWeatherApp/savedData/preferences/");
        String file = "";
        try ( DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                if(Objects.equals(filename, path.getFileName().toString())){
                    file = path.toString();
                }
            }
            if (file == ""){
                System.out.println("FILE NOT FOUND");
            }
            JsonObject jsonObject = convertFileToJSON(file);
            loadToGraphview(jsonObject,node);

        }

    }


    private void loadToGraphview(JsonObject jsonObject, Node node){
        // TODO: add values to corresponding controllersAd
        Object controller = null;
        do {
            controller = node.getUserData();
            node = node.getParent();
        } while (controller == null && node != null);

        // Get all the controllers
        PrimaryController pc = (PrimaryController) controller;
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
        }


    }

    private JsonObject convertFileToJSON(String fileName){

        // Read from File to String
        JsonObject jsonObject = new JsonObject();

        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(fileName));
            jsonObject = jsonElement.getAsJsonObject();
        } catch (IOException e) {
            System.out.println("ERROR WHILE CONVERTING");
        }


        return jsonObject;
    }


}
