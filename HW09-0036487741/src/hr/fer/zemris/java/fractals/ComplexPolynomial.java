package hr.fer.zemris.java.fractals;

import java.util.Arrays;

public class ComplexPolynomial {

	private Complex[] factors;

	// constructor
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	// returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	public short order() {
		return (short) (factors.length - 1);
	}

	// computes a new polynomial this*p
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		if(p == null) {
			throw new IllegalArgumentException("Polynom can't be null");
		}
		int high = factors.length - 1 + p.factors.length;
		Complex[] result = new Complex[high];
		for (int i = 0; i < factors.length; i++) {
			for (int j = 0; j < p.factors.length ; j++) {
				result[i + j] = factors[i].multiply(p.factors[j]);
			}
		}
		return new ComplexPolynomial(result);
	}

	// computes first derivative of this polynomial; for example, for
	// (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	public ComplexPolynomial derive() {
		if (factors.length == 1) {
			return new ComplexPolynomial(Complex.ZERO);
		}
		Complex[] result = new Complex[factors.length - 1];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Complex(factors[i + 1].getRe() * (i + 1),
					factors[i + 1].getIm() * (i + 1));
		}
		return new ComplexPolynomial(result);
	}

	// computes polynomial value at given point z
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		for (int i = 0; i < factors.length; i++) {
			result = result
					.add(factors[i].multiply(z.power(factors.length - i - 1)));
		}

		return result;
	}

	@Override
	public String toString() {
		return Arrays.toString(factors);
	}
}
