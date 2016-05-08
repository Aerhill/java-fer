package hr.fer.zemris.java.simplecomp.tests;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrRet;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@RunWith(MockitoJUnitRunner.class)
public class RetTest {

	@Mock private Computer computer;
	@Mock private Registers registers;
	@Mock private Memory memory;
	@Mock private List<InstructionArgument> list;
	@Mock private InstructionArgument argument;
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArguments() {
		when(list.size()).thenReturn(1);
		new InstrRet(list);
	}
	
	@Test
	public void testExecution() {
		// WE WILL SIMULATE NEXT OPERATION:
		// PC will be 100, we will act as if we are in program.
		// We will return to the address 50.
		// Address of return will be stored on stack,
		// at the 300 address.
		
		when(list.size()).thenReturn(0);
		
		// Create instruction.
		Instruction ret = new InstrRet(list);
		
		// Mocking memory and register components.
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		
		// Mocking PC behavior.
		when(registers.getProgramCounter()).thenReturn(100);
		doNothing().when(registers).setProgramCounter(anyInt());
		
		// Mocking all registers, only one with stack pointer should be used.
		when(registers.getRegisterValue(anyInt())).thenReturn(299,300);
		doNothing().when(registers).setRegisterValue(anyInt(), anyObject());
		
		// Mocking memory behavior.
		when(memory.getLocation(anyInt())).thenReturn(50);
		
		ret.execute(computer);
		
		// Checking minimal performance.
		verify(registers, times(0)).getProgramCounter();
		verify(registers, times(1)).setProgramCounter(50);
		verify(registers, atLeast(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(registers, atLeast(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX, 300);
		
		// Stack exchange should be performed:
		// 1.) increment stack pointer
		// 2.) take address from stack
		// Stack exchange should not be performed the other way around.
		verify(memory, times(0)).getLocation(299);
		verify(memory, times(1)).getLocation(300);
	}
}