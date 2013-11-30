package method;

import java.util.ArrayList;

import model.Iteration;

public abstract class Method {
	int iteration;
	
	public Method(int iteration) {
		this.iteration = iteration;
	}

	public int getNumberofIteration() {
		return iteration;
	}

	public void setIteration(int iteration) {
		this.iteration = iteration;
	}
}
