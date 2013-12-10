package model;

import java.util.ArrayList;

import method.RegulaFalsi;
import method.Secant;

public class Model {

	ArrayList<Iteration> iterations = null;
	
	public void compute(double x0, double x1, int numIteration, int methodType) {
		System.out.println(x0 + " " + x1 + " " + numIteration + " " + methodType);
		switch(methodType) {
			// regula falsi
			case 0:
				RegulaFalsi regula = new RegulaFalsi(numIteration);
				iterations = regula.compute(x0, x1);
				System.out.println(iterations.size());
				break;
			// secant
			case 1:
				Secant secant = new Secant(numIteration);
				iterations = secant.compute(x0, x1);
				break;
		}
	}
	
	public ArrayList<Iteration> getIterations(){
		return iterations;
	}
	
}
