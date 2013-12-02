package view;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

import view.listeners.GraphListener;


@SuppressWarnings("serial")
public class View extends JFrame implements GraphListener{
	private ParameterPanel parameterPanel;
	private JPanel graphPanel;
	private PolynomialGraph graph;
	
	
	public View() {
		super("Roots of Polynomials");
		//setPreferredSize(new Dimension(450, 600));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new FlowLayout());
		
		parameterPanel = new ParameterPanel();
		parameterPanel.setBorder(BorderFactory.createEtchedBorder());
		parameterPanel.setGraphListener(this);
		add(parameterPanel);
		
		graphPanel = new JPanel();
		graphPanel.setBorder(BorderFactory.createEtchedBorder());
		add(graphPanel);
		
		
		pack(); 
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void drawGraph(GraphParameters parameters){
		graphPanel.removeAll();
		
		double[] c = parameters.polynomial.getDoubles();
		PolynomialFunction function = new PolynomialFunction(c);
		graph = new PolynomialGraph(function, "Sample graph");
		
		graphPanel.add(graph.getChart());
		
	}

	@Override
	public void GraphRequested(GraphParameters parameters) {
		drawGraph(parameters);
	}
	
}
