package fi.tuni.trafficweatherapp;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksi
 */
public class PieDrawer  {

    public PieChart pieChart;

    /**
     * Create a new PieChart
     */
    public PieDrawer() {

        pieChart = new PieChart();

        // Setting data from DataInterface
        List<PieChart.Data> pieData = new ArrayList<>();
        for (String key: DataInterface.getMaintenanceMap().keySet()) {
            pieData.add(new PieChart.Data(key, DataInterface.getMaintenanceMap().get(key)));
        }
        for (PieChart.Data dat:pieData) {
            dat.nameProperty().bind(Bindings.concat(String.format("%s - %.0f",dat.getName(), dat.pieValueProperty().getValue())));
        }

        ObservableList<PieChart.Data> oList = FXCollections.observableArrayList(pieData);
        pieChart.setData(oList);

        // Tooltip
        pieChart.getData().stream().forEach(data -> {
            Tooltip tooltip = new Tooltip();
            tooltip.setText(data.getName());
            Tooltip.install(data.getNode(), tooltip);
            data.pieValueProperty().addListener((observable, oldValue, newValue) ->
                    tooltip.setText(data.getName()));
        });

        //Setting the other properties
        pieChart.setTitle("Task type amounts");
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(10);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(360);
    }

    // return the current chart
    public PieChart getPieChart() {
        return pieChart;
    }
}

