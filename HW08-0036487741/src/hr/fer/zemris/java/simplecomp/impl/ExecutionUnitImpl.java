package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;

/**
 * <p>
 * Upravljački sklop računala. Ovaj razred "izvodi" program odnosno predstavlja
 * impulse takta za sam procesor.
 * </p>
 * 
 * @author Ante Spajic
 *
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	@Override
	public boolean go(Computer computer) {
		computer.getRegisters().setProgramCounter(0);
		while (true) {
			int pc = computer.getRegisters().getProgramCounter();
			Instruction instr = (Instruction) computer.getMemory().getLocation(pc);
			computer.getRegisters().incrementProgramCounter();
			if (instr.execute(computer)) {
				break;
			}
		}
		return true;
	}

}
