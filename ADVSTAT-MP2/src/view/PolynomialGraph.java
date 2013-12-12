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

public class PolynomialGraph {
	private PolynomialFunction2D graphableFunction;
	private ChartPanel chartPanel;
	
	public PolynomialGraph(PolynomialFunction2D polynomialFunction2D, String graphTitle, double start, double end, String label){
		graphableFunction = polynomialFunction2D;
		
		int samples = 150;
		XYDataset dataset = DatasetUtilities.sampleFunction2D(graphableFunction, start, end, samples, "f(x) = " + label);
		JFreeChart chart = ChartFactory.createXYLineChart(graphTitle, 
						null, null,
						dataset, 
						PlotOrientation.VERTICAL, 
						true, true, false);
		
		chart.setBackgroundPaint(new Color(238, 238, 238));
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 290));
	}

	public ChartPanel getChart() {
		return chartPanel;
	}
}
