package hr.fer.zemris.java.tecaj.p3.c2;

public class Storage {

	private double value;
	
	public Storage(double value) {
		this.value = value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public ValueProvider getValueProvider() {
		return new ValueProviderImpl();
	}
	
	private class ValueProviderImpl implements ValueProvider {
		
		@Override
		public double getValue() {
			return value;
		}

		@Override
		public double getSquared() {
			return value*value;
		}

		@Override
		public double getSquareRoot() {
			return Math.sqrt(value);
		}
		
	}
}
