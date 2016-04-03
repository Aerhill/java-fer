package hr.fer.zemris.java.tecaj.p3.c;

public class Storage {

	private double value;
	
	public Storage(double value) {
		this.value = value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public ValueProvider getValueProvider(){
		return new ValueProviderImpl(this);
	}
	
	private static class ValueProviderImpl implements ValueProvider {

		private Storage myStorage;
		
		public ValueProviderImpl(Storage storage) {
			myStorage = storage;
		}
		
		@Override
		public double getValue() {
			return myStorage.value;
		}

		@Override
		public double getSquared() {
			return Math.pow(myStorage.value, 2);
		}

		@Override
		public double getSquareRoot() {
			return Math.pow(myStorage.value, 1./2);
		}
		
	}
}
