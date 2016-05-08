package hr.fer.zemris.java.tecaj_7.dretve1;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PIPokusaj5 {

	public static void main(String[] args) {

		final int NUMBER_OF_SAMPLES = 100_000_000;
		
		ExecutorService pool = Executors.newFixedThreadPool(2);
		
		double pi = izracunaj(NUMBER_OF_SAMPLES, pool);
		
		System.out.println("PI = " + pi);
		
		pool.shutdown();
	}

	private static double izracunaj(int numberOfSamples, ExecutorService pool) {
		class Posao implements Callable<Integer> {
			int samples;
			public Posao(int samples){
				this.samples = samples;
			}
			@Override
			public Integer call() {
				return PIUtil.testNumberOfPointsInCircle(samples, new Random());
			}
		}
		Posao p1 = new Posao(numberOfSamples/2);
		Posao p2 = new Posao(numberOfSamples - numberOfSamples/2);
		
		Future<Integer> i1 = pool.submit(p1);
		Future<Integer> i2 = pool.submit(p2);

		int inside1 = 0;
		int inside2 = 0;
		while(true){
			try {
				inside1 = i1.get();
				break;
			} catch (InterruptedException | ExecutionException e) {
			}
		}
		while(true){
			try {
				inside2 = i2.get();
				break;
			} catch (InterruptedException | ExecutionException e) {
			}
		}
		return 4.0 * (inside1 + inside2) / numberOfSamples;
	}

}
