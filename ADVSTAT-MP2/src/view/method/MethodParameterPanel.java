package view.method;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class MethodParameterPanel extends JPanel{

	private final Dimension methodParameterDimension = new Dimension(400, 400);
	public MethodParameterPanel(String title){
		setPreferredSize(methodParameterDimension);
		setBorder(BorderFactory.createTitledBorder(title));
	}
}
