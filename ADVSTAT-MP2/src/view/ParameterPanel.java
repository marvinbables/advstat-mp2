package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

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
	private JTextField inputTerm, leftInterval, rightInterval;
	private JLabel outputPolynomial, outputInterval;
	private View view;
	private JButton btnAddTerm;
	private JButton btnGraph, btnInterval;
	
	private JComboBox<String> cmbxMethod;
	
	private Polynomial currentPolynomial;
	private int selectedMethod;
	
	private static final boolean SHOW_BORDER = false;
	
	private Dimension petiteDimension = new Dimension(50, 30);
	private Dimension smallDimension = new Dimension(100, 30);
	private Dimension mediumDimension = new Dimension(200, 30);
	private Dimension wideDimension = new Dimension(420, 50);
	private Dimension medwideDimension = new Dimension(200, 50);
	private GraphListener graphListener;
	private JButton btnReset;
	

	public ParameterPanel(View view) {
		this.view = view;
		/** Initialize the panel */
		setPreferredSize(new Dimension(400, 300));
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setBorder(BorderFactory.createEtchedBorder());
		
		/** Initialize components */
		outputPolynomial = newLabel("");
		outputPolynomial.setBorder(BorderFactory.createTitledBorder("Current Polynomial"));
		outputPolynomial.setPreferredSize(medwideDimension);
		add(outputPolynomial);
		
		outputInterval = newLabel("");
		outputInterval.setBorder(BorderFactory.createTitledBorder("Current Intervals"));
		outputInterval.setPreferredSize(medwideDimension);
		add(outputInterval);

		/** Initialize parameters */
		InitializeParameters();
		
		inputTerm = newInput("e.g. 4x^2", mediumDimension);
		inputTerm.setPreferredSize(smallDimension);
		inputTerm.addKeyListener(new KeyHandler());
		add(inputTerm);

		btnAddTerm = newButton("Add term", this, smallDimension);
		add(btnAddTerm);

		btnReset = newButton("Reset", this, smallDimension);
		add(btnReset);
		
		btnGraph = newButton("Graph", this, smallDimension);
		add(btnGraph);
		
		btnReset.setMnemonic(java.awt.event.KeyEvent.VK_R);
		btnGraph.setMnemonic(java.awt.event.KeyEvent.VK_G);
		

		
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
		
		JLabel lblInterval = new JLabel("Interval : [");
		lblInterval.setPreferredSize(new Dimension(60, 30));
		add(lblInterval);
		
		leftInterval = newInput("e.g. 2", petiteDimension);
		leftInterval.addKeyListener(new KeyHandler());
		add(leftInterval);
		
		JLabel label = new JLabel(",");
		label.setPreferredSize(new Dimension(8, 30));
		add(label);
		
		rightInterval = newInput("e.g. 4", petiteDimension);
		rightInterval.addKeyListener(new KeyHandler());
		add(rightInterval);
		
		JLabel label1 = new JLabel("]");
		label1.setPreferredSize(new Dimension(15, 30));
		add(label1);
		
		btnInterval = newButton("Add Interval", this, mediumDimension); //new Dimension(120, 30));
		add(btnInterval);
		
		btnInterval.setMnemonic(java.awt.event.KeyEvent.VK_I);
		
	//	add(Box.createRigidArea(new Dimension(80, 30)));
		
		if(SHOW_BORDER) {
			outputPolynomial.setBorder(BorderFactory.createEtchedBorder());
			
		}
	}

	private void InitializeParameters() {
		currentPolynomial = new Polynomial(new ArrayList<Term>());
		outputPolynomial.setText(currentPolynomial.toString());
	}
	
	
	public Dimension getDimensions(String size){
		
		if(size.equalsIgnoreCase("small"))
			return smallDimension;
		else if(size.equalsIgnoreCase("medium"))
			return mediumDimension;	
		else if(size.equalsIgnoreCase("wide"))
			return wideDimension;
		else
		return null;
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object target = e.getSource();
		if (target.equals(btnAddTerm))
			AddTerm();
		
		else if (target.equals(btnReset)){
			InitializeParameters();
			view.resetGraph();
		}
		
		else if (target.equals(btnGraph)){
			GraphParameters parameters = new GraphParameters(currentPolynomial);
			graphListener.GraphRequested(parameters);
		}
		else if(target.equals(view.getBtnNext())){
			view.nextButton();
		}
		else if(target.equals(view.getBtnPrev())){
			view.prevButton();
		}
		
		else if (target.equals(btnInterval) || target.equals(rightInterval)){
			AddInterval();
				
		}
		
			
	}
	
	
	private void AddInterval(){
		System.out.println(leftInterval.getText() + " " + rightInterval.getText());
		if((isInputwrong("numbers only", leftInterval.getText()) && isInputwrong("numbers only", rightInterval.getText())))
			JOptionPane.showMessageDialog(this, "Please follow the format : Numbers only" , "Input error", JOptionPane.ERROR_MESSAGE);
		
		else{
			if(!leftInterval.getText().equals("") && !rightInterval.getText().equals(""))
			outputInterval.setText("[" + Integer.parseInt(leftInterval.getText()) + " ," + Integer.parseInt(rightInterval.getText()) + "]" );
			else{
				if(leftInterval.getText().equals(""))
					JOptionPane.showMessageDialog(this, "Missing input : left Interval" , "Input error", JOptionPane.ERROR_MESSAGE);
				else	JOptionPane.showMessageDialog(this, "Missing input : right Interval" , "Input error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private boolean isInputwrong(String s, String field){
		boolean error = false;
		char input[] = field.toCharArray();
		
		if(s.equalsIgnoreCase("Numbers Only")){
			
			for(int j = 0; j < input.length; j++){
				if (((input[j] < '0') || (input[j] > '9')))
						error = true;
				System.out.println(input[j] + "" + error);
				}
				
			}
		return error;
			
	}
	
	
	private void AddTerm() {
		Term newTerm = null;
		
		/** Parsing */
		try {
			double coefficient 	= -1;
			int exponent 		= -1;
			boolean hasX		= false;
			boolean hasExponent = false;
			
			
			String text = inputTerm.getText();
			text = text.toLowerCase();
			
			text = text.replace("-x", "-1x");
			text = text.replace("+x", "+1x");
			
		//	for(2 chips)
			
			text = text.substring(text.length() - 1).equalsIgnoreCase("x") ? text.replace("x", "x^1") : text;
			

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

			if(!hasX && hasExponent){
				String [] toks = null;
				text = text.replace("^", "x");
				toks = text.split("x");
				text = text.replace("x", "");
				
				int i = 1;
				int val = Integer.parseInt(toks[0]);
				while(i != Integer.parseInt(toks[1])){
					val += val;
					i++;
				}
				text = Integer.toString(val);
				exponent = 0;
			}
		
			// Remove x and exponent, and split
			String[] tokens = null;
			
			
				text = text.replace("^", "");
				text = text.trim();
			
				tokens = text.split("x");
			
			if (tokens.length == 0 || tokens[0].length() == 0 && hasX)
				coefficient = 1;
			
			if (exponent == -1) exponent = Integer.parseInt(tokens[1]);
			if (coefficient == -1) coefficient = Double.parseDouble(tokens[0]);
			
			newTerm = new Term(coefficient, exponent);
			
			currentPolynomial = currentPolynomial.addTerm(newTerm);
			outputPolynomial.setText(currentPolynomial.toString());
		}catch(Exception err){
			String problem = "Please follow the format: 4x^2!";
			JOptionPane.showMessageDialog(this, problem, "Input error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/** Getters, setters, and factories */
	
	public void setGraphListener(GraphListener listener){
		graphListener = listener;
	}
	
	public JFormattedTextField newInput(String inputHint, Dimension dim){
		JXFormattedTextField txtField = new JXFormattedTextField(inputHint);
		txtField.setPreferredSize(dim);
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

	public JButton newButton(String string, ActionListener listener, Dimension dimension){
		JButton button = new JButton(string);
		button.setFocusable(false);
		button.setPreferredSize(dimension);
		button.addActionListener(listener);
		return button;
	}
	public JButton newButton(ImageIcon img, ActionListener listener){
		JButton button = new JButton(img);
		button.setFocusable(false);
		button.setPreferredSize(smallDimension);
		button.addActionListener(listener);
		return button;
	}
	
	public class KeyHandler extends KeyAdapter{ 
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER && inputTerm.hasFocus()){
				ParameterPanel.this.actionPerformed(new ActionEvent(btnAddTerm, ActionEvent.ACTION_PERFORMED, "Add a term"));
			}
			super.keyPressed(e);
		}
	}

}
