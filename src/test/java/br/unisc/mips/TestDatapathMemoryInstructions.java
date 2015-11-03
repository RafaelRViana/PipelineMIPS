package br.unisc.mips;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import br.unisc.mips.exception.InvalidInstructionException;

public class TestDatapathMemoryInstructions {

	@BeforeClass
	public static void setUpBeforeClass() throws InvalidInstructionException {
		
		TextSegment textSegment = new TextSegment();
		textSegment.create(Arrays.asList("li $s0, 15",
				"sw $s0, 0($sp)"));
		
		datapath = new Datapath(textSegment.getInstructions(), textSegment.getLabels());
	}
	
	private static Datapath datapath;
	private RegisterBank rBank = RegisterBank.getInstance();
	
	@Test
	public void shouldStoreValueToMemoryJustInTheLastCycle() {
		fail("I need to finish this test");
	}
	
	@Test
	public void shouldLoadValueFromMemoryJustInTheLastCycle() {
		fail("I need to finish this test");
	}
	
}