package hr.fer.zemris.java.tecaj.hw6.observer2;

public class IntegerStorageChange {

	private IntegerStorage istorage;
	private int value;
	private int newValue;
	
	public IntegerStorageChange(IntegerStorage istorage, int value,
			int newValue) {
		super();
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
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @return the newValue
	 */
	public int getNewValue() {
		return newValue;
	}
	
	
		
}
