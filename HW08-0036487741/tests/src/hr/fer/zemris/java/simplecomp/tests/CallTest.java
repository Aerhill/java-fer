package hr.fer.zemris.java.simplecomp.tests;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrCall;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@RunWith(MockitoJUnitRunner.class)
public class CallTest {

	@Mock private Computer computer;
	@Mock private Registers registers;
	@Mock private Memory memory;
	@Mock private List<InstructionArgument> list;
	@Mock private InstructionArgument argument;

	@Test(expected = IllegalArgumentException.class)
	public void testWrongNoOfArguments1() {
		when(list.size()).thenReturn(0);
		new InstrCall(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongNoOfArguments2() {
		when(list.size()).thenReturn(2);
		new InstrCall(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testArgumentNotNumber() {
		when(list.size()).thenReturn(1);
		when(list.get(0)).thenReturn(argument);
		when(argument.isNumber()).thenReturn(false);
		new InstrCall(list);
	}

	@Test
	public void testAllValidInputs() {
		when(list.size()).thenReturn(1);
		when(list.get(0)).thenReturn(argument);
		when(argument.isNumber()).thenReturn(true);
		when(argument.getValue()).thenReturn(500);
		new InstrCall(list);
	}

	@Test
	public void testExecution() {
		// WE WILL SIMULATE NEXT OPERATION:
		// PC will be 100, we want to jump at program at the address 200.
		// Stack pointer points to address 300.
		// Stack pointer should be changed after operation to 299.

		// Prepare list of arguments.
		when(list.size()).thenReturn(1);
		when(list.get(0)).thenReturn(argument);
		when(argument.isNumber()).thenReturn(true);
		when(argument.getValue()).thenReturn(200);

		// Create instruction.
		Instruction call = new InstrCall(list);

		// Mocking memory and register components.
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);

		// Mocking PC behavior.
		when(registers.getProgramCounter()).thenReturn(100);
		doNothing().when(registers).setProgramCounter(anyInt());

		// Mocking all registers, only one with stack pointer should be used.
		when(registers.getRegisterValue(anyInt())).thenReturn(300);
		doNothing().when(registers).setRegisterValue(anyInt(), anyObject());
		
		// Mocking memory behavior.
		doNothing().when(memory).setLocation(anyInt(), anyObject());

		call.execute(computer);

		// Checking minimal performance.
		verify(registers, times(1)).getProgramCounter();
		verify(registers, times(1)).setProgramCounter(200);
		verify(registers, atLeast(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(registers, atLeast(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX, 299);

		// Stack exchange should be performed:
		// 1.) put address on stack
		// 2.) decrement stack pointer
		// Stack exchange should not be performed the other way around.
		verify(memory, times(0)).setLocation(299, 100);
		verify(memory, times(1)).setLocation(300, 100);
	}
}