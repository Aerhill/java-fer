package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * <tt>IntegerStorageObserver</tt> interface describes 'observer' from
 * 'observer-pattern', object which can be subscribed to 'subject' and informed
 * about it's internal state changes. Observer of type
 * <tt>IntegerStorageObserver</tt> defines observer which is subscribed to more
 * specific subject, one that stores <tt>Integer</tt> value.
 * 
 * @author Ante Spajic
 *
 */
public interface IntegerStorageObserver {

	/**
	 * Method which subject calls over it's observer's references to inform them
	 * automatically of any state changes.
	 * 
	 * @param istorage
	 *            reference to subject
	 */
	public void valueChanged(IntegerStorageChange istorage);
}
