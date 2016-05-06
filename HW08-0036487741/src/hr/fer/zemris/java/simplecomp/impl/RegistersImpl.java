package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementacija sučelja {@link Registers} koje predstavlja registre u našem
 * računalnom sustavu. Više dokumentacije u samom sučelju.
 * 
 * @author Ante Spajic
 *
 */
public class RegistersImpl implements Registers {

	/**
	 * Polje registara koje ovaj sustav sadrži
	 */
	private Object[] registers;
	/**
	 * Pokazivač na trenutnu operaciju u programu
	 */
	private int programCounter;
	/**
	 * Registar zastavice
	 */
	private boolean flag;

	/**
	 * Javni konstruktor koji prima broj registara u računalu
	 * 
	 * @param regsLen
	 *            broj registara koje želimo stvoriti
	 */
	public RegistersImpl(int regsLen) {
		this.registers = new Object[regsLen];
	}

	@Override
	public Object getRegisterValue(int index) {
		if (index < 0 || index >= registers.length) {
			throw new IndexOutOfBoundsException("Invalid register");
		}
		return registers[index];
	}

	@Override
	public void setRegisterValue(int index, Object value) {
		if (index < 0 || index >= registers.length) {
			throw new IndexOutOfBoundsException("Invalid register");
		}
		registers[index] = value;
	}

	@Override
	public int getProgramCounter() {
		return programCounter;
	}

	@Override
	public void setProgramCounter(int value) {
		this.programCounter = value;
	}

	@Override
	public void incrementProgramCounter() {
		programCounter++;
	}

	@Override
	public boolean getFlag() {
		return flag;
	}

	@Override
	public void setFlag(boolean value) {
		this.flag = value;
	}

}
