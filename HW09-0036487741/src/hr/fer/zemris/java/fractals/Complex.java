package hr.fer.zemris.java.fractals;

import java.util.Arrays;
import java.util.List;

public class Complex {

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	private double re;
	private double im;

	public Complex() {
	}

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	// returns module of complex number
	public double module() {
		return Math.sqrt(re * re + im * im);
	}

	/**
	 * @return the re
	 */
	public double getRe() {
		return re;
	}

	/**
	 * @return the im
	 */
	public double getIm() {
		return im;
	}

	// returns angle of complex number
	public double angle() {
		return Math.atan2(im, re) < 0 ? Math.atan2(im, re) + 2 * Math.PI
				: Math.atan2(im, re);
	}

	// returns this*c
	public Complex multiply(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		double real = this.re * c.re - this.im * c.im;
		double imag = this.re * c.im + this.im * c.re;
		return new Complex(real, imag);
	}

	// returns this/c
	public Complex divide(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException("Invalid argument : null");
		}
		double a = c.re;
		double b = c.im;
		if (a == 0.0 && b == 0.0) {
			throw new IllegalArgumentException("Cannot divide by 0");
		}
		if (Math.abs(a) < Math.abs(b)) {
			double q = a / b;
			double denominator = a * q + b;
			return new Complex((re * q + im) / denominator,
					(im * q - re) / denominator);
		} else {
			double q = b / a;
			double denominator = b * q + a;
			return new Complex((im * q + re) / denominator,
					(re - re * q) / denominator);
		}
	}

	// returns this+c
	public Complex add(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException(
					"Invalid operand, can't be null");
		}
		double real = this.re + c.re;
		double imag = this.im + c.im;
		return new Complex(real, imag);
	}

	// returns this-c
	public Complex sub(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException(
					"Invalid operand, can't be null");
		}
		double real = this.re - c.re;
		double imag = this.im - c.im;
		return new Complex(real, imag);
	}

	// returns -this
	public Complex negate() {
		return new Complex(-re, -im);
	}

	// returns this^n, n is non-negative integer
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException(
					"Power must be greater or equal to 0");
		}
		double magnitude = Math.pow(module(), n);
		double angle = angle() * n;
		return new Complex(magnitude * Math.cos(angle),
				magnitude * Math.sin(angle));
	}

	// returns n-th root of this, n is positive integer
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException(
					"You requested a negative or 0 root. Root must be positive");
		}
		double rootAngle = angle() / n;
		double rootMagnitude = Math.pow(module(), 1. / n);
		Complex[] roots = new Complex[n];
		for (int i = 0; i < n; i++) {
			roots[i] = new Complex(rootMagnitude * Math.cos(rootAngle),
					rootMagnitude * Math.sin(rootAngle));
			rootAngle += 2 * Math.PI / n;
		}
		return Arrays.asList(roots);
	}

	@Override
	public String toString() {
		if (im == 0) return re + "";
		if (re == 0) return im + "i";
		if (im < 0) return re + " - " + (-im) + "i";
		return re + " + " + im + "i";
	}
}
