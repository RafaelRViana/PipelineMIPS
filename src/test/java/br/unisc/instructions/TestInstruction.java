package br.unisc.instructions;

import static org.junit.Assert.*;

import org.junit.Test;

import br.unisc.mips.exception.InvalidInstructionException;
import br.unisc.mips.instructions.InstructionIType;
import br.unisc.mips.instructions.InstructionJType;
import br.unisc.mips.instructions.InstructionRType;
import br.unisc.mips.instructions.parser.InstructionParser;

public class TestInstruction {
	
	@Test
	public void shouldCreateInstructionRType() throws InvalidInstructionException {
		InstructionRType instruction = new InstructionRType("add $s0, $s1, $t0");
	
		assertEquals("add", instruction.getOperation());
		assertEquals("$s0", instruction.getRd());
		assertEquals("$s1", instruction.getRs());
		assertEquals("$t0", instruction.getRt());
	}
	
	@Test
	public void shouldCreateInstructionRTypeWithMoreThanOneBlankSpace() throws InvalidInstructionException {
		InstructionRType instruction = new InstructionRType("add    $s0  ,    $s1  ,    $t0");
	
		assertEquals("add", instruction.getOperation());
		assertEquals("$s0", instruction.getRd());
		assertEquals("$s1", instruction.getRs());
		assertEquals("$t0", instruction.getRt());
	}
	
	@Test
	public void shouldCreateInstructionIType() throws InvalidInstructionException {
		InstructionIType instruction = new InstructionIType("addi $s0, $s1, 15");
		
		assertEquals("addi", instruction.getOperation());
		assertEquals("$s0", instruction.getRt());
		assertEquals("$s1", instruction.getRs());
		assertEquals("15", instruction.getImmediate());
	}
	
	@Test
	public void shouldCreateInstructionITypeWithBranch() throws InvalidInstructionException {
		InstructionIType instruction = new InstructionIType("beq $s0, $s1, LABEL");
		
		assertEquals("beq", instruction.getOperation());
		assertEquals("$s0", instruction.getRs());
		assertEquals("$s1", instruction.getRt());
		assertEquals("LABEL", instruction.getLabel());
	}
	
	@Test
	public void shouldCreateInstructionLoadWord() throws InvalidInstructionException {
		InstructionIType instruction = new InstructionIType("lw    $t1, 0($t4)");
		
		assertEquals("lw", instruction.getOperation());
		assertEquals("$t1", instruction.getRt());
		assertEquals("$t4", instruction.getRs());
		assertEquals("0", instruction.getImmediate());
	}
	
	@Test
	public void shouldCreateInstructionJType() throws InvalidInstructionException {
		InstructionJType instruction = new InstructionJType("j LABEL");
		
		assertEquals("j", instruction.getOperation());
		assertEquals("LABEL", instruction.getLabel());
	}
	
}