package fi.tuni.trafficweatherapp;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
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

        // set default value in case of no data in datainterface
        if (pieData.size() == 0){
            pieData.add(new PieChart.Data("NO DATA",0));
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

    /**
     * Get the current pie chart
     * @return PieChart current PieChart
     */
    public PieChart getPieChart() {
        return pieChart;
    }
}

