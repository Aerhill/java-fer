package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * This class represents Newton-Rhapson iteration used to extended to complex
 * functions and to systems of equations.
 * 
 * In numerical analysis, Newton's method (also known as the Newton–Raphson
 * method), named after Isaac Newton and Joseph Raphson, is a method for finding
 * successively better approximations to the roots (or zeroes) of a real-valued
 * function.
 * 
 * <pre>
 *  x : f(x) = 0
 * </pre>
 * 
 * The Newton–Raphson method in one variable is implemented as follows:
 * 
 * The method starts with a function f defined over the real numbers x, the
 * function's derivative f', and an initial guess x0 for a root of the function
 * f. If the function satisfies the assumptions made in the derivation of the
 * formula and the initial guess is close, then a better approximation x1 is
 * 
 * <pre>
 *  x<sub>1</sub> = x<sub>0</sub> - (f(x<sub>0</sub>)/f'(x<sub>0</sub>))
 * </pre>
 * 
 * Geometrically, (x<sub>1</sub>, 0) is the intersection of the x-axis and the
 * tangent of the graph of f at (x<sub>0</sub>, f(x<sub>0</sub>)).
 * 
 * The process is repeated as
 * 
 * <pre>
 * x<sub>n+1</sub> = x<sub>n</sub> - f(x<sub>n</sub>)/f'(x<sub>n</sub>)
 * </pre>
 * 
 * until a sufficiently accurate value is reached.
 * 
 * This algorithm is first in the class of Householder's methods, succeeded by
 * Halley's method. The method can also be extended to complex functions and to
 * systems of equations.
 * 
 * @author Ante Spajic
 *
 */
public class Newton {

	/**
	 * constant that represents predefined number of iterations
	 */
	public static final int MAX_ITER = 16 * 16;
	/**
	 * acceptable root-distance
	 */
	public static final double ROOT_THRESHOLD = 0.002;
	/**
	 * the convergence threshold
	 */
	public static final double CONVERGENCE_THRESHOLD = 0.001;

	/**
	 * Entry point to the program
	 * 
	 * @param args
	 *            unused command line arguments
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		int i = 0;
		List<Complex> rootList = new ArrayList<>();
		while (true) {
			System.out.print("Root " + ++i + ">");
			String s = sc.nextLine();
			if (s.toLowerCase().equals("done")) {
				break;
			}
			Complex c = parse(s);
			if (c == null) {
				System.out.println("Can't parse");
				System.exit(1);
			}
			rootList.add(c);
		}
		if (rootList.size() < 2) {
			System.out.println("Expected at least 2 roots you entered only " + i + " , please act accordingly");
			System.exit(1);
		}

		sc.close();
		ComplexRootedPolynomial roots = new ComplexRootedPolynomial(rootList.toArray(new Complex[0]));
		ComplexPolynomial poly = roots.toComplexPolynom();

		FractalViewer.show(new FractalProducerImpl(roots, poly));
	}

	/**
	 * Class used to compute color of each pixel from fractal that was created
	 * from a complex polynomial.
	 * 
	 * @author Ante Spajic
	 *
	 */
	public static class ComputationJob implements Callable<Void> {

		/**
		 * minimum and maximum real part of complex number
		 */
		double reMin;
		double reMax;

		/**
		 * minimum and maximum imaginary part of complex number
		 */
		double imMin;
		double imMax;

		/**
		 * width and height of raster
		 */
		int width;
		int height;

		/**
		 * starting and ending line of raster to generate
		 */
		int yMin;
		int yMax;

		/**
		 * array containing color indexes
		 */
		short[] data;

		/**
		 * entered complex polynom roots
		 */
		ComplexRootedPolynomial roots;

		/**
		 * complex polynomial with provided roots
		 */
		ComplexPolynomial poly;

		/**
		 * the offset in array
		 */
		int offset;

		/**
		 * Creates a new iterative computation job.
		 * 
		 * @param reMin
		 *            minimum real part of complex number
		 * @param reMax
		 *            maxiumum real part of complex number
		 * @param imMin
		 *            minimum imaginary part of complex number
		 * @param imMax
		 *            maximum imaginary part of complex number
		 * @param width
		 *            width of raster
		 * @param height
		 *            height of raster
		 * @param yMin
		 *            starting line of raster to generate
		 * @param yMax
		 *            ending line of raster to generate
		 * @param data
		 *            data of color indexes
		 * @param roots
		 *            entered complex polynom roots
		 * @param poly
		 *            complex polynomial with provided roots
		 * @param offset
		 *            the offset
		 */
		public ComputationJob(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, short[] data, ComplexRootedPolynomial roots, ComplexPolynomial poly, int offset) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
			this.poly = poly;
			this.roots = roots;
			this.offset = offset;
		}

		@Override
		public Void call() {

			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cre, cim);
					Complex zn1;
					double module;
					int iter = 0;
					do {
						zn1 = zn.sub(poly.apply(zn).divide(poly.derive().apply(zn)));
						module = zn1.sub(zn).module();
						zn = zn1;
						iter++;
					} while (module > CONVERGENCE_THRESHOLD && iter < MAX_ITER);
					int index = roots.indexOfClosestRootFor(zn1, ROOT_THRESHOLD);
					if (index == -1) {
						data[offset++] = 0;
					} else {
						data[offset++] = (short) index;
					}
				}
			}

			return null;
		}
	}

	/**
	 * Class that implements {@link IFractalProducer} and is used to produce
	 * fractals using Newton-Raphson iteration. As you are surely aware, for
	 * about three-hundred years we know that each function that is k-times
	 * differentiable around a given point x0 can be approximated by a k-th
	 * order Taylor-polynomial.
	 * 
	 * @author Ante Spajic
	 *
	 */
	public static class FractalProducerImpl implements IFractalProducer {

		/**
		 * entered roots
		 */
		private ComplexRootedPolynomial roots;
		/**
		 * polynom whose roots were entered
		 */
		private ComplexPolynomial polynom;

		public FractalProducerImpl(ComplexRootedPolynomial roots, ComplexPolynomial poly) {
			this.roots = roots;
			this.polynom = poly;
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {
			System.out.println("Begininng calculations...");
			short[] data = new short[width * height];
			final int jobCount = 8 * Runtime.getRuntime().availableProcessors();
			int part = height / jobCount;

			ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), (r) -> {
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			});
			List<Future<Void>> results = new ArrayList<>();

			for (int i = 0; i < jobCount; i++) {
				int yMin = i * part;
				int yMax = (i + 1) * part - 1;
				if (i == jobCount - 1) {
					yMax = height - 1;
				}
				ComputationJob job = new ComputationJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data,
						roots, polynom, yMin * width);
				results.add(pool.submit(job));
			}
			for (Future<Void> job : results) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			pool.shutdown();
			System.out.println("Calculations done. Notifying observer => GUI!");
			observer.acceptResult(data, (short) (polynom.order() + 1), requestNo);
		}
	}

	/**
	 * Method that parses entered complex number and creates a new Complex
	 * number or returns <code>null</code> if an invalid string was entered.
	 * 
	 * @param s
	 *            entered string to be parsed
	 * @return complex number if a valid string was entered or <code>null</code>
	 *         if it couldn't be parsed
	 */
	private static Complex parse(String s) {
		s = s.replaceAll("\\s+", "");
		Complex parsed = null;
		if (s.contains(String.valueOf("+")) || (s.contains(String.valueOf("-")) && s.lastIndexOf('-') > 0)) {
			String re = "";
			String im = "";
			s = s.replaceAll("i", "");
			if (s.indexOf('+') > 0) {
				re = s.substring(0, s.indexOf('+'));
				im = s.substring(s.indexOf('+') + 1, s.length());
				parsed = new Complex(Double.parseDouble(re), Double.parseDouble(im));
			} else if (s.lastIndexOf('-') > 0) {
				re = s.substring(0, s.lastIndexOf('-'));
				im = s.substring(s.lastIndexOf('-') + 1, s.length());
				parsed = new Complex(Double.parseDouble(re), -Double.parseDouble(im));
			}
		} else {
			if (s.endsWith("i")) {
				s = s.replaceAll("i", "");
				s = s.trim().length() == 0 ? "1" : s;
				s = s.equals("-") ? "-1" : s;
				parsed = new Complex(0, Double.parseDouble(s));
			} else {
				parsed = new Complex(Double.parseDouble(s), 0);
			}
		}
		return parsed;
	}

}
