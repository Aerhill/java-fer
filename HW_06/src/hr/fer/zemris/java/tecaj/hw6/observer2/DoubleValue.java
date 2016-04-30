package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Implementation of observer from 'observer pattern' that displays doubled
 * value of subject's current storage value on standard output. Observer will
 * perform this action for finite number of times, and will then de-register
 * itself from subject.
 * 
 * @author Ante Spajic
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * Number of times action will be performed by this observer.
	 */
	private int numberOfUsagesLeft;

	/**
	 * Public constructor receives value that represents number of times action
	 * will be performed by this observer.
	 * 
	 * @param n
	 */
	public DoubleValue(int n) {
		this.numberOfUsagesLeft = n;
	}

	@Override
	public void valueChanged(IntegerStorageChange change) {
		if (numberOfUsagesLeft-- <= 0) {
			change.getIstorage().removeObserver(this);
			return;
		}
		System.out.println("Double value: " + 2 * change.getNewValue());
	}

}
