//package br.unisc.mips;
//
//import static org.junit.Assert.*;
//
//import java.util.Arrays;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import br.unisc.mips.exception.InvalidInstructionException;
//import br.unisc.mips.exception.UnreachableLabelException;
//
//public class TestDatapathForwarding {
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws InvalidInstructionException {
//		
//		TextSegment textSegment = new TextSegment();
//		textSegment.create(Arrays.asList("li $s0, 15",
//				"li $s1, 20",
//				"add $s2, $s0, $s1",
//				"addi $s3, $s2, 15"));
//		
//		datapath = new Datapath(textSegment.getInstructions(), textSegment.getLabels());
//	}
//	
//	private static Datapath datapath;
//	private RegisterBank rBank = RegisterBank.getInstance();
//	
//	@Test
//	public void shouldForwardResultOfS2ToForwardingUnit() throws UnreachableLabelException, InvalidInstructionException {
//		//First Cycle
//		datapath.moveForward();
//		
//		//Second Cycle
//		datapath.moveForward();
//		
//		//Third Cycle
//		datapath.moveForward();
//		
//		//Fourth Cycle
//		datapath.moveForward();
//		
//		//Fifth Cycle
//		datapath.moveForward();
//		assertEquals(15, rBank.getValue("s0"));
//		assertEquals("li", datapath.getFifthStage().getOperation());
//		
//		//Sixth Cycle
//		datapath.moveForward();
//		assertEquals(20, rBank.getValue("s1"));
//		assertEquals("li", datapath.getFifthStage().getOperation());
//		
//		//Seventh Cycle
//		datapath.moveForward();
//		assertEquals("add", datapath.getFifthStage().getOperation());
//		assertEquals(35, rBank.getValue("s2"));
//		
//		//Eighth Cycle
//		datapath.moveForward();
//		assertEquals("addi", datapath.getFifthStage().getOperation());
//		assertEquals(45, rBank.getValue("s3"));
//		
//		fail("Problema: Deveria acontecer erro, porque n‹o foi utilizada uma unidade de forwarding...");
//	}
//	
//}