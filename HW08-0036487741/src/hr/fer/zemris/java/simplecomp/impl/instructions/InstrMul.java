package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja implementira {@link Instruction} sučelje koje prestavlja
 * asemblersku operaciju <code>mul</code> koja množi sadržaj pohranjen u dva
 * registra i sprema ga u prvi navedeni registar.
 * 
 * <pre>
 *  mul r1, r2, r3 -> r1 = r2*r3
 * </pre>
 * 
 * @author Ante Spajic
 *
 */
public class InstrMul implements Instruction {

	/**
	 * indeks registra u koji spremamo produkt množenja
	 */
	private int indexRegistra1;
	/**
	 * indeks prvog registra operanda operacije množenja
	 */
	private int indexRegistra2;
	/**
	 * indeks drugog registra operanda operacije množenja
	 */
	private int indexRegistra3;

	/**
	 * Javni konstruktor koji stvara novu instrukciju koja predstavlja operaciju
	 * množenja, operacija prima listu argumenata od kojih su dva registra čije
	 * se pohranjene vrijednosti množe i spremaju u prvi argument liste.
	 * 
	 * @param arguments
	 *            lista argumenata koji sadrži redom, registar za pohranu
	 *            rezultata i dva argumenta operacije množenja
	 */
	public InstrMul(List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		for (int i = 0; i < 3; i++) {
			if (!arguments.get(i).isRegister() || RegisterUtil.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException("Type mismatch for argument " + i + "!");
			}
		}
		this.indexRegistra1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.indexRegistra2 = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
		this.indexRegistra3 = RegisterUtil.getRegisterIndex((Integer) arguments.get(2).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(indexRegistra2);
		Object value2 = computer.getRegisters().getRegisterValue(indexRegistra3);
		computer.getRegisters().setRegisterValue(indexRegistra1, Integer.valueOf((Integer) value1 * (Integer) value2));
		return false;
	}
}