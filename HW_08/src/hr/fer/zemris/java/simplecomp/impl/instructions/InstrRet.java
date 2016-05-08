package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Instrukcija našeg računalnog sustava koja predstavlja asemblersku operaciju
 * <code>ret</code> koja služi za vraćanja iz potprograma. Ono što ova operacija
 * radi je uzima adresu sa vrha stoga koja je predana kada je potprogram pozvan
 * instrukcijom <code>call</code> i nju stavlja u PC. Valjan poziv ove
 * instrukcije:
 * 
 * <pre>
 * ret
 * </pre>
 * 
 * @author Ante Spajic
 *
 */
public class InstrRet implements Instruction {

	/**
	 * Javni konstruktor koji prima praznu listu argumenata.
	 * 
	 * @param arguments
	 *            prazna lista argumenata
	 */
	public InstrRet(List<InstructionArgument> arguments) {
		if (arguments.size() != 0) {
			throw new IllegalArgumentException("Expected no arguments");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		int top = (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, top + 1);
		top = (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
		computer.getRegisters().setProgramCounter((Integer) computer.getMemory().getLocation(top));

		return false;
	}

}
