package hr.fer.zemris.java.tecaj.hw6.observer2;

public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.println("Provided new value: "+ value + ", square is: " + value * value);
	}

}
