package fi.tuni.trafficweatherapp;

import java.util.*;

/**
 * @author Aleksi
 */
public final class DataInterface {

    // Values from DigiTraffic
    //
    // Contains all road conditions, index 0 is no forecast and for indexes 1,2,3,4 forecast is 2h,4h,6h,12h
    private static List<String> conditionForecast = new ArrayList<String>();;
    // Key Precipitation/OverallCondition/(WinterSlipperiness) and Value index 0,1,2,3 is 2h,4h,6h,12h
    private static Map<String, List<String>> itemsOfInterest = new HashMap<>();
    // maintenance list is replaced by maintenanceMap
    private static List<String> maintenance = new ArrayList<String>();
    // Key maintenance type
    private static Map<String,Integer> maintenanceMap = new HashMap<>();
    // messages list is replaced by messagesMap
    private static List<String> messages = new ArrayList<String>();
    // Key "TRAFFIC_ANNOUNCEMENT", "EXEMPTED_TRANSPORT", "WEIGHT_RESTRICTION", "ROAD_WORK"
    private static Map<String, List<String>> messagesMap = new HashMap<>();

    // List containing all possible maintenance types, should currently only be used by MaintenanceMenuController
    private static List<String> allTaskTypes = new ArrayList<>();

    // Values from FMI
    //
    // forecast lists have next 24h in 30min steps --- 48 elements -> index 0 next xx:30 or xx:00 and 48th element is 24h from index 0's time
    private static List<Float> forecastTemperature = new ArrayList<>();
    private static List<Float> forecastRain = new ArrayList<>();
    private static List<Float> forecastWind = new ArrayList<>();
    // Observed data
    private static Double temperature;
    private static String wind;
    private static Double rain;
    private static Double cloud;

    // parameters for user selections
    //GraphView
    boolean observationSelected = false;
    int selectedForecast = 0;
    // WeatherMenuController
    boolean windSelected = false;
    boolean temperatureSelected = false;
    boolean cloudSelected = false;
    // TrafficMenu
    boolean maintenanceSelected = false;
    String selectedMaintenance = "";
    boolean conditionSelected = false;
    boolean announcementSelected = false;
    boolean transportSelected = false;
    boolean weightSelected = false;
    boolean roadworkSelected = false;
    boolean precipitationSelected = false;
    boolean slipperinessSelected = false;
    boolean overallConditionSelected = false;






    // Accessors

    // DigiTraffic

    public static List<String> getMaintenance() {return maintenance;}
    public static void setMaintenance(List<String> newMaintenance) {maintenance = newMaintenance;}
    public static void addMaintenance(String newMaintenance) {maintenance.add(newMaintenance);}

    public static List<String> getConditionForecast() {return conditionForecast;}
    public static void setConditionForecast(List<String> newConditionForecast) {conditionForecast = newConditionForecast;}
    public static void addConditionForecast(String newConditionForecast) {conditionForecast.add(newConditionForecast);}

    public static List<String> getMessages() {return messages;}
    public static void setMessages(List<String> newMessages) {messages = newMessages;}
    public static void addMessage(String newMessage) {messages.add(newMessage);}

    public static Map<String, List<String>> getItemsOfInterest() {return itemsOfInterest;}
    public static void setItemsOfInterest(Map<String, List<String>> newItemsOfInterest) {itemsOfInterest = newItemsOfInterest;}
    public static void addItemOfInterest(String key, String value) {
        if (!itemsOfInterest.containsKey(key)){itemsOfInterest.put(key, new ArrayList<>());}
        itemsOfInterest.get(key).add(value);
    }

    public static Map<String, List<String>> getMessagesMap() {return messagesMap;}
    public static void setMessagesMap(Map<String, List<String>> messagesMap) {DataInterface.messagesMap = messagesMap;}

    public static Map<String, Integer> getMaintenanceMap() {return maintenanceMap;}
    public static void setMaintenanceMap(Map<String, Integer> maintenanceMap) {DataInterface.maintenanceMap = maintenanceMap;}

    // FMI

    public static List<Float> getForecastTemperature() {return forecastTemperature;}
    public static void setForecastTemperature(List<Float> newForecastTemperature) {forecastTemperature = newForecastTemperature;}
    public static void addForecastTemperature(Float newForecastTemperature) {forecastTemperature.add(newForecastTemperature);}

    public static Double getTemperature() {return temperature;}
    public static void setTemperature(Double newTemperature) {temperature = newTemperature;}

    public static List<Float> getForecastWind() {return forecastWind;}
    public static void setForecastWind(List<Float> newForecastWind) {forecastWind = newForecastWind;}
    public static void addForecastWind(Float newForecastWind) {forecastWind.add(newForecastWind);}

    public static String getWind() {return wind;}
    public static void setWind(String newWind) {wind = newWind;}

    public static List<Float> getForecastRain() {return forecastRain;}
    public static void setForecastRain(List<Float> newForecastRain) {forecastRain = newForecastRain;}
    public static void addForecastRain(Float newForecastRain) {forecastRain.add(newForecastRain);}

    public static Double getRain() {return rain;}
    public static void setRain(Double newRain) {rain = newRain;}

    public static Double getCloud() {return cloud;}
    public static void setCloud(Double newCloud) {cloud = newCloud;}


    public static List<String> getAllTaskTypes() {return allTaskTypes;}
    public static void setAllTaskTypes(List<String> allTaskTypes) {DataInterface.allTaskTypes = allTaskTypes;}


    public boolean isObservationSelected() {return observationSelected;}
    public void setObservationSelected(boolean observationSelected) {this.observationSelected = observationSelected;}

    public int getSelectedForecast() {return selectedForecast;}
    public void setSelectedForecast(int selectedForecast) {this.selectedForecast = selectedForecast;}

    public boolean isWindSelected() {return windSelected;}
    public void setWindSelected(boolean windSelected) {this.windSelected = windSelected;}

    public boolean isTemperatureSelected() {return temperatureSelected;}
    public void setTemperatureSelected(boolean temperatureSelected) {this.temperatureSelected = temperatureSelected;}

    public boolean isCloudSelected() {return cloudSelected;}
    public void setCloudSelected(boolean cloudSelected) {this.cloudSelected = cloudSelected;}

    public boolean isMaintenanceSelected() {return maintenanceSelected;}
    public void setMaintenanceSelected(boolean maintenanceSelected) {this.maintenanceSelected = maintenanceSelected;}

    public String getSelectedMaintenance() {return selectedMaintenance;}
    public void setSelectedMaintenance(String selectedMaintenance) {this.selectedMaintenance = selectedMaintenance;}

    public boolean isConditionSelected() {return conditionSelected;}
    public void setConditionSelected(boolean conditionSelected) {this.conditionSelected = conditionSelected;}

    public boolean isAnnouncementSelected() {return announcementSelected;}
    public void setAnnouncementSelected(boolean announcementSelected) {this.announcementSelected = announcementSelected;}

    public boolean isTransportSelected() {return transportSelected;}
    public void setTransportSelected(boolean transportSelected) {this.transportSelected = transportSelected;}

    public boolean isWeightSelected() {return weightSelected;}
    public void setWeightSelected(boolean weightSelected) {this.weightSelected = weightSelected;}

    public boolean isRoadworkSelected() {return roadworkSelected;}
    public void setRoadworkSelected(boolean roadworkSelected) {this.roadworkSelected = roadworkSelected;}

    public boolean isPrecipitationSelected() {return precipitationSelected;}
    public void setPrecipitationSelected(boolean precipitationSelected) {this.precipitationSelected = precipitationSelected;}

    public boolean isSlipperinessSelected() {return slipperinessSelected;}
    public void setSlipperinessSelected(boolean slipperinessSelected) {this.slipperinessSelected = slipperinessSelected;}

    public boolean isOverallConditionSelected() {return overallConditionSelected;}
    public void setOverallConditionSelected(boolean overallConditionSelected) {this.overallConditionSelected = overallConditionSelected;}


    // Get all values for testing
    public static String getAll() {
        String stringTraffic = "DigiTraffic data: \nconditionForecast: " + getConditionForecast() + "\nitems of interest: " + getItemsOfInterest() + "\nmaintenance: " + getMaintenance()
                + "\nmessages: " + getMessages();

        String stringFmi = "FMI data:\nforecastTemperature: " + getForecastTemperature() + "\n forecastWind: " + getForecastWind() + "\n forecastRain: " + getForecastRain()
                + "\n temperature: " + getTemperature() + "\n wind: " + getWind() + "\n rain: " + getRain() + "\n cloud: " +getCloud();
        return stringTraffic + "\n" + stringFmi;
    }
}
