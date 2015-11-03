package br.unisc.instructions;

import static org.junit.Assert.*;

import org.junit.Test;

import br.unisc.mips.exception.InvalidInstructionException;
import br.unisc.mips.instructions.Instruction;
import br.unisc.mips.instructions.InstructionNOP;
import br.unisc.mips.instructions.parser.InstructionParser;

public class TestInstructionParser {

	/**
	 * 
	 * I can create  a instruction with just an exit label with none instruction. 
	 * Parser will receive an empty string.
	 * I will consider it as a NOP (No Operation)
	 * @throws InvalidInstructionException 
	 * 
	 */
	@Test
	public void shouldCreateInstructionWithJustALabel() throws InvalidInstructionException {
		InstructionParser parser = new InstructionParser();
		Instruction instruction = parser.parse("");
		
		assertEquals(InstructionNOP.class, instruction.getClass());
	}
}