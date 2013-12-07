package model;

public class Interval {

	private double leftInterval, rightInterval;
	
	public Interval(double left, double right){
		leftInterval = left; 
		rightInterval = right;
	}

	public double getLeftInterval() {
		return leftInterval;
	}

	public void setLeftInterval(double leftInterval) {
		this.leftInterval = leftInterval;
	}

	public double getRightInterval() {
		return rightInterval;
	}

	public void setRightInterval(double rightInterval) {
		this.rightInterval = rightInterval;
	}
	
	
}
