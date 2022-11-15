/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.trafficweatherapp;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TextArea;

/**
 *
 * @author mikko
 */
public class GraphDrawerFactory {
    
    String type;
    
    public GraphDrawerFactory(String type) {
        this.type = type;
    }
    public GraphDrawerFactory() {
        
    }
    
    public LineChart createPlot() {
        PlotDrawer plotter = new PlotDrawer(new Double[] 
        {2.0, 5.0, 6.0, 7.0, 7.0, 7.5, 6.0, 1.1, 0.1, -0.9,-5.0}, 1);

        return plotter.getChart();
    }
    
    public BarChart createIconsChart() {
        IconsDrawer iconsChart = new IconsDrawer(new Double[] 
        {2.0, 15.0, 26.0, 37.0, 47.0, 57.5, 66.0, 71.1, 80.1, 91.0, 100.0}, 1);
        
        return iconsChart.getChart();
    }
    
    /* HistogramDrawer / IconsDrawer
    public BarChart createHistogram() {
        
    }*/
    
    /*
    public TextArea createMessages() {
        
    }*/
}
