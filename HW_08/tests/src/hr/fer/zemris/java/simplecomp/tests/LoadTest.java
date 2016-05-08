package hr.fer.zemris.java.simplecomp.tests;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrLoad;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@RunWith(MockitoJUnitRunner.class)
public class LoadTest {

	@Mock private Computer computer;
	@Mock private Registers registers;
	@Mock private Memory memory;
	@Mock private List<InstructionArgument> list;
	@Mock private InstructionArgument argument;
	
	@Mock private InstructionArgument first;
	@Mock private InstructionArgument second;

	@Test(expected = IllegalArgumentException.class)
	public void testWrongNoOfArguments1() {
		when(list.size()).thenReturn(1);
		new InstrLoad(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongNoOfArguments2() {
		when(list.size()).thenReturn(3);
		new InstrLoad(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFirstArgumentNotRegister() {
		when(list.size()).thenReturn(2);
		when(argument.isRegister()).thenReturn(false);
		when(list.get(0)).thenReturn(argument);
		new InstrLoad(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegisterWithIndirectAddressing() {
		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(argument);
		when(list.get(0).isRegister()).thenReturn(true);
		when(list.get(0).getValue()).thenReturn(0x01000000);
		when(list.get(1)).thenReturn(second);
		when(list.get(1).isNumber()).thenReturn(true);
		when(list.get(1).getValue()).thenReturn(231321);
		new InstrLoad(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSecondArgumentNotNumber() {
		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(argument);
		when(list.get(0).isRegister()).thenReturn(true);
		when(list.get(0).getValue()).thenReturn(0x00000000);
		when(list.get(1)).thenReturn(argument);
		when(list.get(1).isNumber()).thenReturn(false);
		new InstrLoad(list);
	}

	@Test
	public void testAllValidInputs() {
		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(first);
		when(list.get(0).isRegister()).thenReturn(true);
		when(list.get(1)).thenReturn(second);
		when(list.get(1).isNumber()).thenReturn(true);

		when(first.getValue()).thenReturn(5);
		when(second.getValue()).thenReturn(200);

		new InstrLoad(list);
	}

	@Test
	public void testExecution() {
		// WE WILL SIMULATE NEXT OPERATION:
		// We will try to move object from address 300
		// to 5th register.
		// Moved object will be PI.

		// Prepare list of arguments.
		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(first);
		when(list.get(1)).thenReturn(second);
		when(first.isRegister()).thenReturn(true);
		when(second.isNumber()).thenReturn(true);
		when(first.getValue()).thenReturn(5);
		when(second.getValue()).thenReturn(300);

		// Create instruction.
		Instruction load = new InstrLoad(list);

		// Mocking memory and register components.
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);

		when(memory.getLocation(anyInt())).thenReturn(new Double(3.14));
		doNothing().when(registers).setRegisterValue(anyInt(), anyObject());

		load.execute(computer);

		// Checking minimal performance.
		verify(memory, times(1)).getLocation(300);
		verify(registers, times(1)).setRegisterValue(5, 3.14);
	}
}