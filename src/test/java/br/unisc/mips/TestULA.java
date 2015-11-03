//package br.unisc.mips;
//
//import static org.junit.Assert.*;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import br.unisc.mips.exception.InvalidInstructionException;
//import br.unisc.mips.exception.Jump;
//
//public class TestULA {
//
//	@BeforeClass
//	public static void setUpBeforeClass() {
//		alu = new ALU();
//	}
//	
//	private static ALU alu;
//	private static RegisterBank rBank = RegisterBank.getInstance();
//	
//	private int result;
//	
//	@Test
//	public void shouldAdd() throws Jump, InvalidInstructionException {
//		result = alu.execute("add", 5, 10);
//		assertEquals(15, result);
//	}
//	
//	@Test
//	public void shouldAddi() throws Jump, InvalidInstructionException {
//		result = alu.execute("addi", 25, 25);
//		assertEquals(50, rBank.getValue("s5"));
//	}
//	
//	@Test
//	public void shouldSub() throws Jump, InvalidInstructionException {
//		result = alu.execute("sub", 10, 5);
//		assertEquals(5, result);
//	}
//	
//	@Test(expected=Jump.class)
//	public void shouldBeq() {
//		//alu.execute(new InstructionIType("beq $s1, $s2, LABEL"));
//		fail("como ficaria o BEQ nessa nova ULA?");
//	}
//	
//	@Test
//	public void shouldNotBeq() {
//		rBank.setValue("s1", 15);
//		rBank.setValue("s2", 20);
//		//alu.execute(new InstructionIType("beq $s1, $s2, LABEL"));
//		fail("como ficaria o BEQ nessa nova ULA?");
//	}
//	
//}