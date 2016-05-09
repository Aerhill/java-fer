package hr.fer.zemris.java.fractals;

public class Test {

	public static void main(String[] args) {
		Complex[] roots = {
				new Complex(1, 0),
				new Complex(0, 1),
				new Complex(-1, 0),
				new Complex(0, -1)
			};
		ComplexRootedPolynomial pol = new ComplexRootedPolynomial(roots);
		//System.out.println(pol.apply(new Complex(3, 0)).toString());
		
		System.out.println(pol.toComplexPolynom().derive());
	}
}
