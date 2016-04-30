package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Class in 'observer pattern' which serves as intermediate object whose
 * reference will be served to 'observer' objects to consume changes from
 * 'subject' object. This implementation 'wraps' subject object that stores
 * <tt>Integer</tt> value.
 * 
 * @author Ante Spajic
 *
 */
public class IntegerStorageChange {

	/**
	 * Subject.
	 */
	private IntegerStorage istorage;

	/**
	 * Subject's old storage value.
	 */
	private int value;

	/**
	 * Subject's current storage value.
	 */
	private int newValue;

	/**
	 * Public constructor receives reference to 'subject' object, and it's
	 * current and previous value.
	 * 
	 * @param istorage
	 *            subject
	 * @param newValue
	 *            current value
	 * @param value
	 *            previous value
	 * @throws NullPointerException
	 *             if subject passed is <tt>null</tt> value
	 */
	public IntegerStorageChange(IntegerStorage istorage, int value, int newValue) {
		if ( istorage == null ){
			throw new NullPointerException("Storage cannot be null");
		}
		this.istorage = istorage;
		this.value = value;
		this.newValue = newValue;
	}

	/**
	 * @return the Integer Storage reference.
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * Getter for the original value before the change.
	 * 
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Getter for the value after the change has ocurred.
	 * 
	 * @return the newValue
	 */
	public int getNewValue() {
		return newValue;
	}

}
