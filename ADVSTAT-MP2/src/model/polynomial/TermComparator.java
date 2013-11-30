package model.polynomial;

import java.util.Comparator;

public class TermComparator implements Comparator<Term> {
	@Override
	public int compare(Term o1, Term o2) {
		return o2.exponent - o1.exponent;
	}
}
