package hr.fer.zemris.java.tecaj.hw6.demo3;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PrimesCollection implements Iterable<Integer> {

	private int numberOfPrimes;

	public PrimesCollection(int numberOfPrimes) {
		this.numberOfPrimes = numberOfPrimes;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimeNumberIterator();
	}

	private class PrimeNumberIterator implements Iterator<Integer> {

		private int primesLeft = numberOfPrimes;
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
