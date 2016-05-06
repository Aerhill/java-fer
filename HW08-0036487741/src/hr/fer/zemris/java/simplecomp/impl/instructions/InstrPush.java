package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Instrukcija koja implementira sučelje {@link Instruction} koja služi kao
 * asemblerska operacija <code>push</code> koja stavlja sadržaj predanog
 * registra na vrh stoga.
 * 
 * <pre>
 * push rx
 * </pre>
 * 
 * Evekt izvođenja ove operacije je:
 * 
 * <pre>
 * [r15] <- rx
 * 	r15 <- r15 - 1
 * </pre>
 * 
 * @author Ante Spajic
 *
 */
public class InstrPush implements Instruction {

	/**
	 * Index registra koji čiji sadržaj stavljamo na stoz
	 */
	private int indexRegistra;

	/**
	 * Javni konstruktor koji prima listu argumenata čija je valjana duljina 1,
	 * a taj jedan argument je registar čiji sadržaj želimo spremiti na stog.
	 * 
	 * @param arguments
	 *            registar čiji sadržaj želimo spremiti na vrh stoga
	 */
	public InstrPush(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("wowowow dear user!");
		}
		this.indexRegistra = (Integer) arguments.get(0).getValue();
		if (RegisterUtil.isIndirect(indexRegistra)) {
			throw new IllegalArgumentException("This does not support indirect addressing");
		}

	}

	@Override
	public boolean execute(Computer computer) {
		int top = (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
		computer.getMemory().setLocation(top, computer.getRegisters().getRegisterValue(indexRegistra));
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, top - 1);
		return false;
	}
}
