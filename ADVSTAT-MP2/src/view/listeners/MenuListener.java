package view.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.listeners.GraphListener.GraphParameters;

public class MenuListener implements ActionListener {

	private GraphListener graphListener;

	public void actionPerformed(ActionEvent arg0) {
		double start = -5, end = 5;
		try {
			String startX = JOptionPane.showInputDialog(null, "Please input start x", -5);
			start = Double.parseDouble(startX);
		}catch(Exception e){
			Error("Invalid input!");
		}

		try {
			String EndX = JOptionPane.showInputDialog(null, "Please input end x", 5);
			end = Double.parseDouble(EndX);
		}catch(Exception e){
			Error("Invalid input!");
		}
		
		if (start < end){
			GraphParameters.StartX = start;
			GraphParameters.EndX = end;
			graphListener.GraphRequested(GraphParameters.LastInstance);
		}else{
			Error("Start x needs to be less than end x.");
		}
	}

	private void Error(String mes){
		JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void setGraphListener(GraphListener listener) {
		this.graphListener = listener;
	}

}
