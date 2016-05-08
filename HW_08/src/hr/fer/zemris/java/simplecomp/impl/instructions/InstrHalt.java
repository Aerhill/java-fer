package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Intrukcija koja implementira sučelje {@link Instruction} i ekvivalent je
 * instrukciji <code>halt</code> u asemblerskom kodu koja zaustavlja rad
 * procesora.
 * 
 * @author Ante Spajic
 *
 */
public class InstrHalt implements Instruction {

	/**
	 * Javni konstruktor koji stvara intrukciju halt koja zaustavlja rad
	 * računala program.
	 * 
	 * @param arguments
	 *            ova metoda prima praznu listu argumenata
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		if (arguments.size() != 0) {
			throw new IllegalArgumentException("Expected no arguments wow");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		return true;
	}

}
