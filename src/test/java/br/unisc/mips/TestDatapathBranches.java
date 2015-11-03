package br.unisc.mips;

import br.unisc.mips.instructions.Instruction;
import br.unisc.mips.instructions.InstructionIType;
import br.unisc.mips.instructions.InstructionJType;
import br.unisc.mips.instructions.InstructionRType;
import java.util.HashMap;
import java.util.Map;

import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Test;

import br.unisc.mips.exception.InvalidInstructionException;
import br.unisc.mips.exception.UnreachableLabelException;

public class TestDatapathBranches {
//	
//	@BeforeClass
//	public static void setUpBeforeClass() throws InvalidInstructionException {
//		
//		TextSegment textSegment = new TextSegment();
//		textSegment.create(Arrays.asList("li $s0, 15",
//				"li $s1, 15",
//				"beq $s0, $s1, EXIT",
//				"addi $s3, $s2, 15",
//				"EXIT:"));
//		
//		datapath = new Datapath(textSegment.getInstructions(), textSegment.getLabels());
//	}
//	
//	private static Datapath datapath;
//	
//	@Test
//	public void shouldJumpToExit() throws UnreachableLabelException, InvalidInstructionException {
//		datapath.moveForward();	
//		assertEquals("li", datapath.getFirstStage().getOperation());
//		
//		datapath.moveForward();
//		assertEquals("li", datapath.getFirstStage().getOperation());
//		
//		datapath.moveForward();
//		assertEquals("beq", datapath.getFirstStage().getOperation());
//		
//		datapath.moveForward();
//		assertEquals("EXIT", datapath.getFirstStage().getOperation());
//	}

        RegisterBank rBank = RegisterBank.getInstance();
        
    
        @Test
	public void shouldSimulateBranch() throws InvalidInstructionException, UnreachableLabelException {
                Instruction first = new InstructionIType("li $t0, 15");
		Instruction second = new InstructionJType("j LABEL");
		Instruction third = new InstructionRType("add $s2, $t0, $t0");
                Instruction fourth = new InstructionIType("andi $t1, $t0, 7");
                Instruction fifth = new InstructionRType("or $s3, $t0, $s2");
	
		Map<String, Integer> labels = new HashMap();
		labels.put("LABEL", 4);
		
		Datapath datapath = new Datapath(Arrays.asList(first, second, third, fourth, fifth), labels);
	
		//First Cycle
		//Stage 1 : li
		//Stage 2: NOP
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
		
		//Second Cycle
		//Stage 1 : beq
		//Stage 2: li
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
		
		//Third Cycle
		//Stage 1 : NOP
		//Stage 2 : beq
		//Stage 3 : li
                datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
		
		//Fourth Cycle
                //Stage 1 : or              
		//Stage 2 : NOP
		//Stage 3 : beq
                //Stage 4 : li
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());              
                
                //Fifth Cycle
                //Stage 1 : NOP              
		//Stage 2 : or
		//Stage 3 : NOP
                //Stage 4 : beq
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
		assertEquals(15,rBank.getValue("$t0"));
                
                //Sixth Cycle
                //Stage 1 : NOP              
		//Stage 2 : NOP
		//Stage 3 : or
                //Stage 4 : NOP
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
		assertEquals(15,rBank.getValue("$t0"));
                
                //Seventh Cycle
                //Stage 1 : NOP              
		//Stage 2 : NOP
		//Stage 3 : NOP
                //Stage 4 : or
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
		assertEquals(15,rBank.getValue("$t0"));
                
                //Eigth Cycle
                //Stage 1 : NOP              
		//Stage 2 : NOP
		//Stage 3 : NOP
                //Stage 4 : NOP
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
		assertEquals(15,rBank.getValue("$s3"));
                                
	}
	
}