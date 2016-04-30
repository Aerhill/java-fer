package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Implementation of observer from 'observer pattern' that counts number of
 * subject's value property changes since the start of subject tracking.
 * 
 * @author Ante Spajic
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Counter of changes.
	 */
	private int counter = 0;

	@Override
	public void valueChanged(IntegerStorageChange change) {
		System.out.println("Number of value changes since tracking: " + ++counter);
	}

}
