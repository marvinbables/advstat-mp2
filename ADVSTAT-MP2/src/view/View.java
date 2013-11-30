package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class View extends JFrame {
	ParameterPanel paramPanel;
	
	public View() {
		super("Roots of Polynomials");
		setPreferredSize(new Dimension(450, 600));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new FlowLayout());
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(420, 300));
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		paramPanel = new ParameterPanel();
		panel.add(paramPanel);
		
		add(panel);
		
		JPanel temp = new JPanel();
		temp.setPreferredSize(new Dimension(420, 250));
		temp.setBorder(BorderFactory.createEtchedBorder());
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
