import model.Model;
import view.View;
import controller.Controller;


public class Driver {

	@SuppressWarnings("unused")
    public static void main(String[] args) {
	 	Model model = new Model();
		View view = new View();
		Controller controller = new Controller(view, model);
		
/*		Polynomial.setPolynomial(1, 2, -78.8, 0);
		Secant secant= new Secant(4);
		ArrayList<Iteration> iterations = secant.compute(6, 12);
			for(int i = 0; i < iterations.size(); i++) {
				Iteration iteration = iterations.get(i);
				//System.out.printf("[i = %d] x0: %f ; x1: %f ; x2: %f ; y0: %f ; y1: %f ; y2: %f \n", i, 
				//	iteration.getX0(), iteration.getX1(), iteration.getX2(), iteration.getY0(), iteration.getY1(), iteration.getY2());
				//System.out.printf("[i = %d] x: %f ; f(x) : %f ; f'(x) : %f\n", i, iteration.getX(), iteration.getY(), iteration.getYprime());
			}
*/	}

}
