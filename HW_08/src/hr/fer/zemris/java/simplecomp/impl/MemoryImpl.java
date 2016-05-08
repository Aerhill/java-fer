package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * Implementacija sučelja {@link Memory} koje predstavlja memoriju našeg
 * raćunalnog sustava.
 * 
 * @author Ante Spajic
 *
 */
public class MemoryImpl implements Memory {

	/**
	 * Polje objekata koje predstavlja memoriju računala.
	 */
	private Object[] memory;

	/**
	 * Javni konstruktor koji prima veličinu memorije koju je potrebno stvoriti.
	 * 
	 * @param size
	 *            veličina memorije
	 */
	public MemoryImpl(int size) {
		this.memory = new Object[size];
	}

	@Override
	public void setLocation(int location, Object value) {
		if (location < 0 || location >= memory.length) {
			throw new IndexOutOfBoundsException("Invalid memory location");
		}
		memory[location] = value;
	}

	@Override
	public Object getLocation(int location) {
		if (location < 0 || location >= memory.length) {
			throw new IndexOutOfBoundsException("Invalid memory location");
		}
		return memory[location];
	}

}
