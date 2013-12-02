package model.polynomial;

import java.util.Comparator;

public class ReverseTermComparator implements Comparator<Term> {
	@Override
	public int compare(Term o1, Term o2) {
		return o1.exponent - o2.exponent;
	}
}

