/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author mikko
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
    @FXML BarChart chartHistogram;

    Tooltip tipSideMenu = new Tooltip("Graph settings");

    GraphDrawerFactory graphFactory = new GraphDrawerFactory();

    @FXML
    FXMLLoader loaderSideMenuTitleBoxes = new FXMLLoader(
            getClass().getResource("sideMenuTitleBoxes.fxml"));

    public void initialize() throws Exception {

        ToggleGroup groupTimeline = new ToggleGroup();
        buttonForecast.setToggleGroup(groupTimeline);
        buttonObservation.setToggleGroup(groupTimeline);

        ToggleGroup groupForecastOptions = new ToggleGroup();
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

        try {
            //LineChart chart = graphFactory.createPlot();
            //stackPaneGraph.getChildren().add(chart);
        } catch (Exception e) {
            System.out.println(e);
        }

        buttonForecast.setOnAction(event -> this.radioButtonEvent(event));
        buttonObservation.setOnAction(event -> this.radioButtonEvent(event));

        chartHistogram.getData().add(graphFactory.createHistogram());
        chartHistogram.lookup(".chart-plot-background").setStyle("-fx-background-color: #C8B6E2;");
        //chartHistogram.set
        chartLine.getData().add(graphFactory.createPlot());
        chartLine.getYAxis().setSide(Side.RIGHT);

        /*Pane filler = new Pane();
        filler.managedProperty().bind(chart.visibleProperty());
        filler.visibleProperty().bind(chart.visibleProperty());
        filler.minWidthProperty().bind(chart.getYAxis().widthProperty());
        filler.maxWidthProperty().bind(chart.getYAxis().widthProperty());
        HBox.setHgrow(histo, Priority.ALWAYS);
        HBox wrapper = new HBox(histo, filler);*/

        //stackPaneGraph.getChildren().addAll(chartHistogram, chartLine);
        //StackPane.setAlignment(histo, Pos.CENTER);
        //StackPane.setAlignment(chart, Pos.CENTER);
        //histo.translateYProperty().bind(chart.translateYProperty());
        //histo.translateXProperty().bind(chart.translateXProperty());
        //histo.translateZProperty().bind(chart.translateZProperty());

        //shapeChartBackground.widthProperty().bind(chart.widthProperty().subtract(20));
        //shapeChartBackground.heightProperty().bind(chart.heightProperty().subtract(60));
    }

    private void radioButtonEvent(ActionEvent event) {
        if (buttonForecast.isSelected()) {
            button2h.setDisable(false);
            button4h.setDisable(false);
            button6h.setDisable(false);
            button12h.setDisable(false);
        } else {
            button2h.setDisable(true);
            button4h.setDisable(true);
            button6h.setDisable(true);
            button12h.setDisable(true);
        }
    }

}
