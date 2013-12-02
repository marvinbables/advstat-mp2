package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.polynomial.Polynomial;
import model.polynomial.Term;

import org.jdesktop.swingx.JXFormattedTextField;

import view.listeners.GraphListener;
import view.listeners.GraphListener.GraphParameters;


public class ParameterPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField inputTerm;
	private JLabel outputPolynomial;

	private JButton btnAddTerm;
	private JButton btnGraph;
	
	private JComboBox<String> cmbxMethod;
	
	private Polynomial currentPolynomial;
	private int selectedMethod;
	
	private static final boolean SHOW_BORDER = false;

	private Dimension smallDimension = new Dimension(100, 30);
	private Dimension mediumDimension = new Dimension(150, 30);
	private Dimension wideDimension = new Dimension(350, 30);
	private GraphListener graphListener;


	public ParameterPanel() {
		/** Initialize the panel */
		setPreferredSize(new Dimension(400, 400));
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setBorder(BorderFactory.createEtchedBorder());
		
		/** Initialize parameters */
		InitializeParameters();

		/** Initialize components */
		outputPolynomial = newLabel("No polynomial yet");
		outputPolynomial.setPreferredSize(wideDimension );
		add(outputPolynomial);

		inputTerm = newInput("e.g. 11x^3");
		inputTerm.addKeyListener(new KeyHandler());
		add(inputTerm);

		btnAddTerm = newButton("Add term", this);
		add(btnAddTerm);

		btnGraph = newButton("Graph", this);
		
		add(btnGraph);

		/** Initialize methods available */
		JLabel lblMethod = newLabel("Select a method");
		add(lblMethod);

		cmbxMethod = new JComboBox<String>();
		cmbxMethod.setPreferredSize(mediumDimension);
		cmbxMethod.addItem("Regula Falsi");
		cmbxMethod.addItem("Secant");
		cmbxMethod.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { selectedMethod = cmbxMethod.getSelectedIndex(); }
			});
		add(cmbxMethod);

		if(SHOW_BORDER) {
			outputPolynomial.setBorder(BorderFactory.createEtchedBorder());
			
		}
	}

	private void InitializeParameters() {
		currentPolynomial = new Polynomial(new ArrayList<Term>());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object target = e.getSource();
		if (target.equals(btnAddTerm)){
			Term newTerm = null;
			
			/** Parsing */
			try {
				double coefficient 	= -1;
				int exponent 		= -1;
				boolean hasX		= false;
				boolean hasExponent = false;
				
				
				String text = inputTerm.getText();
				text = text.toLowerCase();
				
				hasExponent = text.contains("^");
				hasX = text.contains("x");
				
				if (text.contains("+") || text.contains("e") || text.contains("*") || text.contains("/"))
					throw new Exception("Invalid!");
				
				if (!hasExponent){
					if (hasX)
						exponent = 1;
					else
						exponent = 0;
				}
				
				// Remove x and exponent, and split
				text = text.replace("^", "");
				text = text.trim();
				String[] tokens = text.split("x");
				
				if (tokens.length == 0 || tokens[0].length() == 0 && hasX)
					coefficient = 1;
				
				if (exponent == -1) exponent = Integer.parseInt(tokens[1]);
				if (coefficient == -1) coefficient = Double.parseDouble(tokens[0]);
				
				newTerm = new Term(coefficient, exponent);
				currentPolynomial = currentPolynomial.addTerm(newTerm);
				System.out.println(currentPolynomial);
				outputPolynomial.setText(currentPolynomial.toString());
			}catch(Exception err){
				String problem = "Please follow the format: 3x^3!";
				JOptionPane.showMessageDialog(this, problem, "Input error", JOptionPane.ERROR_MESSAGE);
			}
			
			
			
		}else if (target.equals(btnGraph)){
			GraphParameters parameters = new GraphParameters(currentPolynomial);
			graphListener.GraphRequested(parameters);
		}
	}
	
	
	
	
	
	
	
	/** Getters, setters, and factories */
	
	public void setGraphListener(GraphListener listener){
		graphListener = listener;
	}
	
	public JFormattedTextField newInput(String inputHint){
		JXFormattedTextField txtField = new JXFormattedTextField(inputHint);
		txtField.setPreferredSize(mediumDimension);
		return txtField;
	}

	public JLabel newLabel(String string){
		JLabel label = new JLabel(string);
		label.setPreferredSize(mediumDimension);
		return label;
	}

	public JLabel newLabel(){
		return newLabel(null);
	}

	public JButton newButton(String string, ActionListener listener){
		JButton button = new JButton(string);
		button.setPreferredSize(smallDimension);
		button.addActionListener(listener);
		return button;
	}
	
	
	public class KeyHandler extends KeyAdapter{ 
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER){
				ParameterPanel.this.actionPerformed(new ActionEvent(btnAddTerm, ActionEvent.ACTION_PERFORMED, "Add a term"));
			}
			super.keyPressed(e);
		}
	}

}
