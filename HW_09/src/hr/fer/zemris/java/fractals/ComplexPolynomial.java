package hr.fer.zemris.java.fractals;

/**
 * Class that represents an immutable model of coefficient-based complex
 * polynomial.
 * 
 * @author Ante Spajic
 *
 */
public class ComplexPolynomial {

	/**
	 * cofficients of this polynomial
	 */
	private Complex[] factors;

	/**
	 * Creates a new complex polynomial with given complex factors.
	 * 
	 * @param factors
	 *            complex factors of this polynomial
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	/**
	 * returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	 * 
	 * @return order of this polynom
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * computes a new polynomial <i>this*p</i>
	 * 
	 * @param p
	 *            a polynomial to multiply this with
	 * @return a new polynomial that is a product
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		if (p == null) {
			throw new IllegalArgumentException("Polynom can't be null");
		}
		int high = factors.length - 1 + p.factors.length;
		Complex[] result = new Complex[high];
		for (int i = 0; i < result.length; i++) {
			result[i] = Complex.ZERO;
		}
		for (int i = 0; i < factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				result[i + j] = result[i + j].add(factors[i].multiply(p.factors[j]));
			}
		}
		return new ComplexPolynomial(result);
	}

	/**
	 * computes first derivative of this polynomial; for example, for
	 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * 
	 * @return 1st derivation of this polynomial
	 */
	public ComplexPolynomial derive() {
		if (factors.length == 1) {
			return new ComplexPolynomial(Complex.ZERO);
		}
		Complex[] result = new Complex[factors.length - 1];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Complex(factors[i].getRe() * (result.length - i), factors[i].getIm() * (result.length - i));
		}
		return new ComplexPolynomial(result);
	}

	/**
	 * computes polynomial value at given point z
	 * 
	 * @param z
	 *            point to compute the polynomial value at
	 * @return a complex number
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		for (int i = 0; i < factors.length; i++) {
			result = result.add(factors[i].multiply(z.power(factors.length - i - 1)));
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < factors.length - 2; i++) {
			if (factors[i].getRe() != 0 || factors[i].getIm() != 0) {
				sb.append("(" + factors[i].toString() + ")" + "*z^" + (factors.length - i - 1) + " + ");
			}
		}
		if (factors[factors.length - 2].getRe() != 0 || factors[factors.length - 2].getIm() != 0) {
			sb.append("(" + factors[factors.length - 2].toString() + ")" + "*z + ");
		}
		if (factors[factors.length - 1].getRe() != 0 || factors[factors.length - 1].getIm() != 0) {
			sb.append("(" + factors[factors.length - 1].toString() + ")");
		}
		return sb.toString();

	}
}
