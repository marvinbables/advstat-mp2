package view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.polynomial.Polynomial;
import model.polynomial.Term;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;


@SuppressWarnings("serial")
public class View extends JFrame {
	ParameterPanel paramPanel;
	
	public View() {
		super("Roots of Polynomials");
		//setPreferredSize(new Dimension(450, 600));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new FlowLayout());
		
		paramPanel = new ParameterPanel();
		add(paramPanel);
		
		JPanel temp = new JPanel();
		temp.setBorder(BorderFactory.createEtchedBorder());
		double[] c = {5, 2, 1, -2, 4, -2};
		
		ArrayList<Term> terms = new ArrayList<Term>();
		terms.add(new Term(1, 0));
		terms.add(new Term(2, 1));
		terms.add(new Term(3, 2));
		System.out.println(new Polynomial(terms));
		
		PolynomialFunction function = new PolynomialFunction(c);
		PolynomialGraph graph = new PolynomialGraph(function, "Sample graph");
		
		temp.add(graph.getChart());
		add(temp);
		
		
		pack(); 
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public ParameterPanel getParamPanel() {
		return paramPanel;
	}
	
	public int getSelectedMethod() {
		return paramPanel.getCmbxMethod().getSelectedIndex();
	}
	
	public int getIterations() throws NumberFormatException {
		return Integer.parseInt(paramPanel.getTxtIterations().getText());
	}
	
	public int getX0() throws NumberFormatException {
		return Integer.parseInt(paramPanel.getTxtX0().getText());
	}
	
	public int getX1() throws NumberFormatException {
		return Integer.parseInt(paramPanel.getTxtX1().getText());
	}
	
	public void addActionListener(ActionListener l) {
		paramPanel.addActionListener(l);
	}

}
