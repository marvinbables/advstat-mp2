package view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;

import method.Method.Approach;
import model.Iteration;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.function.PolynomialFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import view.listeners.GraphListener.GraphParameters;

public class PolynomialGraph
{
    private PolynomialFunction2D graphableFunction;
    private String               graphTitle;
    private double               start;
    private double               end;

    private ChartPanel           scatterChart;
    private ChartPanel           polynomialChart;
    private ArrayList<Iteration> iterations;
    private Approach approach;
    
    public static JFrame polynomialFrame;
    public static JFrame scatterplotFrame;

    
    public PolynomialGraph(GraphParameters parameters)
    {
        this.graphableFunction = new PolynomialFunction2D(parameters.polynomial.getDoubles());
        this.graphTitle = parameters.polynomial.toString(); 
        this.iterations = parameters.iterations;
        this.approach = parameters.approach;
        
        this.start = GraphParameters.StartX;
        this.end = GraphParameters.EndX;

        InitPolynomialChart();
        InitScatterplotChart();
    }

    private void InitScatterplotChart()
    {
        if (approach == null) return;
        /* Then the actual values */
        XYSeries functionItself = new XYSeries("Function itself");
        XYSeries valuesOfX = new XYSeries("Values of x");
        
        ArrayList<Double> values = new ArrayList<>();
        for (Iteration iter : iterations)
        {
            double x = iter.getX2();
            double y = iter.getY2();
            functionItself.add(x, y);
            valuesOfX.add(x, 0);
            values.add(y);
        }
        
        Collections.sort(values);
        Double lowest = values.get(0);
        Double highest = values.get(values.size() - 1);

        XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(functionItself);
        collection.addSeries(valuesOfX);
        
        JFreeChart chart = ChartFactory.createScatterPlot("f(x) = " + graphTitle, null, null, collection, PlotOrientation.VERTICAL, false, false, false);
        
        XYPlot xyPlot = chart.getXYPlot();
        // NumberAxis domain = (NumberAxis)xyPlot.getDomainAxis();
        NumberAxis range = (NumberAxis)xyPlot.getRangeAxis();
        
        // domain.setRange(start, end);
        range.setRange(lowest - 1, highest + 1);
        
        chart.setBackgroundPaint(new Color(238, 238, 238));
        scatterChart = new ChartPanel(chart);
        scatterChart.setPreferredSize(new Dimension(400, 320));

    }

    private void InitPolynomialChart()
    {
        int samples = 150;
        XYDataset dataset = DatasetUtilities.sampleFunction2D(graphableFunction, start, end, samples, "f(x) = " + graphTitle);

        JFreeChart chart = ChartFactory.createXYLineChart(graphTitle, null, null, dataset, PlotOrientation.VERTICAL, true, true, false);

        chart.setBackgroundPaint(new Color(238, 238, 238));
        polynomialChart = new ChartPanel(chart);
        polynomialChart.setPreferredSize(new Dimension(400, 320));
    }

    public ChartPanel getScatterChart()
    {
        if (approach == null) return null;
        return scatterChart;
    }

    public ChartPanel getPolynomialChart()
    {
        return polynomialChart;

    }
}
