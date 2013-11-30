package method;

import java.util.ArrayList;

import model.Iteration;

public class Newton extends Method{
	
	public Newton(int iteration) {
		super(iteration);
	}
	
	public ArrayList<Iteration> compute(double x) {
		ArrayList<Iteration> iterations = new ArrayList<Iteration>();
		int i = 1;
		double y = Polynomial.f(x);
		double yprime = Polynomial.fprime(x);
		
		iterations.add(new Iteration(x, y, yprime));
		
		while(i < iteration) {
			// x(i+1) = x(i) - f(x(i)) / f'(x(i))
			x = x - (y/yprime);
			y = Polynomial.f(x);
			yprime = Polynomial.fprime(x);
			
			iterations.add(new Iteration(x, y, yprime));
			i++;
		}
		return iterations;
	}
}
