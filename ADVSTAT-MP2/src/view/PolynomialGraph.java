package view;

import java.awt.Color;
import java.awt.Dimension;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.function.PolynomialFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;

public class PolynomialGraph {
	private PolynomialFunction function;
	private PolynomialFunction2D graphableFunction;
	private ChartPanel chartPanel;
	
	public PolynomialGraph(PolynomialFunction function, String graphTitle){
		this.function = function;
		graphableFunction = new PolynomialFunction2D(function.getCoefficients());
		
		double start = -2;
		double end = 2;
		int samples = 50;
		XYDataset dataset = DatasetUtilities.sampleFunction2D(graphableFunction, start, end, samples, "function");
		JFreeChart chart = ChartFactory.createXYLineChart(graphTitle, 
						null, null,
						dataset, 
						PlotOrientation.VERTICAL, 
						true, true, false);
		
		chart.setBackgroundPaint(new Color(238, 238, 238));
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(600, 390));
	}

	public ChartPanel getChart() {
		return chartPanel;
	}
}