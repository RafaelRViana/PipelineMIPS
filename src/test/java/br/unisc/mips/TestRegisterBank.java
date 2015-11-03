package br.unisc.mips;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestRegisterBank {

	private static RegisterBank registerBank;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		registerBank = RegisterBank.getInstance();
	}
	
	@Test
	public void shouldSetAndGetterValuesUsingRegistersNames() {
		registerBank.setValue("t0", 10);
		assertEquals(new Integer(10), (Integer) registerBank.getValue("t0"));
	}
	
}