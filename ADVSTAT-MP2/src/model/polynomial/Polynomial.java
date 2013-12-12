package model.polynomial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class used to represent polynomials.
 * This class only handles exponents of the X which 
 * are integral.
 * @author Darren
 *
 */
public class Polynomial {
	private ArrayList<Term> terms;
	private double[] doubles;
	
	public Polynomial(ArrayList<Term> terms){
		this.terms = terms;
		/** Sort in reverse of standard way */
		Collections.sort(terms, new ReverseTermComparator());
		
		/** compute for doubles */
		if (terms.size() > 0){
			// Get the exponent of the highest term
			int length = terms.get(terms.size() - 1).exponent + 1;
			doubles = new double[length];
			
			int termIndex = 0;
			for (int i = 0; i < length; i++){
				try {
					Term term = terms.get(termIndex);
					if (term != null && term.exponent == i){
						doubles[i] = term.coefficient;
						
						// Only move to the next term if the term has been encoded already
						termIndex += 1;
					}else
						doubles[i] = 0;
				}catch (ArrayIndexOutOfBoundsException e){
					doubles[i] = 0;
				}
			}
		}
		
		/** Sort in standard way */
		Collections.sort(terms, new TermComparator());
	}
	
	public Polynomial(double[] coefficients){
		terms = new ArrayList<Term>();
		for (int exponent = 0; exponent < coefficients.length; exponent++){
			double currentCoefficient = coefficients[exponent];
			terms.add(new Term(currentCoefficient, exponent));
		}
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
	
	/**
	 * Adds a term to the polynomial.
	 * If the term already exists, the coefficient is increased.
	 * @param b - the new term to add
	 * @return the resulting polynomial
	 */
	public Polynomial addTerm(Term b){
		ArrayList<Term> newList = new ArrayList<Term>(terms);
		Term term = getTerm(b.exponent);
		if (term == null){
			newList.add(b);
		}else{
			newList.remove(term);
			Term newTerm = term.add(b);
			if (newTerm.coefficient != 0)
				newList.add(newTerm);
		}
		return new Polynomial(newList);
	}
	
	private Term getTerm(int exponent){
		for (Term term : terms) {
			if (term.exponent == exponent)
				return term;
		}
		return null;
	}
	
	public List<Term> getTerms() {
		return terms;
	}
	public double[] getDoubles() {
		return doubles;
	}
	
	
}
