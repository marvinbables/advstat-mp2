package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import method.Polynomial;


public class ParameterPanel extends JPanel implements KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtPolynomial, txtIterations, txtX0, txtX1;
	private JButton btnCompute;
	private JLabel lblPolynomial;
	private JComboBox<String> cmbxMethod;
	private int selectedMethod;
	private boolean SHOW_BORDER = !true;
	
	private JLabel lblx1;
	
	public ParameterPanel() {
		setPreferredSize(new Dimension(400, 110));
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		createComponents();
	}

	private void createComponents() {
		JLabel lblPolym = new JLabel("Polynomial");
		lblPolym.setPreferredSize(new Dimension(70, 30));
		add(lblPolym);
		
		txtPolynomial = new JTextField();
		txtPolynomial.setPreferredSize(new Dimension(100, 30));
		txtPolynomial.addKeyListener(this);
		add(txtPolynomial);
		
		lblPolynomial = new JLabel();
		lblPolynomial.setPreferredSize(new Dimension(190, 30));
		lblPolynomial.setHorizontalAlignment(JTextField.CENTER);
		lblPolynomial.setText("x^2 + x + 3");
		add(lblPolynomial);
		
		JPanel panelMethod = new JPanel();
		panelMethod.setPreferredSize(new Dimension(300, 40));
		panelMethod.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		selectedMethod = 0;
		cmbxMethod = new JComboBox<String>();
		cmbxMethod.setPreferredSize(new Dimension(130, 30));
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
			/*
			private void updateMethodPanel() {
				switch (selectedMethod) {
				case 0:
					
					break;
				case 3:
					txtX1.setVisible(false);
					lblx1.setVisible(false);
					break;
				default:
					break;
				}
				
			}
			*/
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
			lblPolynomial.setBorder(BorderFactory.createEtchedBorder());
			lblIteration.setBorder(BorderFactory.createEtchedBorder());
			lblPolym.setBorder(BorderFactory.createEtchedBorder());
			panelMethod.setBorder(BorderFactory.createEtchedBorder());
			lblx0.setBorder(BorderFactory.createEtchedBorder());
			lblx1.setBorder(BorderFactory.createEtchedBorder());
			setBorder(BorderFactory.createEtchedBorder());
		}
	}

	public JTextField getTxtPolynomial() {
		return txtPolynomial;
	}

	public JLabel getLblPolynomial() {
		return lblPolynomial;
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		String input[] = txtPolynomial.getText().split(" ");
		double d = 0, numbers[] = new double[input.length];
		boolean error = false;
		txtPolynomial.setBackground(Color.white);
		
		if(input.length % 2 != 0)
			txtPolynomial.setBackground(Color.pink);
		else {
			for (int i = 0; i < input.length; i++) {
				try {
					d = Double.parseDouble(input[i]);
					numbers[i] = d;
				} catch(NumberFormatException ex) {
					error = true;
					txtPolynomial.setBackground(Color.pink);
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
		lblPolynomial.setText(text);
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

}
