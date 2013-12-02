package view;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.data.function.PolynomialFunction2D;
import view.listeners.GraphListener;
import view.listeners.MenuListener;


@SuppressWarnings("serial")
public class View extends JFrame implements GraphListener{
	private ParameterPanel parameterPanel;
	private JPanel graphPanel;
	private PolynomialGraph graph;
	
	
	public View() {
		super("Roots of Polynomials");
		setPreferredSize(new Dimension(450, 600));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		/** Prepare menu */
		MenuListener menuListener = new MenuListener();
		menuListener.setGraphListener(this);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu settings = new JMenu("Settings");
		menuBar.add(settings);
		
		JMenuItem changeBounds = new JMenuItem("Change graph bounds");
		changeBounds.addActionListener(menuListener);
		settings.add(changeBounds);
		
		
		/** Initialize components */
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		parameterPanel = new ParameterPanel();
		parameterPanel.setBorder(BorderFactory.createEtchedBorder());
		parameterPanel.setGraphListener(this);
		add(parameterPanel);
		
		graphPanel = new JPanel();
		graphPanel.setBorder(BorderFactory.createEtchedBorder());
		graphPanel.setPreferredSize(new Dimension(430, 400));
		add(graphPanel);
		
		
		pack(); 
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void drawGraph(GraphParameters parameters, double begin, double end){
		graphPanel.removeAll();
		if (parameters.polynomial.getTerms().size() == 0)
		{
			JOptionPane.showMessageDialog(this, "Invalid polynomial to graph.", "Invalid polynomial", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
 		double[] c = parameters.polynomial.getDoubles();
		graph = new PolynomialGraph(new PolynomialFunction2D(c), "Sample graph", begin, end);
		graphPanel.add(graph.getChart());
		validate();
		repaint();
	}

	@Override
	public void GraphRequested(GraphParameters parameters) {
		drawGraph(parameters, GraphParameters.StartX, GraphParameters.EndX);
	}
	
}
