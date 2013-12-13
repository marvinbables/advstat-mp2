package method;

import java.util.ArrayList;

import model.Iteration;

public class Secant extends Method {

	public Secant(int iteration, double threshold) {
		super(Approach.Secant, iteration, threshold);
	}
	
	public ArrayList<Iteration> compute(double x1, double x2) {
		ArrayList<Iteration> iterations = new ArrayList<Iteration>();
		double currFx = Polynomial.f(x2);
		double prevFx = Polynomial.f(x1);
		double prevX = x1, currXi = x2, nextX;
		iterations.add(new Iteration(x1, prevFx, Polynomial.fprime(x1)));
		iterations.add(new Iteration(x2, currFx, Polynomial.fprime(x2)));
		int i = 0;
		
		double error = 999;
		while(i < iteration && error > threshold) {
			nextX = currXi - ( currFx * (prevX - currXi) ) / (prevFx - currFx);
			prevX = currXi;
			prevFx = currFx;
			currXi = nextX;
			currFx = Polynomial.f(currXi);
			
			error = prevFx - currFx;
			
			iterations.add(new Iteration(currXi, currFx, Polynomial.fprime(currXi)));
			i++;
		}
		
		return iterations;
	}
}
