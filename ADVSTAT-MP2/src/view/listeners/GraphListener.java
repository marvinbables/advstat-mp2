package view.listeners;

import model.polynomial.Polynomial;


public interface GraphListener {
	public class GraphParameters {
		public static GraphParameters LastInstance;
		public static double StartX = -5;
		public static double EndX = 5;
		public Polynomial polynomial;

		public GraphParameters(Polynomial currentPolynomial) {
			super();
			GraphParameters.LastInstance = this;
			this.polynomial = currentPolynomial;
		}
	}
	
	void GraphRequested(GraphParameters parameters);
}
