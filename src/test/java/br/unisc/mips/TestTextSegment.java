package br.unisc.mips;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import br.unisc.mips.exception.InvalidInstructionException;
import br.unisc.mips.instructions.InstructionIType;
import br.unisc.mips.instructions.InstructionRType;

public class TestTextSegment {
	
	private static TextSegment textSegment;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		textSegment = new TextSegment();
	}
	
	@Test
	public void shouldCreateDataSegment() throws InvalidInstructionException {
		textSegment.create(Arrays.asList(
				"add $s1, $s0, $s1", 
				"sub $s0, $s1, $s2", 
				"lw $t1, 0($t4)",
				"addi $t2, $t1, 100",
				"sw $t2, 0($t0)",
				"label: add $s1, $s0, $s1"));
		
		assertEquals(InstructionRType.class, textSegment.getInstruction(0).getClass());
		assertEquals(InstructionRType.class, textSegment.getInstruction(1).getClass());
		assertEquals(InstructionIType.class, textSegment.getInstruction(2).getClass());
		assertEquals(InstructionIType.class, textSegment.getInstruction(3).getClass());
		assertEquals(InstructionIType.class, textSegment.getInstruction(4).getClass());
		assertEquals(InstructionRType.class, textSegment.getInstruction(5).getClass());
		
		assertEquals(new Integer(5), textSegment.getLabelLine("label"));
	}

}
