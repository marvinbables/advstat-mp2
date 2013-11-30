package model.polynomial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Polynomial {
	private List<Term> terms;
	private double[] doubles;
	
	public Polynomial(List<Term> terms){
		this.terms = terms;
		Collections.sort(terms, new TermComparator());
		
	}
	
	public Polynomial(double[] coefficients) throws Exception{
		String text = "";
		
		for (int i = 0; i < coefficients.length; i++) {
			if(i % 2 != 0) {
				if(coefficients[i-1] != 0) {
					if(coefficients[i] != 0) {
						if(Math.abs(coefficients[i]) - Math.floor(Math.abs(coefficients[i])) == 0) {
							int number = (int)coefficients[i];
							if(number < 0)
								text += "x^(" + number + ")";
							else {
								if(number == 1)
									text += "x";
								else
									text += "x^" + number;
								
							}
						}
						else {
							if(coefficients[i] < 0)
								text += "x^(" + coefficients[i] + ")";
							else {
								if(coefficients[i] == 1)
									text += "x";
								else
									text += "x^" + coefficients[i];
							}
						}
					}
				}
			}
			else {
				if(coefficients[i] != 0) {
					int num = 0;

					if( Math.abs(coefficients[i]) - Math.floor(Math.abs(coefficients[i])) == 0) {
						num = (int)coefficients[i];
						if(num < 0) {
							text += " - " + (num == -1? "" : Math.abs(num));
						}
						else {
							if(i != 0)
								text += " + " + (num == 1? "" : num);
							else
								text += (num == 1? "" : num);
						}
					}
					else {
						if(coefficients[i] < 0) {
							text += " - " + (coefficients[i] == -1? "" : Math.abs(coefficients[i]));
						}
						else {
							if(i != 0)
								text += " + " + (coefficients[i] == 1? "" : coefficients[i]);
							else
								text += (coefficients[i] == 1? "" : coefficients[i]);
						}
					}
				}
			}
		}
		
		
		
		/**
		 * TODO: Marvin
		 * This code needs to be modified in such a way that 
		 * the list of terms were able to be parsed from the 
		 * array of doubles.
		 */
		
		this.terms = new ArrayList<Term>();
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
	
	public Polynomial addTerm(Term a){
		ArrayList<Term> newList = new ArrayList<Term>(terms);
		newList.add(a);
		return new Polynomial(newList);
	}
}
