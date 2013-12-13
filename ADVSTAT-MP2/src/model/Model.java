package model;

import java.util.ArrayList;

import method.Method.Approach;
import method.RegulaFalsi;
import method.Secant;

public class Model {
    public static final Model Instance = new Model();
    
	ArrayList<Iteration> iterations = null;
	
	public void compute(double leftInterval, double rightInterval, int numIteration, double threshold, Approach approach) {
	    System.out.println("The left interval is " + leftInterval);
	    System.out.println("The right interval is " + rightInterval);
	    System.out.println("Do " + approach.toString() + " " + numIteration + " times.");
	    switch(approach) {
			case RegulaFalsi:
				RegulaFalsi regula = new RegulaFalsi(numIteration, threshold);
				iterations = regula.compute(leftInterval, rightInterval);
				break;
			case Secant:
				Secant secant = new Secant(numIteration, threshold);
				iterations = secant.compute(leftInterval, rightInterval);
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
