package hr.fer.zemris.java.tecaj.pred4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SlijedParnihBrojeva implements Iterable<Integer> {

	private int prvi;
	private int ukupno;
	
	public SlijedParnihBrojeva(int prvi, int ukupno) {
		if(prvi % 2 != 0) {
			throw new IllegalArgumentException(prvi + " nije paran broj!");
		}
		if(ukupno < 1) {
			throw new IllegalArgumentException("Veće od 1");
		}
		this.prvi = prvi;
		this.ukupno = ukupno;
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new MojIterator();
	}

	private class MojIterator implements Iterator<Integer> {

		private int preostaloBrojeva = ukupno;
		private int trenutni = prvi;
		
		@Override
		public boolean hasNext() {
			return preostaloBrojeva > 0;
		}

		@Override
		public Integer next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			int vrati = trenutni;
			trenutni += 2;
			preostaloBrojeva--;
			return vrati;
		}
		
	}
	
}
