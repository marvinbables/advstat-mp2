package method;
public abstract class Method {

    public enum Approach{
        Bisection,
        Newton,
        Polynomial,
        RegulaFalsi,
        Secant
    }
    
	public int iteration;
    protected Approach approach;
    protected double threshold;
	
	public Method(Approach approach, int iteration, double threshold) {
		this.approach = approach;
        this.iteration = iteration;
        this.threshold = threshold;
	}

}
