package method;

import java.util.ArrayList;

public class Polynomial {
	
	public static ArrayList<Double> function;
	public static ArrayList<Double> derivative;
	
	static {
		function = new ArrayList<Double>();
		derivative = new ArrayList<Double>();
	}
	// Multiple parameters of type double
	/*
	 * setEquation(1, 2, -78.8, 0);
	 */
	public static void setPolynomial(double[] args) {
		if(args.length % 2 != 0) {
			System.out.println("Number of parameters must be even.");
			return;
		}
		function.clear();
		for(int i = 0; i < args.length; i++) {
			function.add(args[i]);
		}
		
		derivative.clear();
		for (int i = 0; i < args.length; i+=2) {
			double coefficient = args[i] * args[i+1];
			if(coefficient != 0) {
				derivative.add(coefficient);
				derivative.add(args[i+1] - 1);
			}
		}
	}
	/*
	 // Single parameter of type double[]
	 // This might be used in the future instead of the other function
	 // double d[] = {1, 2, -78.8, 0};
	  * setEquation(d);
	  *
	public static void setEquation(double[] args) {
		if(args.length % 2 == 0) {
			System.out.println("Number of parameters must be even.");
			return;
		}
		function.clear();
		for(int i = 0; i < args.length; i++) {
			function.add(args[i]);
		}
		
		derivative.clear();
		for (int i = 0; i < args.length; i+=2) {
			double coefficient = args[i] * args[i+1];
			if(coefficient != 0) {
				derivative.add(coefficient);
				derivative.add(args[i+1] - 1);
			}
		}
	}
	*/
	public static double f(double x) {
		double result = 0;
		
		for (int i = 0; i < function.size(); i+=2) 
			result += function.get(i) * Math.pow(x, function.get(i+1));
		
		return result;
	}
	
	public static double fprime(double x) {
		double result = 0;
		
		for (int i = 0; i < derivative.size(); i+=2) 
			result += derivative.get(i) * Math.pow(x, derivative.get(i+1));
		
		return result;
	}
	
}
