package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Instrukcija koja predstavlja asemblersku operaciju <code>call</code> koja
 * predstavlja poziv potprograma. Trenutni sadržaj registra PC (program counter)
 * pohranjuje na stog; potom u taj registar upisuje predanu adresu čime definira
 * sljedeću instrukciju koja će biti izvedena. Valjan poziv ove naredbe:
 * 
 * <pre>
 * call adresa
 * </pre>
 * 
 * @author Ante Spajic
 *
 */
public class InstrCall implements Instruction {

	/**
	 * lokacija na kojoj se nalazi potprogram
	 */
	private int location;

	/**
	 * Javni konstruktor koji prima listu sa samo jednim argumentom a to je
	 * lokacija potprograma koji želimo izvesti.
	 * 
	 * @param arguments
	 *            lista sa samo jednim argumentom koji je lokacija potprograma
	 */
	public InstrCall(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch, invalid memory location");
		}
		this.location = (Integer) arguments.get(0).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		int pc = computer.getRegisters().getProgramCounter();
		int top = (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);

		computer.getMemory().setLocation(top, pc);
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, top - 1);

		computer.getRegisters().setProgramCounter(location);

		return false;
	}

}
