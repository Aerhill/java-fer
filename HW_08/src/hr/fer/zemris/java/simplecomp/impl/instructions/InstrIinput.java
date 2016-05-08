package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja prestavlja asemblersku operaciju <code>iinput</code> koja
 * čita redak s tipkovnice. Sadržaj tumači kao Integer i njega zapisuje na
 * zadanu memorijsku lokaciju. Dodatno postavlja zastavicu flag na true ako je
 * sve u redu, odnosno na false ako konverzija nije uspjela ili je drugi problem
 * s čitanjem.
 * 
 * <pre>
 * [lokacija] <- pročitani Integer
 * </pre>
 * 
 * @author Ante Spajic
 *
 */
public class InstrIinput implements Instruction {

	/**
	 * lokacija na koju spremamo predani broj
	 */
	public int location;
	/**
	 * primjerak razreda Scanner koji koristimo za učitavanje broja sa
	 * standardnog ulaza
	 */
	private static Scanner sc = new Scanner(System.in);

	/**
	 * Javni konstruktor koji prima listu čija je valjana veličina 1 čiji je
	 * jedini argument lokacija na koju želimo spremiti broj koji korisnik
	 * upiše.
	 * 
	 * @param arguments
	 *            lista sa 1 argumentom koji je memorijska lokacija
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch expected number for location");
		}
		this.location = (Integer) arguments.get(0).getValue();

	}

	@Override
	public boolean execute(Computer computer) {
		try {
			String line = sc.nextLine();
			int value = Integer.parseInt(line);
			computer.getRegisters().setFlag(true);
			computer.getMemory().setLocation(location, value);
		} catch (Exception e) {
			computer.getRegisters().setFlag(false);
		}
		return false;
	}

}
