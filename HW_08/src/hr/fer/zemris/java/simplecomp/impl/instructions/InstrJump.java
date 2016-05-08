package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja označava asemblersku operaciju <code>jump</code> koja
 * bezuvjetno skače na naredbu sa zadanom adresom. Adresa je zadana običnim
 * brojem kao kod memorijskih naredaba.
 * 
 * <pre>
 * jump lokacija => pc <- lokacija
 * </pre>
 * 
 * @author Ante Spajic
 *
 */
public class InstrJump implements Instruction {

	/**
	 * lokacija na koju želimo "skočiti"
	 */
	private int location;

	/**
	 * Javni konstruktor instrukcije koja prima listu čija je valjana veličina 1
	 * čiji element je memorijska lokacija na koju želimo skočiti.
	 * 
	 * @param arguments
	 *            lista koja sadrži memorijsku lokaciju na koju želimo skočiti
	 */
	public InstrJump(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Invalid memory location, must be a number");
		}
		this.location = (Integer) arguments.get(0).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(location);
		return false;
	}

}
