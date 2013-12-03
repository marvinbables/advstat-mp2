package view;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.bind.Marshaller.Listener;

import org.jfree.data.function.PolynomialFunction2D;

import view.listeners.GraphListener;
import view.listeners.MenuListener;


@SuppressWarnings("serial")
public class View extends JFrame implements GraphListener{
	private ParameterPanel parameterPanel;
	private JPanel graphPanel, btnPanel, tablePanel;
	private PolynomialGraph graph;
	private JButton btnNext, btnPrev;
	private ImageIcon imgNext, imgPrev;
	
	
	public View() {
		super("Roots of Polynomials");
		setPreferredSize(new Dimension(450, 650));
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
		
		parameterPanel = new ParameterPanel(this);
		parameterPanel.setBorder(BorderFactory.createEtchedBorder());
		parameterPanel.setGraphListener(this);
		add(parameterPanel);
		
		graphPanel = new JPanel();
		graphPanel.setBorder(BorderFactory.createEtchedBorder());
		graphPanel.setPreferredSize(new Dimension(430, 400));
		add(graphPanel);
		
		imgNext = new ImageIcon("icons/next.png");
		imgPrev = new ImageIcon("icons/prev.png");
		
		btnPanel = new JPanel();
		btnPanel.setPreferredSize(new Dimension(200,50));
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		
		tablePanel = new JPanel();
		tablePanel.setPreferredSize(new Dimension(430, 400));
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
		
		
		
		btnNext = parameterPanel.newButton(imgNext, parameterPanel);
		
		
		btnPrev = parameterPanel.newButton(imgPrev, parameterPanel);
		btnPrev.setEnabled(false);
		
		btnPanel.add(btnPrev);
		btnPanel.add(Box.createRigidArea(parameterPanel.getDimensions("small")));
		btnPanel.add(btnNext);
		
		add(btnPanel);
		
		
		pack(); 
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void nextButton(){
		add(tablePanel);
		remove(graphPanel);
		btnNext.setEnabled(false);
		btnPrev.setEnabled(true);
	}
	public void prevButton(){
		add(graphPanel);
		remove(tablePanel);
		btnNext.setEnabled(true);
		btnPrev.setEnabled(false);
	}
	
	public JButton getBtnNext() {
		return btnNext;
	}

	public void setBtnNext(JButton btnNext) {
		this.btnNext = btnNext;
	}

	public JButton getBtnPrev() {
		return btnPrev;
	}

	public void setBtnPrev(JButton btnPrev) {
		this.btnPrev = btnPrev;
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
	
	public void addButtonActionListener(ActionListener l){
		btnNext.addActionListener(l);
		btnPrev.addActionListener(l);
	}
	@Override
	public void GraphRequested(GraphParameters parameters) {
		drawGraph(parameters, GraphParameters.StartX, GraphParameters.EndX);
	}
	
}
