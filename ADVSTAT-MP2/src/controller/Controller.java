package controller;

import model.Model;
import view.View;
@SuppressWarnings("unused")
public class Controller {
	private View view;
	private Model model;
	
	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	
	
	
}

