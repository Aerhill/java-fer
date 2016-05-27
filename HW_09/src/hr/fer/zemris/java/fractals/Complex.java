package hr.fer.zemris.java.fractals;

import java.util.Arrays;
import java.util.List;

/**
 * This is an implementation of immutable model of complex number denoted. Each
 * method returns a new instance of {@link Complex} class. This class contains
 * constants:
 * <ul>
 * <li><code>ZERO</code>: 0+i0</li>
 * <li><code>ONE</code>: 1+i0</li>
 * <li><code>ONE_NEG</code>: -1+i0</li>
 * <li><code>IM</code>: 0+i1</li>
 * <li><code>IM_NEG</code>: 0-i1</li>
 * </ul>
 * 
 * @author Ante Spajic
 *
 */
public class Complex {

	/**
	 * <code>0 + i0</code> constant
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * <code>1 + i0</code> constant
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * <code>-1 + i0</code> constant
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * <code>0 + i</code> constant
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * <code>0 - i</code> constant
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * real part of complex number
	 */
	private double re;
	/**
	 * imaginary part of complex number
	 */
	private double im;

	public Complex() {
		this(0, 0);
	}

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Returns the module of this complex number
	 * 
	 * @return module of this complex number
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}

	/**
	 * Returns this complex numbers real part
	 * 
	 * @return the real part
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Returns this complex numbers imaginary part
	 * 
	 * @return the imaginary part
	 */
	public double getIm() {
		return im;
	}

	/**
	 * Returns the angle of this complex number
	 * 
	 * @return angle of this complex number
	 */
	public double angle() {
		return Math.atan2(im, re) < 0 ? Math.atan2(im, re) + 2 * Math.PI : Math.atan2(im, re);
	}

	/**
	 * Returns a new complex number representing a product <i>this*c</i>
	 * 
	 * @param c
	 *            complex number to multiply with
	 * @return a new complex number that is a product of two
	 */
	public Complex multiply(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		double real = this.re * c.re - this.im * c.im;
		double imag = this.re * c.im + this.im * c.re;
		return new Complex(real, imag);
	}

	/**
	 * returns <i> this/c </i>
	 * 
	 * @param c
	 *            complex number that is a divisor
	 * @return complex number that is a result of division
	 */
	public Complex divide(Complex c) {
		if (c == null) {
			throw new NullPointerException("Value given can not be null.");
		}
		if (c.re == 0 && c.im == 0) {
			throw new IllegalArgumentException("Value given can not be zero.");
		}
		double magnitude = this.module() / c.module();
		double angle = this.angle() - c.angle();
		return new Complex(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * returns <i> this+c </i>
	 * 
	 * @param c
	 *            complex number to add to this one
	 * @return addition of this + provided complex number
	 */
	public Complex add(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException("Invalid operand, can't be null");
		}
		double real = this.re + c.re;
		double imag = this.im + c.im;
		return new Complex(real, imag);
	}

	/**
	 * returns <i>this-c</i>
	 * 
	 * @param c
	 *            complex number to subtract from this
	 * @return result of subraction this-c
	 */
	public Complex sub(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException("Invalid operand, can't be null");
		}
		double real = this.re - c.re;
		double imag = this.im - c.im;
		return new Complex(real, imag);
	}

	/**
	 * returns -this
	 * 
	 * @return returns a new complex number that is this negated
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * returns this<sup>n</sup>, n is non-negative integer
	 * 
	 * @param n
	 *            power
	 * @return new complex number that is this on n-th power
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Power must be greater or equal to 0");
		}
		double magnitude = Math.pow(module(), n);
		double angle = angle() * n;
		return new Complex(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * returns n-th root of this, n is positive integer
	 * 
	 * @param n
	 *            requested root
	 * @return list with complex roots
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("You requested a negative or 0 root. Root must be positive");
		}
		double rootAngle = angle() / n;
		double rootMagnitude = Math.pow(module(), 1. / n);
		Complex[] roots = new Complex[n];
		for (int i = 0; i < n; i++) {
			roots[i] = new Complex(rootMagnitude * Math.cos(rootAngle), rootMagnitude * Math.sin(rootAngle));
			rootAngle += 2 * Math.PI / n;
		}
		return Arrays.asList(roots);
	}

	@Override
	public String toString() {
		if (im == 0)
			return re + "";
		if (re == 0)
			return im + "i";
		if (im < 0)
			return re + " - " + (-im) + "i";
		return re + " + " + im + "i";
	}

	public void setReal(double re) {
		this.re = re;
	}

	public void setImaginary(double im) {
		this.im = im;
	}
}
