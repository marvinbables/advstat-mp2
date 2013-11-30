package model;

public class Iteration {
	// For bracketing methods (bisection, regula falsi)
	private double x0, x1, x2;
	private double y0, y1, y2;
	
	// For open methods (newton, secant)
	private double x, y, yprime;
	
	/**
	 * - Initialization block -
	 * Called at the beginning of every constructor
	 */
	{
		x0 = x1 = x2 = 0;
		y0 = y1 = y2 = 0;
		x = y = yprime = 0;
	}
	
	/**
	 * Used for bracketing methods (Bisection, Regula Falsi / False Position)
	 * @param x0
	 * @param x1
	 * @param x2
	 * @param y0
	 * @param y1
	 * @param y2
	 */
	public Iteration(double x0, double x1, double x2, double y0, double y1,
			double y2) {
		super();
		this.x0 = x0;
		this.x1 = x1;
		this.x2 = x2;
		this.y0 = y0;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	/**
	 * Used for open methods (Newton-Raphson, Secant)
	 * @param x
	 * @param y
	 * @param yprime
	 */
	public Iteration(double x, double y, double yprime) {
		super();
		this.x = x;
		this.y = y;
		this.yprime = yprime;
	}

	public double getX0() {
		return x0;
	}

	public void setX0(double x0) {
		this.x0 = x0;
	}

	public double getX1() {
		return x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public double getX2() {
		return x2;
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

	public double getY0() {
		return y0;
	}

	public void setY0(double y0) {
		this.y0 = y0;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(double y2) {
		this.y2 = y2;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getYprime() {
		return yprime;
	}

	public void setYprime(double yprime) {
		this.yprime = yprime;
	}
	
	
}
