package hr.fer.zemris.java.tecaj.hw6.observer2;

public class DoubleValue implements IntegerStorageObserver {

	private int numberOfUsagesLeft;
	
	public DoubleValue(int n) {
		this.numberOfUsagesLeft = n;
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		if( numberOfUsagesLeft == 0) {
			istorage.removeObserver(this);
			return;
		}
		System.out.println("Double value: " + 2*istorage.getValue());
		numberOfUsagesLeft--;
	}

}
