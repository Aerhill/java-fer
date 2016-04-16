package hr.fer.zemris.java.tecaj.hw6.observer1;

public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.println("Square value: " + value * value);
	}

}
