package method;

import java.util.ArrayList;

import model.Iteration;

public class RegulaFalsi extends Method{
	public RegulaFalsi(int iteration, double threshold) {
		super(Approach.RegulaFalsi, iteration, threshold);
	}
	
	public ArrayList<Iteration> compute(double x0, double x1) {
	    System.out.println("f(x) = " + Polynomial.read());
		ArrayList<Iteration> iterations = new ArrayList<Iteration>();
		int i = 0;
		double y0, y1, y2, x2;
		y0 = Polynomial.f(x0);
		y1 = Polynomial.f(x1);
		
		x2 = (x0 * y1 - x1 * y0) / (y1 - y0);
		y2 = Polynomial.f(x2);
		
		i++;
		iterations.add(new Iteration(x0, x1, x2, y0, y1, y2));
		double oldx2 = x2;
		
		double error = 999;
        while(true) {
            if (y2 == 0){
                System.out.println("Reached the root of the function.");
                break;
            }
            if (i >= iteration){ 			
                System.err.println("Iterations have finished.");
                break;
            }
            if (error <= threshold){
                System.out.println("Error difference [" + i +"] was: " + error);
                System.err.println("Threshold reached.");
                break;
            }
			if(y2 != 0) {
				if(y1 * y2 < 0) {
					x0 = x2;
					y0 = y2;
				}
				else {
					x1 = x2;
					y1 = y2;
				}
				
				if (y1 - y0 == 0) {
				    System.out.println("Division by zero. Infinity reached.");
				    break;
				}
				
				oldx2 = x2;
				x2 = (x0 * y1 - x1 * y0) / (y1 - y0);
				y2 = Polynomial.f(x2);
				error = Math.abs(x2 - oldx2);
				System.out.println("Error difference [" + i +"] was: " + error);
				iterations.add(new Iteration(x0, x1, x2, y0, y1, y2));
			}
			i++;
		}
		return iterations;
	}
}
