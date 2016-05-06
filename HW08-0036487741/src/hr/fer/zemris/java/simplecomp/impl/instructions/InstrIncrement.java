package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja implementira {@link Instruction} sučelje koje prestavlja
 * asemblersku operaciju <code>decrement</code> koja inkrementira sadržaj pohranjen u dva
 * registra i sprema ga u natrag u registar. Navedena operacija ne podržava
 * indirektno adresiranje.
 * 
 * <pre>
 *  increment r1 <- r1+1
 * </pre>
 * 
 * @author Ante Spajic
 *
 */
public class InstrIncrement implements Instruction {

	/**
	 * index registra kojemu želimo uvećati vrijednost
	 */
	private int indexRegistra;

	/**
	 * Javni konstruktor koji prima listu sa samo jednim argumentom koji index
	 * registra u računalu čiji sadržaj želimo uvećati za 1.
	 * 
	 * @param arguments
	 *            lista sa jednim elementom koji je index registra
	 */
	public InstrIncrement(List<InstructionArgument> arguments) {
		if(arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch, argument must be a register");
		}
		this.indexRegistra = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
	}
	
	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getRegisters().getRegisterValue(indexRegistra);
		computer.getRegisters().setRegisterValue(indexRegistra, Integer.valueOf((Integer) value + 1));
		return false;
	}

}
