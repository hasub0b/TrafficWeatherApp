package fi.tuni.trafficweatherapp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PieDrawer  {

    public PieChart pieChart;

    public PieDrawer() throws IOException, ParseException {

        pieChart = new PieChart();

        // Setting data from DataInterface
        List<PieChart.Data> data = new ArrayList<>();
        for (String key: DataInterface.getMaintenanceMap().keySet()) {
            data.add(new PieChart.Data(key, DataInterface.getMaintenanceMap().get(key)));
        }

        ObservableList<PieChart.Data> oList = FXCollections.observableArrayList(data);
        pieChart.setData(oList);
        //Setting the other properties
        pieChart.setTitle("Task type amounts");
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(10);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(360);
    }

    public PieChart getPieChart() {
        return pieChart;
    }
}

