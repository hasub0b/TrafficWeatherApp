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
    
    /* HistogramDrawer / IconsDrawer
    public BarChart createHistogram() {
        
    }*/
    
    /*
    public TextArea createMessages() {
        
    }*/
}
