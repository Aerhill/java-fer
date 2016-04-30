package hr.fer.zemris.java.tecaj.hw6.demo3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class <tt>PrimesCollection</tt> represents simple collection of prime numbers
 * which are provided when needed, meaning that they are not stored locally in
 * computer memory and are calculated on call.
 * 
 * @author Ante Spajic
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * Number of primes in this collection.
	 */
	private int numberOfPrimes;

	/**
	 * Public constructor accepts a number of consecutive primes that must be in
	 * this collection.
	 * 
	 * @param numberOfPrimes
	 */
	public PrimesCollection(int numberOfPrimes) {
		if (numberOfPrimes < 1) {
			throw new IllegalArgumentException("Must be greater than 1");
		}
		this.numberOfPrimes = numberOfPrimes;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimeNumberIterator();
	}

	/**
	 * Private nested class provides implementation of iterator over prime
	 * numbers in <tt>PrimesCollection</tt>.
	 * 
	 * @author Ante Spajic
	 *
	 */
	private class PrimeNumberIterator implements Iterator<Integer> {

		/**
		 * Counter for how many primes should be generated.
		 */
		private int primesLeft = numberOfPrimes;

		/**
		 * Prime number candidate
		 */
		private int candidateforPrime = 2;

		@Override
		public boolean hasNext() {
			return primesLeft > 0;
		}

		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			for (int i = 2; i <= candidateforPrime / 2; i++) {
				if (candidateforPrime % i == 0) {
					candidateforPrime++;
					i = 2;
				}
			}
			primesLeft--;
			return candidateforPrime++;
		}

	}

}
