package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja implementira sučelje {@link Instruction} koja služi kao
 * asemblerska operacija load koja učitava sadržaj sa memorijske lokacije u
 * predani registar.
 * 
 * <pre>
 * Rx <- lokacija
 * </pre>
 * 
 * @author Ante Spajic
 *
 */
public class InstrLoad implements Instruction {

	/**
	 * Index registra u koji učitavamo
	 */
	private int indexRegistra;
	/**
	 * Memorijska lokacija s koje učitavamo
	 */
	private int memorijskaLokacija;

	/**
	 * Javni konstruktor koji prima listu argumenata. Prihvatljiva lista je
	 * duljine 2 a u svim ostalim slučajevima se baca
	 * {@link IllegalArgumentException}. Prvi od dva argumenta je registar u
	 * koji se sprema sadržaj drugog argumenta koji predstavlja memorijsku
	 * lokaciju.
	 * 
	 * @param arguments
	 *            lista argumenata koje ova instrukcija prima, prihvatljiv broj
	 *            argumenata je 2
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1, it must be a register!");
		}
		if (!arguments.get(1).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 2, it must be a number!");
		}
		this.indexRegistra = (Integer) arguments.get(0).getValue();
		if (RegisterUtil.isIndirect(indexRegistra)) {
			throw new IllegalArgumentException("This operation does not support indirect addressing");
		}
		this.memorijskaLokacija = (Integer) arguments.get(1).getValue();

	}

	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(indexRegistra, computer.getMemory().getLocation(memorijskaLokacija));
		return false;
	}

}
