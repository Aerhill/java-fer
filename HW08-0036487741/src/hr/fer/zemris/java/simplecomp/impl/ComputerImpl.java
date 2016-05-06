package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementacija sučelja {@link Computer} koje predstavlja podatkovni dio našeg
 * računalnog sustava.
 * 
 * @author Ante Spajic
 *
 */
public class ComputerImpl implements Computer {

	/**
	 * Memorija računala
	 */
	private Memory memory;
	/**
	 * Registri računala
	 */
	private Registers registers;

	/**
	 * Javni konstruktor koji stvara računalo sa određenom veličinom memorije i
	 * određenim brojem registara.
	 * 
	 * @param memorySize
	 *            veličina memorije, broj objekata koje može spremiti
	 * @param registersLength
	 *            broj registara koje naše računalo ima
	 */
	public ComputerImpl(int memorySize, int registersLength) {
		this.memory = new MemoryImpl(memorySize);
		this.registers = new RegistersImpl(registersLength);
	}

	@Override
	public Registers getRegisters() {
		return registers;
	}

	@Override
	public Memory getMemory() {
		return memory;
	}

}
