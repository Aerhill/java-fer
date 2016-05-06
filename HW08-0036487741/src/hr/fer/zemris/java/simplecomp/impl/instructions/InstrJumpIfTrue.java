package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja označava asemblersku operaciju <code>jumpIfTrue</code> koja
 * izvodi uvjetni skok na osnovu registra zastavice unutar registara našeg
 * računalnog sustava. Instrukcija se izvodi na dva moguća načina:
 * 
 * <ul>
 * <li>Ako je uvjet ispunjen => skok se ostvaruje</li>
 * <li>Ako uvjet nije ispunjen => skok se ne ostvaruje, tj. izvodi se sljedeća
 * naredba (ona koja je "ispod" naredbe skoka)</li>
 * </ul>
 * 
 * <pre>
 * jumpIfTrue lokacija => ako je flag=1 tada pc <- lokacija
 * </pre>
 * 
 * @author Ante Spajic
 *
 */
public class InstrJumpIfTrue implements Instruction {

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
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
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
		if (computer.getRegisters().getFlag()) {
			computer.getRegisters().setProgramCounter(location);
		}
		return false;
	}

}
