package model;

import java.util.ArrayList;

import method.Method.Approach;
import method.RegulaFalsi;
import method.Secant;

public class Model {

	ArrayList<Iteration> iterations = null;
	
	public void compute(double x0, double x1, int numIteration, Approach approach) {
		System.out.println(x0 + " " + x1 + " " + numIteration + " " + approach);
		switch(approach) {
			case RegulaFalsi:
				RegulaFalsi regula = new RegulaFalsi(numIteration);
				iterations = regula.compute(x0, x1);
				System.out.println(iterations.size());
				break;
			case Secant:
				Secant secant = new Secant(numIteration);
				iterations = secant.compute(x0, x1);
				break;
        case Bisection:
            break;
        case Newton:
            break;
        case Polynomial:
            break;
        default:
            break;
		}
	}
	
	public ArrayList<Iteration> getIterations(){
		return iterations;
	}
	
}
