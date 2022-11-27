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
            //loadToGraphview(jsonObject,node);
            loadToGraphview(node);
        }

    }
    private void loadToGraphview( Node node){
        // TODO: add values to corresponding controllersAd
        Object controller = null;
        do {
            controller = node.getUserData();
            node = node.getParent();
        } while (controller == null && node != null);

        System.out.println(controller);
        PrimaryController pc = (PrimaryController) controller;
        System.out.println(pc);
        FXMLLoader graphLoader = pc.getLoaderGraphView();
        GraphViewController gc = graphLoader.getController();
        SideMenuTitleBoxesController sc = gc.loaderSideMenuTitleBoxes.getController();
        TrafficMenuController tc = sc.loaderTrafficMenu.getController();
        WeatherMenuController wc = sc.loaderWeatherMenu.getController();
        CoordinatesMenuController cc = sc.loaderCoordinatesMenu.getController();
        gc.buttonForecast.setSelected(true);
    }

    private void loadToGraphview(JsonObject jsonObject, Node node){
        // TODO: add values to corresponding controllersAd
        Object controller = null;
        do {
            controller = node.getUserData();
            node = node.getParent();
        } while (controller == null && node != null);

        System.out.println(controller);
        PrimaryController pc = (PrimaryController) controller;
        System.out.println(pc);
        FXMLLoader graphLoader = pc.getLoaderGraphView();
        GraphViewController gc = graphLoader.getController();
        SideMenuTitleBoxesController sc = gc.loaderSideMenuTitleBoxes.getController();
        TrafficMenuController tc = sc.loaderTrafficMenu.getController();
        WeatherMenuController wc = sc.loaderWeatherMenu.getController();
        CoordinatesMenuController cc = sc.loaderCoordinatesMenu.getController();
        gc.buttonForecast.setSelected(true);
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
