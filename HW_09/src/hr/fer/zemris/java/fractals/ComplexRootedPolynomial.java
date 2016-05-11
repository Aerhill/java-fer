package hr.fer.zemris.java.fractals;

/**
 * This is an implementation of immutable model of root-based complex polynomial
 * denoted. Each method returns a new instance of this class.
 * 
 * @author Ante Spajic
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * roots of this polynom
	 */
	private Complex[] roots;

	/**
	 * Creates a new complex rooted polynomial with given roots
	 * 
	 * @param roots
	 *            entered roots of this polynom
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		if (roots == null) {
			throw new IllegalArgumentException("roots can't be null");
		}
		this.roots = roots;
	}

	/**
	 * computes polynomial value at given point z
	 * 
	 * @param z
	 *            value to compute the polynomial at
	 * @return a complex number that we get after we compute it
	 */
	public Complex apply(Complex z) {
		if (z == null) {
			throw new IllegalArgumentException("Complex number passed can't be null.");
		}
		Complex result = Complex.ONE;
		for (int i = 0; i < roots.length; i++) {
			result = result.multiply(z.sub(roots[i]));
		}
		return result;
	}

	/**
	 * converts this representation to ComplexPolynomial type
	 * 
	 * @return polynomial whose solutions are this objects roots
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(Complex.ONE, roots[0].negate());
		for (int i = 1; i < roots.length; i++) {
			result = result.multiply(new ComplexPolynomial(Complex.ONE, roots[i].negate()));
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < roots.length; i++) {
			sb.append("root" + (i + 1) + " = " + roots[i] + " ");
		}

		return sb.toString();
	}

	/**
	 * finds index of closest root for given complex number z that is within
	 * threshold if there is no such root, returns -1
	 * 
	 * @param z
	 *            number to find the closest root to
	 * @param threshold
	 *            discrepancy for closest root
	 * @return index of a root
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		if (z == null) {
			throw new IllegalArgumentException("Complex number passed can not be null.");
		}
		if (threshold < 0) {
			throw new IllegalArgumentException("Defined threshold must be positive number.");
		}
		double distance = Double.MAX_VALUE;
		int index = 0;
		for (int i = 0; i < roots.length; i++) {
			double magnitude = z.sub(roots[i]).module();
			if (magnitude < distance) {
				distance = magnitude;
				index = i;
			}
		}
		return distance <= threshold ? index + 1 : -1;
	}
}
