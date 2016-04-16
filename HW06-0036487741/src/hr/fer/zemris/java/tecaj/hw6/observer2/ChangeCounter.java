package hr.fer.zemris.java.tecaj.hw6.observer2;

public class ChangeCounter implements IntegerStorageObserver {

	private int counter = 0;
	
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Number of value changes since tracking: " + ++counter);
	}

}
