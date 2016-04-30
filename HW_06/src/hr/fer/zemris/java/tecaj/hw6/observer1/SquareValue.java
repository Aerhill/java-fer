package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Implementation of observer from 'observer pattern' that displays square value
 * of subject's current storage value on standard output.
 * 
 * @author Ante Spajic
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.println("Provided new value: " + value + ", square is: " + value * value);
	}

}
