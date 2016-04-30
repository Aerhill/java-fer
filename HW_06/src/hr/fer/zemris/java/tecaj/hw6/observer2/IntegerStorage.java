package hr.fer.zemris.java.tecaj.hw6.observer2;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <tt>IntegerStorage</tt> presents implementation of 'subject' object from
 * 'observer pattern'. Subject serves as a container (storage) for
 * <tt>Integer</tt> value.
 * 
 * @author Ante Spajic
 *
 */
public class IntegerStorage {

	/**
	 * Value stored.
	 */
	private int value;

	/**
	 * List of observers subscribed to this subject.
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * Public constructor receives initial value of <tt>Integer</tt> stored as
	 * argument.
	 * 
	 * @param initialValue
	 *            initial value
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Adds observer to subject's list of subscribed observers.
	 * 
	 * @param observer
	 *            observer to be added
	 * @throws IllegalArgumentException
	 *             if observer passed is <tt>null</tt> value
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observer == null) {
			throw new IllegalArgumentException("Observer can't be null");
		}
		if (observers == null) {
			observers = new CopyOnWriteArrayList<>();
		}
		observers.add(observer);
	}

	/**
	 * Removes observer from subject's list of subscribed observers.
	 * 
	 * @param observer
	 *            observer to be removed
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}

	/**
	 * Removes all observers from subject's list of subscribed observers.
	 */
	public void clearObservers() {
		observers.clear();
	}
	
	/**
	 * Getter for subject's current value stored.
	 * 
	 * @return value stored
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Setter for subject's value stored. All observers subscribed to this
	 * subject will be informed about the change in value stored.
	 * 
	 * @param value
	 *            new value
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			// Update current value
			IntegerStorageChange isc = new IntegerStorageChange(this, this.value, value);
			this.value = value;
			// Notify all registered observers
			if (observers != null) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(isc);
				}
			}
		}
	}
}
