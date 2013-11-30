package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import method.Polynomial;

import org.jdesktop.swingx.JXFormattedTextField;


public class ParameterPanel extends JPanel implements KeyListener, ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField inputPolynomial, txtIterations, txtX0, txtX1;
	private JButton btnCompute;
	private JLabel outputPolynomial;
	private JComboBox<String> cmbxMethod;
	private int selectedMethod;
	private boolean SHOW_BORDER = !true;
	
	private JLabel lblx1;
	
	private Dimension smallDimension = new Dimension(100, 30);
	private Dimension mediumDimension = new Dimension(150, 30);
	private Dimension wideDimension = new Dimension(300, 30);
	private JButton btnAddTerm;
	private Component btnGraph;
	
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
	
	public ParameterPanel() {
		setPreferredSize(new Dimension(400, 400));
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setBorder(BorderFactory.createEtchedBorder());
		
		outputPolynomial = newLabel("No polynomial yet");
		outputPolynomial.setPreferredSize(wideDimension );
		add(outputPolynomial);
		
		inputPolynomial = newInput("e.g. 11x^3");
		inputPolynomial.addKeyListener(this);
		add(inputPolynomial);
		
		btnAddTerm = newButton("Add term", this);
		add(btnAddTerm);
		
		btnGraph = newButton("Graph", this);
		add(btnGraph);
		
		JLabel lblMethod = newLabel("Select a method");
		add(lblMethod);
		
		cmbxMethod = new JComboBox<String>();
		cmbxMethod.setPreferredSize(mediumDimension);
		cmbxMethod.addItem("Regula Falsi");
		cmbxMethod.addItem("Secant");
		//cmbxMethod.addItem("Bisection");
		//cmbxMethod.addItem("Newton-Raphson");
		
		cmbxMethod.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedMethod = cmbxMethod.getSelectedIndex();
				setVisible();
				//updateMethodPanel();
			}
			
			private void setVisible() {
				txtX1.setVisible(true);
				lblx1.setVisible(true);
			}
		});
		add(cmbxMethod);
		
		JLabel lblx0 = new JLabel("X0");
		lblx0.setPreferredSize(new Dimension(30, 30));
		lblx0.setHorizontalAlignment(JTextField.CENTER);
		add(lblx0);
		
		txtX0 = new JTextField();
		txtX0.setPreferredSize(new Dimension(40, 30));
		add(txtX0);
		
		lblx1 = new JLabel("X1");
		lblx0.setPreferredSize(new Dimension(30, 30));
		add(lblx1);
		
		txtX1 = new JTextField();
		txtX1.setPreferredSize(new Dimension(40, 30));
		add(txtX1);
		
		//add(panelMethod);
		
		JLabel lblIteration = new JLabel("i");
		lblIteration.setPreferredSize(new Dimension(30, 30));
		lblIteration.setHorizontalAlignment(JTextField.CENTER);
		add(lblIteration);
		
		txtIterations = new JTextField();
		txtIterations.setPreferredSize(new Dimension(40, 30));
		add(txtIterations);
		
		btnCompute = new JButton("Compute");
		btnCompute.setPreferredSize(new Dimension(100, 30));
		btnCompute.setBorder(BorderFactory.createEtchedBorder());
		add(btnCompute);
		
		if(SHOW_BORDER) {
			outputPolynomial.setBorder(BorderFactory.createEtchedBorder());
			lblIteration.setBorder(BorderFactory.createEtchedBorder());
			lblx0.setBorder(BorderFactory.createEtchedBorder());
			lblx1.setBorder(BorderFactory.createEtchedBorder());
			setBorder(BorderFactory.createEtchedBorder());
		}
	}

	public JTextField getTxtPolynomial() {
		return inputPolynomial;
	}

	public JLabel getLblPolynomial() {
		return outputPolynomial;
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		String input[] = inputPolynomial.getText().split(" ");
		double d = 0, numbers[] = new double[input.length];
		boolean error = false;
		inputPolynomial.setBackground(Color.white);
		
		if(input.length % 2 != 0)
			inputPolynomial.setBackground(Color.pink);
		else {
			for (int i = 0; i < input.length; i++) {
				try {
					d = Double.parseDouble(input[i]);
					numbers[i] = d;
				} catch(NumberFormatException ex) {
					error = true;
					inputPolynomial.setBackground(Color.pink);
				}
			}
			if(!error)
				toPolynomial(numbers);
		}
	}


	private void toPolynomial(double[] numbers) {
		String text = "";
		
		for (int i = 0; i < numbers.length; i++) {
			if(i % 2 != 0) {
				if(numbers[i-1] != 0) {
					if(numbers[i] != 0) {
						if(Math.abs(numbers[i]) - Math.floor(Math.abs(numbers[i])) == 0) {
							int number = (int)numbers[i];
							if(number < 0)
								text += "x^(" + number + ")";
							else {
								if(number == 1)
									text += "x";
								else
									text += "x^" + number;
								
							}
						}
						else {
							if(numbers[i] < 0)
								text += "x^(" + numbers[i] + ")";
							else {
								if(numbers[i] == 1)
									text += "x";
								else
									text += "x^" + numbers[i];
							}
						}
					}
				}
			}
			else {
				if(numbers[i] != 0) {
					int num = 0;

					if( Math.abs(numbers[i]) - Math.floor(Math.abs(numbers[i])) == 0) {
						num = (int)numbers[i];
						if(num < 0) {
							text += " - " + (num == -1? "" : Math.abs(num));
						}
						else {
							if(i != 0)
								text += " + " + (num == 1? "" : num);
							else
								text += (num == 1? "" : num);
						}
					}
					else {
						if(numbers[i] < 0) {
							text += " - " + (numbers[i] == -1? "" : Math.abs(numbers[i]));
						}
						else {
							if(i != 0)
								text += " + " + (numbers[i] == 1? "" : numbers[i]);
							else
								text += (numbers[i] == 1? "" : numbers[i]);
						}
					}
				}
			}
		}
		Polynomial.setPolynomial(numbers);
		outputPolynomial.setText(text);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	public void addActionListener(ActionListener l) {
		btnCompute.addActionListener(l);
	}

	public JTextField getTxtIterations() {
		return txtIterations;
	}

	public JTextField getTxtX0() {
		return txtX0;
	}

	public JTextField getTxtX1() {
		return txtX1;
	}

	public JComboBox<String> getCmbxMethod() {
		return cmbxMethod;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
