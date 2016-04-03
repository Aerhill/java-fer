package hr.fer.zemris.java.tecaj.p3.d;


public class Demonstracija {

	static interface Function {
		double valueAt(double x);
	}
	
	static class Kvadriranje implements Function {
		
		@Override
		public double valueAt(double x) {
			return x*x;
		}
	}
	
	static class Korjenovanje implements Function {
		
		@Override
		public double valueAt(double x) {
			return Math.sqrt(x);
		}
	}
	
	public static void main(String[] args) {
		double[] polje = {0.0, 3.14, 2.71, 15.0, 25};
		
		ispisi(polje, new Function() {
			@Override
			public double valueAt(double x) {
				return x*x;
			}
		});
		
		ispisi(polje, x -> {
			return x*x;
		});
		
		ispisi(polje, x -> Math.pow(Math.sin(x+0.45),1.3));
		
		ispisi(polje, Math::sin);
	}

	private static void ispisi(double[] polje, Function f) {
		for(double d : polje){
			double r = f.valueAt(d);
			System.out.printf("f(%.2f)= %.2f%n", d, r);
		}
		
	}
}
