package hr.fer.zemris.java.fractals;

public class ComplexRootedPolynomial {

	private Complex[] roots;

	// constructor
	public ComplexRootedPolynomial(Complex... roots) {
		if (roots == null) {
			throw new IllegalArgumentException("roots can't be null");
		}
		this.roots = roots;
	}

	// computes polynomial value at given point z
	public Complex apply(Complex z) {
		if (z == null) {
			throw new IllegalArgumentException(
					"Complex number passed can't be null.");
		}
		Complex result = Complex.ONE;
		for (int i = 0; i < roots.length; i++) {
			result = result.multiply(z.sub(roots[i]));
		}
		return result;
	}

	// converts this representation to ComplexPolynomial type
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(roots[0].negate(), Complex.ONE);
        for (int i = 1; i < roots.length; i++) {
            result = result.multiply(new ComplexPolynomial(roots[i].negate(), Complex.ONE));
        }
        return result;
	}

	@Override
	public String toString() {
		return null;
	}

	// finds index of closest root for given complex number z that is within
	// threshold
	// if there is no such root, returns -1
	public int indexOfClosestRootFor(Complex z, double threshold) {
		if (z == null) {
			throw new IllegalArgumentException(
					"Complex number passed can not be null.");
		}
		if (threshold < 0.0) {
			throw new IllegalArgumentException(
					"Defined threshold must be positive number.");
		}
		double min = roots[0].sub(z).module();
		int index = 0;
		for (int i = 0; i < roots.length; i++) {
			double candidate = roots[i].sub(z).module();
			if (candidate < min) {
				min = candidate;
				index = i;
			}
		}
		return min <= threshold ? index : -1;
	}
}
