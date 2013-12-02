package view.listeners;

import model.polynomial.Polynomial;


public interface GraphListener {
	public class GraphParameters {
		public Polynomial polynomial;

		public GraphParameters(Polynomial currentPolynomial) {
			super();
			this.polynomial = currentPolynomial;
		}
	}
	
	void GraphRequested(GraphParameters parameters);
}
