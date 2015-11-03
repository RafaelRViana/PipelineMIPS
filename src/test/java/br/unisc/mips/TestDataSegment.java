package br.unisc.mips;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestDataSegment {

	private static DataSegment dataSegment;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		dataSegment = new DataSegment();
	}
	
	@Test
	public void shouldCreateDataSegment() {
		dataSegment.create(Arrays.asList("var_a:  .word   0x0", "var_b:  .word   0x4"));
		
		assertEquals(DataType.WORD, dataSegment.get("var_a").getType());
		assertEquals("0x0", dataSegment.get("var_a").getValue());
		
		assertEquals(DataType.WORD, dataSegment.get("var_b").getType());
		assertEquals("0x4", dataSegment.get("var_b").getValue());
	}
	
}