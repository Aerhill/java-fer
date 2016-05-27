package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * The primes list model that provides a <code>List</code> with its contents.
 * 
 * @author Ante Spajic
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/** List with all primes. */
	private List<Integer> primes = new ArrayList<>();
	
	/** first prime candidate. */
	private int candidateforPrime = 2;
	
	/** Listeners of this model. */
	private List<ListDataListener> listeners = new CopyOnWriteArrayList<>();

	/**
	 * Constructor that creates a new primes list with first element.
	 */
	public PrimListModel() {
		primes.add(1);
	}

	@Override
	public int getSize() {
		return primes.size();
	}

	/**
	 * Generates next prime number that is to a displayed list and notifies all
	 * listeners about it.
	 */
	public void next() {
		int index = primes.size();
		for (int i = 2; i <= candidateforPrime/2; i++) {
			if (candidateforPrime % i == 0) {
				candidateforPrime++;
				i = 2;
			}
		}
		primes.add(candidateforPrime++);
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index);
		fireIntervalAdded(event);
	}

	/**
	 * Notifies all listeners when an interval has been added, event information
	 * is provided as an argument.
	 * 
	 * @param e
	 *            event information
	 */
	private void fireIntervalAdded(ListDataEvent e) {
		for (ListDataListener l : listeners) {
			l.intervalAdded(e);
		}
	}

	@Override
	public Integer getElementAt(int index) {
		return primes.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

}
