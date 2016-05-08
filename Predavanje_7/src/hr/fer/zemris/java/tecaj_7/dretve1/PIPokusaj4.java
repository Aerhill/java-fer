package hr.fer.zemris.java.tecaj_7.dretve1;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PIPokusaj4 {

	public static void main(String[] args) {

		final int NUMBER_OF_SAMPLES = 100_000_000;
		
		ExecutorService pool = Executors.newFixedThreadPool(2);
		
		double pi = izracunaj(NUMBER_OF_SAMPLES, pool);
		
		System.out.println("PI = " + pi);
		
		pool.shutdown();
	}

	private static double izracunaj(int numberOfSamples, ExecutorService pool) {
		class Posao implements Runnable {
			int samples;
			int inside;
			public Posao(int samples){
				this.samples = samples;
			}
			@Override
			public void run() {
				inside = PIUtil.testNumberOfPointsInCircle(samples, new Random());
			}
		}
		Posao p1 = new Posao(numberOfSamples/2);
		Posao p2 = new Posao(numberOfSamples - numberOfSamples/2);
		
		Future<?> i1 = pool.submit(p1);
		Future<?> i2 = pool.submit(p2);

		while(true){
			try {
				i1.get();
				break;
			} catch (InterruptedException | ExecutionException e) {
			}
		}
		while(true){
			try {
				i2.get();
				break;
			} catch (InterruptedException | ExecutionException e) {
			}
		}
		return 4.0 * (p1.inside + p2.inside) / numberOfSamples;
	}

}
