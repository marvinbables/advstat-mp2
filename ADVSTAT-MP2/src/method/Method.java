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
    private Approach approach;
	
	public Method(Approach approach, int iteration) {
		this.approach = approach;
        this.iteration = iteration;
	}

}
