package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import method.RegulaFalsi;
import method.Secant;
import model.Iteration;
import model.Model;
import view.View;

public class Controller {
	
	
	View view;
	Model model;
	
	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;
		this.view.addActionListener(new ParameterListener());
	}
	
	class ParameterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int num, x0, x1, methodType;
				methodType = view.getSelectedMethod();
				num = view.getIterations();
				x0 = view.getX0();
				x1 = view.getX1();
				System.out.println(x0 + " " + x1 + " " + num + " " + methodType);
				model.compute(x0, x1, num, methodType);
				
			} catch(NumberFormatException ex) {
				System.err.println("Invalid input");
			}
			
			
		}
		
		public boolean checkIfValid() {
			
			
			
			return true;
		}
	}
	
}
