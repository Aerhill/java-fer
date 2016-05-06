package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija računala koja ispisuje sadržaj predanog registra na standardni
 * izlaz. Predani registar može sadržavati indirektnu adresu memorijske lokacije
 * s koje želimo ispisati sadržaj ili može biti direktno pristupljen.
 * 
 * @author Ante Spajic
 *
 */
public class InstrEcho implements Instruction {

	/**
	 * index registra čiji sadržaj želimo ispisati
	 */
	private int indexRegistra;
	/**
	 * zastavica koja označava jeli predani registar u indirektnom obliku
	 */
	private boolean isIndirect;
	/**
	 * odmak memorijske adrese ako je u indirektnom obliku zadan
	 */
	private int offset;

	/**
	 * Javni konstruktor koji prima listu argumenata čija je prihvaljiva
	 * veličina samo kada sadrži jedan element a to je registar čiji sadržaj
	 * želimo ispisati.
	 * 
	 * @param arguments
	 *            lista sa registrom čiji sadržaj želimo ispisati
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch, argument must be a register!");
		}
		int descriptor = (Integer) arguments.get(0).getValue();
		this.indexRegistra = RegisterUtil.getRegisterIndex(descriptor);
		this.isIndirect = RegisterUtil.isIndirect(descriptor);
		this.offset = RegisterUtil.getRegisterOffset(descriptor);
	}

	@Override
	public boolean execute(Computer computer) {
		Object value;
		if (isIndirect) {
			int memloc = (Integer) computer.getRegisters().getRegisterValue(indexRegistra) + offset;
			value = computer.getMemory().getLocation(memloc);
		} else {
			value = computer.getRegisters().getRegisterValue(indexRegistra);
		}
		System.out.print(value.toString());
		return false;
	}

}
