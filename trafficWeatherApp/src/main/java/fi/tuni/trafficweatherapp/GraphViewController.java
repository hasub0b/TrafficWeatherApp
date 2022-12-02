/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.RadioButton;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the Graph View's fxml javafx elements.
 *
 * @author Mikko Moisio
 */
public class GraphViewController {

    @FXML
    ImageView sideMenu;
    @FXML
    StackPane stackPaneGraph;
    @FXML
    AnchorPane anchorGraphView;
    @FXML
    AnchorPane anchorSideMenu;
    @FXML
    AnchorPane anchorSideMenuBoxes;
    @FXML
    VBox vbox;
    @FXML
    HBox hbox;
    @FXML
    RadioButton button2h;
    @FXML
    RadioButton button4h;
    @FXML
    RadioButton button6h;
    @FXML
    RadioButton button12h;
    @FXML
    RadioButton buttonForecast;
    @FXML
    RadioButton buttonObservation;
    //@FXML Rectangle shapeChartBackground;
    @FXML
    LineChart chartLine;
    @FXML
    BarChart chartHistogram;
    @FXML
    BarChart chartIcons;
    @FXML
    TextArea textAreaTrafficMessages;
    @FXML
    TextArea textAreaRoadConditionData;
    @FXML
    TextArea textAreaAvgMaintenanceTasks;
    @FXML
    PieChart pieChartTaskTypes;
    @FXML
    TextField fieldCoordinates;
    @FXML
    Button buttonUpdateGraph;
    @FXML
    GridPane gridLower;

    ContextMenu menuErrorMessage = new ContextMenu();

    Tooltip tipSideMenu = new Tooltip("Graph settings");

    GraphDrawerFactory graphFactory = new GraphDrawerFactory();
    TrafficMessagesDrawer tmd = new TrafficMessagesDrawer();

    ToggleGroup groupTimeline = new ToggleGroup();
    ToggleGroup groupForecastOptions = new ToggleGroup();

    @FXML
    FXMLLoader loaderSideMenuTitleBoxes = new FXMLLoader(
            getClass().getResource("sideMenuTitleBoxes.fxml"));

    /**
     * Initializes graph view's elements.
     *
     * @throws Exception If cannot create graphs
     */
    public void initialize() throws Exception {
        sideMenu.getStyleClass().add("menu");

        buttonForecast.setToggleGroup(groupTimeline);
        buttonObservation.setToggleGroup(groupTimeline);

        button2h.setToggleGroup(groupForecastOptions);
        button4h.setToggleGroup(groupForecastOptions);
        button6h.setToggleGroup(groupForecastOptions);
        button12h.setToggleGroup(groupForecastOptions);

        try {
            anchorSideMenuBoxes = loaderSideMenuTitleBoxes.load();
        } catch (IOException e) {
            System.out.println(e);
        }
        tipSideMenu.setShowDelay(Duration.seconds(0.3));
        Tooltip.install(sideMenu, tipSideMenu);

        // Action Events
        sideMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (!anchorSideMenu.getChildren().isEmpty()) {
                anchorSideMenu.getChildren().clear();
            } else {
                anchorSideMenu.getChildren().add(anchorSideMenuBoxes);
            }
        });

        buttonForecast.setOnAction(event -> this.timelineRadioButtonEvent(event));
        buttonObservation.setOnAction(event -> this.timelineRadioButtonEvent(event));
        button2h.setOnAction(event -> this.forecastRadioButtonEvent(event));
        button4h.setOnAction(event -> this.forecastRadioButtonEvent(event));
        button6h.setOnAction(event -> this.forecastRadioButtonEvent(event));
        button12h.setOnAction(event -> this.forecastRadioButtonEvent(event));

        buttonUpdateGraph.setOnAction(event -> {
            try {
                updateGraphView();
            } catch (Exception ex) {
                System.err.println("Error: Cannot update GraphView!");
            }
        });

        // CHARTS
        // init icons chart
        chartIcons.getData().add(new XYChart.Series<>());
        chartIcons.getYAxis().setOpacity(0);
        chartIcons.getXAxis().setOpacity(0);
        for (Node n : chartIcons.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: transparent;");
        }

        // init line chart
        chartLine.getYAxis().setSide(Side.RIGHT);
        chartLine.getYAxis().setLabel("Temperature (C\u00B0)");
        chartLine.getXAxis().setLabel("Time (hh:mm)");
        chartLine.getData().add(new XYChart.Series<>());

        // init bar chart
        chartHistogram.getYAxis().setLabel("Rain amount (mm)");
        chartHistogram.lookup(".chart-plot-background").setStyle("-fx-background-color: #C8B6E2;");
        chartHistogram.getData().add(new XYChart.Series<>());

        chartHistogram.setVisible(false);
        chartLine.setVisible(false);
        chartIcons.setVisible(false);
        textAreaTrafficMessages.setVisible(false);
        textAreaRoadConditionData.setVisible(false);
        textAreaAvgMaintenanceTasks.setVisible(false);
        pieChartTaskTypes.setVisible(false);
    }

    /**
     * Controls timeline's radiobutton events.
     *
     * @param event itself
     * @hidden
     */
    private void timelineRadioButtonEvent(ActionEvent event) {
        if (buttonForecast.isSelected()) {
            button2h.setDisable(false);
            button4h.setDisable(false);
            button6h.setDisable(false);
            button12h.setDisable(false);
            DataInterface.setObservationSelected(false);
        } else {
            button2h.setDisable(true);
            button4h.setDisable(true);
            button6h.setDisable(true);
            button12h.setDisable(true);
            DataInterface.setObservationSelected(true);
        }
    }

    /**
     * Controls forecast timeline button event
     *
     * @param event itself
     * @hidden
     */
    private void forecastRadioButtonEvent(ActionEvent event) {
        System.out.println(getForecastStatus());
        DataInterface.setSelectedForecast(getForecastStatus());
    }

    /**
     * Gets the timeline observation status
     *
     * @return Is data observed or forecast.
     */
    private boolean getTimelineStatus() {
        return groupTimeline.getSelectedToggle() == buttonObservation;
    }

    /**
     * Checks what forecast timeline button is selected.
     *
     * @return String of what forecast timeline button is selected.
     */
    private String getForecastStatus() {
        if (getTimelineStatus()) {
            return null;
        } else {
            RadioButton selectedRadioButton
                    = (RadioButton) groupForecastOptions.getSelectedToggle();
            return selectedRadioButton.getText();
        }
    }

    /**
     * Updates the graph view
     *
     * @throws Exception if cannot create graph.
     */
    private void updateGraphView() throws Exception {
        Double[] coordinates = DataInterface.getCoordinates();

        // Clear old error messages
        menuErrorMessage.getItems().clear();
        menuErrorMessage.hide();

        if (coordinates != null) {
            fieldCoordinates.setText(String.format("%s, %s, %s, %s",
                    coordinates[0].toString(),
                    coordinates[1].toString(),
                    coordinates[2].toString(),
                    coordinates[3].toString()));

            // Default visibility
            chartHistogram.setVisible(false);
            chartLine.setVisible(false);
            chartIcons.setVisible(false);
            textAreaTrafficMessages.setVisible(false);
            textAreaRoadConditionData.setVisible(false);
            textAreaAvgMaintenanceTasks.setVisible(false);
            pieChartTaskTypes.setVisible(false);
            
            chartHistogram.lookup(".chart-plot-background")
                    .setStyle("-fx-background-color: transparent;");
            chartLine.lookup(".chart-plot-background")
                    .setStyle("-fx-background-color: transparent;");
            chartIcons.lookup(".chart-plot-background")
                    .setStyle("-fx-background-color: transparent;");

            chartLine.getData().set(0, graphFactory.createPlot());
            chartHistogram.getData().set(0, graphFactory.createHistogram());
            chartIcons.getData().set(0, graphFactory.createIcons());
            for (Node n : chartIcons.lookupAll(".default-color0.chart-bar")) {
                n.setStyle("-fx-bar-fill: transparent;");
            }

            // Weather 
            if (DataInterface.isWindSelected()) {
                chartIcons.setVisible(true);
            }
            if (DataInterface.isCloudSelected()) {
                chartIcons.setVisible(true);
            }
            if (DataInterface.isRainSelected()) {
                chartHistogram.setVisible(true);
                chartHistogram.lookup(".chart-plot-background").setStyle("-fx-background-color: #C8B6E2;");
            } // Checking for background in charts
            else {
                if (DataInterface.isTemperatureSelected()) {
                    chartLine.lookup(".chart-plot-background").setStyle("-fx-background-color: #C8B6E2;");
                } else {
                    chartIcons.lookup(".chart-plot-background").setStyle("-fx-background-color: #C8B6E2;");
                }

            }
            if (DataInterface.isTemperatureSelected()) {
                chartLine.setVisible(true);
            }

            // Road data
            // Road condition
            textAreaRoadConditionData.setText(tmd.getRoadConditionString());

            if (DataInterface.isPrecipitationSelected()
                    | DataInterface.isOverallConditionSelected()
                    | DataInterface.isSlipperinessSelected()) {
                textAreaRoadConditionData.setVisible(true);
            }

            // Messages
            textAreaTrafficMessages.setText(tmd.messageCounter());

            if (DataInterface.isAnnouncementSelected()
                    | DataInterface.isTransportSelected()
                    | DataInterface.isWeightSelected()
                    | DataInterface.isRoadworkSelected()) {
                textAreaTrafficMessages.setVisible(true);
            }

            // Maintenance
            if (DataInterface.isMaintenanceSelected()) {
                textAreaAvgMaintenanceTasks.setVisible(true);
                pieChartTaskTypes.setVisible(true);

                // create pie chart
                pieChartTaskTypes.getData().clear();
                // Setting data from DataInterface
                List<PieChart.Data> pieData = new ArrayList<>();
                for (String key : DataInterface.getMaintenanceMap().keySet()) {
                    pieData.add(new PieChart.Data(key, DataInterface.getMaintenanceMap().get(key)));
                }
                for (PieChart.Data dat : pieData) {
                    dat.nameProperty().bind(Bindings.concat(String.format("%s - %.0f",
                            dat.getName(), dat.pieValueProperty().getValue())));
                }

                // set default value in case of no data in datainterface
                if (pieData.size() == 0) {
                    pieData.add(new PieChart.Data("NO DATA", 0));
                }

                ObservableList<PieChart.Data> oList = FXCollections.observableArrayList(pieData);
                pieChartTaskTypes.setData(oList);

                // Tooltip
                pieChartTaskTypes.getData().stream().forEach(data -> {
                    Tooltip tooltip = new Tooltip();
                    tooltip.setShowDelay(Duration.ZERO);
                    tooltip.setText(data.getName());
                    Tooltip.install(data.getNode(), tooltip);
                    data.pieValueProperty().addListener((observable, oldValue, newValue)
                            -> tooltip.setText(data.getName()));
                });

                //Setting the other properties
                pieChartTaskTypes.setClockwise(true);
                pieChartTaskTypes.setLabelLineLength(10);
                pieChartTaskTypes.setLabelsVisible(true);
                pieChartTaskTypes.setStartAngle(360);

                // create average tasks TextArea
                String task = DataInterface.getSelectedMaintenance();
                int amount = 0;

                textAreaAvgMaintenanceTasks.setStyle("-fx-font-size: 12;");

                if (Objects.equals(task, "ALL")) {
                    for (String key : DataInterface.getMaintenanceMapAverage().keySet()) {
                        System.out.println(amount);
                        amount += DataInterface.getMaintenanceMapAverage().get(key);
                    }
                    textAreaAvgMaintenanceTasks.setText(String.format("%s tasks per day for the 3 days: %d",task,amount/3));
                }

                else {
                    if (DataInterface.getMaintenanceMapAverage().containsKey(task)){
                        textAreaAvgMaintenanceTasks.setText(String.format("%s tasks per day for the past 3 days: %d",task,DataInterface.getMaintenanceMapAverage().get(task)/7));
                    } else {
                        textAreaAvgMaintenanceTasks.setText(String.format("%s tasks per day for the past 3 days: %d",task,0));
                    }
                }

            }
        } else {
            MenuItem errorMessage = new MenuItem("Please select coordinates "
                    + "first from the sidemenu on the right!");
            menuErrorMessage.getItems().add(errorMessage);

            buttonUpdateGraph.setContextMenu(menuErrorMessage);
            menuErrorMessage.show(buttonUpdateGraph, Side.BOTTOM, 0, 0);

        }
    }
}
