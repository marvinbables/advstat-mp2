package view;

import java.awt.Color;
import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.function.PolynomialFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class PolynomialGraph
{
    private PolynomialFunction2D graphableFunction;
    private String               graphTitle;
    private double               start;
    private double               end;

    private ChartPanel           scatterChart;
    private ChartPanel           polynomialChart;

    public PolynomialGraph(PolynomialFunction2D polynomialFunction2D, String graphTitle, double start, double end, String label)
    {
        graphableFunction = polynomialFunction2D;
        this.graphTitle = graphTitle;
        this.start = start;
        this.end = end;
        graphTitle = label;
        start = end;

        InitPolynomialChart();
        InitScatterplotChart();
    }

    private void InitScatterplotChart()
    {
        /* Begin with the attempts */
        XYSeries XYSeries = new XYSeries("test");
        XYSeries.add(0, 0);
        XYSeries.add(0, 1);
        XYSeries.add(0, 5);
        XYSeries.add(1, 1);
        XYSeries.add(2, 4);
        XYSeries.add(2, 7);
        XYSeries.add(3, 9);

        /* Then the actual values */
        XYSeries XYSeries2 = new XYSeries("test2 ");
        XYSeries2.add(2, 4);
        XYSeries2.add(3, 5);
        XYSeries2.add(4, 6);
        XYSeries2.add(5, 7);

        XYSeriesCollection collection = new XYSeriesCollection();

        collection.addSeries(XYSeries);
        collection.addSeries(XYSeries2);

        
        JFreeChart chart = ChartFactory.createScatterPlot("f(x) = " + graphTitle, null, null, collection, PlotOrientation.VERTICAL, false, false, false);
        chart.setBackgroundPaint(new Color(238, 238, 238));
        scatterChart = new ChartPanel(chart);
        scatterChart.setPreferredSize(new Dimension(400, 290));

    }

    private void InitPolynomialChart()
    {
        int samples = 150;
        XYDataset dataset = DatasetUtilities.sampleFunction2D(graphableFunction, start, end, samples, "f(x) = " + graphTitle);

        JFreeChart chart = ChartFactory.createXYLineChart(graphTitle, null, null, dataset, PlotOrientation.VERTICAL, true, true, false);

        chart.setBackgroundPaint(new Color(238, 238, 238));
        polynomialChart = new ChartPanel(chart);
        polynomialChart.setPreferredSize(new Dimension(400, 290));
    }

    public ChartPanel getScatterChart()
    {
        return scatterChart;
    }

    public ChartPanel getPolynomialChart()
    {
        return polynomialChart;

    }
}
