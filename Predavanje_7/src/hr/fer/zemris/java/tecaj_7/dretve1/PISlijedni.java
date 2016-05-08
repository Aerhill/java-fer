package hr.fer.zemris.java.tecaj_7.dretve1;

import java.util.Random;

public class PISlijedni {

	public static void main(String[] args) {
		final int NUMBER_OF_SAMPLES = 1_000_000;
		
		double pi = izracunajSlijedno(NUMBER_OF_SAMPLES);
		System.out.println("PI = " + pi);
		
	}
	
	private static double izracunajSlijedno(int numberOfSamples){
		int inside = PIUtil.testNumberOfPointsInCircle(numberOfSamples, new Random());
		return 4.0 * inside/numberOfSamples;
	}
}
