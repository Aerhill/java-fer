package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja označava asemblersku operaciju <code>move</code> koja
 * stavlja određeni sadržaj u predani registar.
 * 
 * <pre>
 * move rx,ry => rx <- ry
 * </pre>
 * 
 * <pre>
 * move rx, broj => rx <- broj
 * </pre>
 * 
 * ; npr: move r7, 24 Prvi argument može biti registar ili indirektna adresa;
 * drugi argument može biti registar, indirektna adresa ili broj.
 * 
 * @author Ante Spajic
 *
 */
public class InstrMove implements Instruction {

	/**
	 * index registra u koji spremamo sadržaj
	 */
	private int indexRegistra;
	/**
	 * Radi li se o indirektnom adresiranju kod prvog argumenta
	 */
	private boolean isIndirect;
	/**
	 * Odmak ukoliko se radi o indirektnom adresiranju
	 */
	private int offset;
	/**
	 * drugi argument operacije
	 */
	private InstructionArgument secondArgument;

	/**
	 * Javni konstruktor koji stvara operaciju koja prima listu sa dva
	 * argumenta. Od kojih je prvi registar u koji spremamo a drugi je registar
	 * ili broj koji želimo spremiti.
	 * 
	 * @param arguments
	 *            lista sa dva argumenta od kojih prvi mora biti registar a
	 *            drugi registar ili broj
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Move instruction expects 2 arguments.");
		} else if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Move instruction. Type mismatch for first argument.");
		} else if (!arguments.get(1).isRegister() && !arguments.get(1).isNumber()) {
			throw new IllegalArgumentException("Move instruction. Type mismatch for second argument.");
		}
		Integer firstRegister = (Integer) arguments.get(0).getValue();
		indexRegistra = RegisterUtil.getRegisterIndex(firstRegister);
		if (RegisterUtil.isIndirect(firstRegister)) {
			isIndirect = true;
			offset = RegisterUtil.getRegisterOffset(firstRegister);
		}
		secondArgument = arguments.get(1);
	}

	@Override
	public boolean execute(Computer computer) {

		// Second value is register.
		if (secondArgument.isRegister()) {

			int registerIndex2 = RegisterUtil.getRegisterIndex((Integer) secondArgument.getValue());
			boolean isIndirect2 = false;
			int offset2 = 0;
			if (RegisterUtil.isIndirect((Integer) secondArgument.getValue())) {
				isIndirect2 = true;
				offset2 = RegisterUtil.getRegisterOffset((Integer) secondArgument.getValue());
			}

			if (isIndirect && isIndirect2) {
				computer.getMemory().setLocation(
						(Integer) computer.getRegisters().getRegisterValue(indexRegistra) + offset,
						computer.getMemory().getLocation(
								(Integer) computer.getRegisters().getRegisterValue(registerIndex2) + offset2));
			} else if (!isIndirect && isIndirect2) {
				computer.getRegisters().setRegisterValue(indexRegistra, computer.getMemory()
						.getLocation((Integer) computer.getRegisters().getRegisterValue(registerIndex2) + offset2));
			} else if (isIndirect && !isIndirect2) {
				computer.getMemory().setLocation(
						(Integer) computer.getRegisters().getRegisterValue(indexRegistra) + offset,
						computer.getRegisters().getRegisterValue(registerIndex2));
			} else if (!isIndirect && !isIndirect2) {
				computer.getRegisters().setRegisterValue(indexRegistra,
						computer.getRegisters().getRegisterValue(registerIndex2));
			}
		}

		// Second value is number.
		else {
			Object operand = secondArgument.getValue();
			if (isIndirect) {
				computer.getMemory().setLocation(
						(Integer) computer.getRegisters().getRegisterValue(indexRegistra) + offset, operand);
			} else {
				computer.getRegisters().setRegisterValue(indexRegistra, operand);
			}
		}
		return false;
	}
}
