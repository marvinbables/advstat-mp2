package view.listeners;

import method.Method.Approach;
import model.polynomial.Polynomial;


public interface GraphListener {
	public class GraphParameters {
        public static GraphParameters LastInstance;
        public static double          StartX = -5;
        public static double          EndX   = 5;
        public Polynomial             polynomial;
        public Approach               approach;
        public int                    iterations;

		public GraphParameters(Polynomial currentPolynomial, int iterations, Approach approach) {
			super();
			GraphParameters.LastInstance = this;
			this.iterations = iterations;
            this.approach = approach;
			this.polynomial = currentPolynomial;
		}
	}
	
	void GraphRequested(GraphParameters parameters);
}
