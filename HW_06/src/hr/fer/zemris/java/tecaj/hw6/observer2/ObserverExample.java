package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Demonstration program for this part of assignment. Copied from assignment.
 * 
 * @author Ante Spajic
 *
 */
public class ObserverExample {

	/**
	 * Entry point to a program
	 * 
	 * @param args
	 *            Unused command line arguments.
	 */
	public static void main(String[] args) {

		IntegerStorage istorage = new IntegerStorage(20);
		istorage.addObserver(new SquareValue());
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(2));

		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
