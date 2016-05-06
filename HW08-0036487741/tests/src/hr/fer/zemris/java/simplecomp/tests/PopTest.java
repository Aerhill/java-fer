package hr.fer.zemris.java.simplecomp.tests;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPop;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@RunWith(MockitoJUnitRunner.class)
public class PopTest {

	@Mock private Computer computer;
	@Mock private Registers registers;
	@Mock private Memory memory;
	@Mock private List<InstructionArgument> list;
	@Mock private InstructionArgument argument;

	@Test(expected = IllegalArgumentException.class)
	public void testWrongNoOfArguments1() {
		when(list.size()).thenReturn(0);
		new InstrPop(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongNoOfArguments2() {
		when(list.size()).thenReturn(2);
		new InstrPop(list);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testArgumentNotRegister() {
		when(list.size()).thenReturn(1);
		when(list.get(0)).thenReturn(argument);
		when(argument.isRegister()).thenReturn(false);
		new InstrPop(list);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterWithIndirectAddressing() {
		when(list.size()).thenReturn(1);
		when(list.get(0)).thenReturn(argument);
		when(list.get(0).isRegister()).thenReturn(true);
		when(list.get(0).getValue()).thenReturn(0x01000000);
		new InstrPop(list);
	}
	
	@Test
	public void testAllValidInputs() {
		when(list.size()).thenReturn(1);
		when(list.get(0)).thenReturn(argument);
		when(argument.isRegister()).thenReturn(true);
		when(argument.getValue()).thenReturn(5);
		new InstrPop(list);
	}
	
	@Test
	public void testExecution() {
		// WE WILL SIMULATE NEXT OPERATION:
		// Generic object (in this case number 100) is currently on stack.
		// Stack pointer points to address 299.
		// Stack pointer should be changed after operation to address 300.
		// Value that was on stack should be stored in register with index 5.
		
		// Prepare list of arguments.
		when(list.size()).thenReturn(1);
		when(list.get(0)).thenReturn(argument);
		when(argument.isRegister()).thenReturn(true);
		when(argument.getValue()).thenReturn(5);

		// Create instruction.
		Instruction pop = new InstrPop(list);

		// Mocking memory and register components.
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		
		// Mocking stack memory address.
		when(memory.getLocation(300)).thenReturn(100);
		
		// Mocking appropriate registers.
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(299,300);
		doNothing().when(registers).setRegisterValue(anyInt(),anyObject());
		
		pop.execute(computer);
		
		// Checking minimal performance.
		verify(registers, times(0)).getRegisterValue(5);
		verify(registers, times(1)).setRegisterValue(5, 100);
		verify(registers, atMost(2)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(registers, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX, 300);
		
		// Stack exchange should be performed:
		// 1.) increment stack pointer
		// 2.) take object from stack
		// Stack exchange should not be performed the other way around.
		// Stack should not be tempered with in any other way.
		verify(memory, times(0)).setLocation(299, Object.class);
		verify(memory, times(0)).setLocation(300, Object.class);
		verify(memory, times(0)).getLocation(299);
		verify(memory, times(1)).getLocation(300);
	}
}