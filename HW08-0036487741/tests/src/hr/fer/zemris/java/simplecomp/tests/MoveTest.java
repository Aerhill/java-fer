package hr.fer.zemris.java.simplecomp.tests;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrLoad;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrMove;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@RunWith(MockitoJUnitRunner.class)
public class MoveTest {
	
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
		new InstrMove(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongNoOfArguments2() {
		when(list.size()).thenReturn(3);
		new InstrMove(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFirstArgumentNotRegister() {
		when(list.size()).thenReturn(2);
		when(argument.isRegister()).thenReturn(false);
		when(list.get(0)).thenReturn(argument);
		new InstrMove(list);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSecondArgumentNotRegisterOrNumber() {
		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(argument);
		when(list.get(0).isRegister()).thenReturn(true);
		when(list.get(0).getValue()).thenReturn(0x00000000);
		when(list.get(1)).thenReturn(argument);
		when(list.get(1).isRegister()).thenReturn(false);
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
	
	// First argument is register without offset,
	// second argument is number.
	@Test
	public void testExecution1() {
		
		// Prepare list of arguments.
		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(first);
		when(list.get(1)).thenReturn(second);
		when(first.isRegister()).thenReturn(true);
		when(second.isNumber()).thenReturn(true);
		
		when(first.getValue()).thenReturn(1);
		when(second.getValue()).thenReturn(3.14);
		
		// Create instruction.
		Instruction move = new InstrMove(list);

		// Mocking register components.
		when(computer.getRegisters()).thenReturn(registers);
		
		move.execute(computer);
		
		// Evaluation.
		verify(registers, times(0)).getRegisterValue(anyInt());
		verify(registers, times(1)).setRegisterValue(1,3.14);
	}
	
	// First argument is register with offset,
	// second argument is number.
	@Test
	public void testExecution2() {
		
		// Prepare list of arguments.
		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(first);
		when(list.get(1)).thenReturn(second);
		when(first.isRegister()).thenReturn(true);
		when(second.isNumber()).thenReturn(true);
		
		// Address is in the first register.
		when(first.getValue()).thenReturn(0x01000001);
		when(second.getValue()).thenReturn(3.14);
		
		// Create instruction.
		Instruction move = new InstrMove(list);

		// Mocking memory and register components.
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		
		// Address in memory will be 300.
		when(registers.getRegisterValue(1)).thenReturn(300);
		doNothing().when(memory).setLocation(anyInt(),anyObject());
		
		move.execute(computer);
		
		// Evaluation.
		verify(registers, times(1)).getRegisterValue(1);
		verify(registers, times(0)).setRegisterValue(anyInt(), anyObject());
		verify(memory, times(0)).getLocation(anyInt());
		verify(memory, times(1)).setLocation(300,3.14);
	}
	
	// First argument is register without offset,
	// second argument is register without offset.
	@Test
	public void testExecution3() {
		
		// Prepare list of arguments.
		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(first);
		when(list.get(1)).thenReturn(second);
		when(first.isRegister()).thenReturn(true);
		when(second.isRegister()).thenReturn(true);
		
		when(first.getValue()).thenReturn(2);
		when(second.getValue()).thenReturn(4);
		
		// Create instruction.
		Instruction move = new InstrMove(list);

		// Mocking register components.
		when(computer.getRegisters()).thenReturn(registers);
				
		when(registers.getRegisterValue(4)).thenReturn(3.14);
		doNothing().when(registers).setRegisterValue(anyInt(), anyObject());
				
		move.execute(computer);
				
		// Evaluation.
		verify(registers, times(1)).getRegisterValue(4);
		verify(registers, times(1)).setRegisterValue(2,3.14);
	}
	
	// First argument is register without offset,
	// second argument is register with offset.
	@Test
	public void testExecution4() {
		
		// Prepare list of arguments.
		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(first);
		when(list.get(1)).thenReturn(second);
		when(first.isRegister()).thenReturn(true);
		when(second.isRegister()).thenReturn(true);
		
		when(first.getValue()).thenReturn(2);
		when(second.getValue()).thenReturn(0x01000504);
		
		// Create instruction.
		Instruction move = new InstrMove(list);

		// Mocking memory and register components.
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
				
		when(registers.getRegisterValue(4)).thenReturn(300);
		when(memory.getLocation(305)).thenReturn(3.14);
		doNothing().when(registers).setRegisterValue(anyInt(), anyObject());
				
		move.execute(computer);
				
		// Evaluation.
		verify(registers, times(1)).getRegisterValue(4);
		verify(registers, times(1)).setRegisterValue(2, 3.14);
		verify(memory, times(1)).getLocation(305);
		verify(memory, times(0)).setLocation(anyInt(),anyObject());
	}
	
	// First argument is register with offset,
	// second argument is register without offset.
	@Test
	public void testExecution5() {
		
		// Prepare list of arguments.
		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(first);
		when(list.get(1)).thenReturn(second);
		when(first.isRegister()).thenReturn(true);
		when(second.isRegister()).thenReturn(true);
		
		when(first.getValue()).thenReturn(0x01000A02);
		when(second.getValue()).thenReturn(4);
		
		when(registers.getRegisterValue(2)).thenReturn(400);
		when(registers.getRegisterValue(4)).thenReturn(3.14);
		doNothing().when(memory).setLocation(anyInt(), anyObject());
		
		// Create instruction.
		Instruction move = new InstrMove(list);

		// Mocking memory and register components.
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		
		move.execute(computer);
		
		// Evaluation.
		verify(registers, times(1)).getRegisterValue(2);
		verify(registers, times(1)).getRegisterValue(4);
		verify(registers, times(0)).setRegisterValue(anyInt(),anyObject());
		verify(memory, times(0)).getLocation(anyInt());
		verify(memory, times(1)).setLocation(410,3.14);
	}
	
	
	// First argument is register with offset,
	// second argument is register with offset.
	@Test
	public void testExecution6() {
		
		// Prepare list of arguments.
		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(first);
		when(list.get(1)).thenReturn(second);
		when(first.isRegister()).thenReturn(true);
		when(second.isRegister()).thenReturn(true);
		
		when(first.getValue()).thenReturn(0x01000A02);
		when(second.getValue()).thenReturn(0x01000B03);
		
		when(registers.getRegisterValue(2)).thenReturn(500);
		when(registers.getRegisterValue(3)).thenReturn(300);
		
		doNothing().when(memory).setLocation(anyInt(), anyObject());
		when(memory.getLocation(311)).thenReturn(3.14);
		
		// Create instruction.
		Instruction move = new InstrMove(list);

		// Mocking memory and register components.
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		
		move.execute(computer);
		
		// Evaluation.
		verify(registers, times(1)).getRegisterValue(2);
		verify(registers, times(1)).getRegisterValue(3);
		verify(registers, times(0)).setRegisterValue(anyInt(),anyObject());
		verify(memory, times(1)).getLocation(311);
		verify(memory, times(1)).setLocation(510,3.14);
	}
}