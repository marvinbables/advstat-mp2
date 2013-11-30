package model.polynomial;

import java.util.Collections;
import java.util.List;

public class Polynomial {
	private List<Term> terms;
	private double[] doubles;
	
	public Polynomial(List<Term> terms){
		this.terms = terms;
		Collections.sort(terms, new TermComparator());
		
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Term term : terms) {
			sb.append( term.toString() );
			if (++i != terms.size())
				sb.append( " + ");
		}
		return sb.toString();
	}
	
	public List<Term> getTerms() {
		return terms;
	}
	public double[] getDoubles() {
		return doubles;
	}
}
