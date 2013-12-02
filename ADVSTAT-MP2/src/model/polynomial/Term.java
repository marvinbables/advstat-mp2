package model.polynomial;

import java.text.DecimalFormat;

public class Term {
	public double coefficient;
	public int exponent;

	public Term (double coefficient, int exponent) {
		this.coefficient = coefficient;
		this.exponent = exponent;
	}

	
	@Override
	public String toString(){
		return getCoefficient() + getVariable() + getExponentText();
	}

	private String getCoefficient() {
		if (coefficient == 0) return "";
		if (coefficient == 1 && exponent != 0) return "";
		return displayDouble(coefficient);
	}

	private String getVariable() {
		if (coefficient == 0) return "";
		if (exponent == 0) return "";
		return "x";
	}

	private String getExponentText() {
		if (coefficient == 0) return "";
		if (exponent == 0)return "";
		if (exponent == 1)return "";
		return "^" + displayDouble(exponent);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Term == false) return false;
		Term src = (Term)obj;
		return src.coefficient == coefficient && src.exponent == exponent;
	};
	
	private String displayDouble(double d){
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
		if (d == Math.floor(d))
			return String.valueOf((int)d);
		return new Double(df2.format(d)).toString();
	}
	
	public Term add(Term b){
		if (b.exponent == this.exponent){
			return new Term(this.coefficient + b.coefficient, exponent);
		}
		return null;
	}
}