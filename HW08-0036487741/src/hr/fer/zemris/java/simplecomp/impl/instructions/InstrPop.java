package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Instrukcija koja predstavlja asemblersku operaciju <code>pop</code> koja s
 * vrha stoga skida podatak koji pohranjuje u registar. Kazaljku vrha stoga
 * uvećava za 1.
 * 
 * <pre>
 * pop rx
 * </pre>
 * 
 * Efekt izvođenja ove operacije je :
 * 
 * <pre>
 * rx <- [r15+1]
 * r15 <- r15 + 1
 * </pre>
 * 
 * @author Ante Spajic
 *
 */
public class InstrPop implements Instruction {

	/**
	 * index registra u koji spremamo podatak koji smo skinuli sa vrha stoga
	 */
	private int indexRegistra;

	/**
	 * Javni konstruktor koji prima listu argumenata čija je valjana veličina 1.
	 * Jedini element koji sadrži je registar u koji želimo spremiti sadržaj sa
	 * vrha stoga.
	 * 
	 * @param arguments
	 *            lista sa indexom registra u koji želimo spremiti
	 */
	public InstrPop(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("wowowow dear user!");
		}
		this.indexRegistra = (Integer) arguments.get(0).getValue();
		if (RegisterUtil.isIndirect(indexRegistra)) {
			throw new IllegalArgumentException("Cannot use indirect addressing");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		int top = (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, top + 1);
		top = (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
		computer.getRegisters().setRegisterValue(indexRegistra, computer.getMemory().getLocation(top));
		return false;
	}

}
