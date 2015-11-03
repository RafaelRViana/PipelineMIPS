/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.mips;

import br.unisc.mips.exception.InvalidInstructionException;
import br.unisc.mips.exception.UnreachableLabelException;
import br.unisc.mips.instructions.Instruction;
import br.unisc.mips.instructions.InstructionIType;
import br.unisc.mips.instructions.InstructionJType;
import br.unisc.mips.instructions.InstructionNOP;
import br.unisc.mips.instructions.InstructionRType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class TestDatapathJumps {
    
        
    private static TextSegment textSegment;
    RegisterBank rBank = RegisterBank.getInstance();        
	
	@BeforeClass
	public static void setUpBeforeClass() {
		textSegment = new TextSegment();
	}
            
    
    @Test
	public void shouldSimulateJumpRegister() throws InvalidInstructionException, UnreachableLabelException {
                rBank.setValue("$t0", 4);
                Instruction first = new InstructionIType("li $t0, 15");
		Instruction second = new InstructionRType("jr $t0");
		Instruction third = new InstructionRType("add $s2, $t0, $t0");
                Instruction fourth = new InstructionIType("andi $t1, $t0, $s2");
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
                
                //Six Cycle
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
                
                //Seven Cycle
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
    
    @Test
	public void shouldSimulateJumpAndLinkEJumpRegisterEJump() throws InvalidInstructionException, UnreachableLabelException {        
                textSegment.create(Arrays.asList(
				"addi $t2, $zero, 10",
                                "jal LABEL",
                                "add $s2, $t2, $t2",
                                "andi $t1, $t2, 3",
                                "j Exit",
                                "LABEL: or $s3, $t2, $s2",
                                "jr $ra",
				"Exit: "));
		
		Datapath datapath = new Datapath(textSegment.getInstructions(), textSegment.getLabels());
	
		//First Cycle
		//Stage 1 : addi
		//Stage 2: NOP
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
                
                //Second Cycle
		//Stage 1 : jal
		//Stage 2 : addi
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
		
		//Third Cycle
		//Stage 1 : NOP
		//Stage 2 : jal
		//Stage 3 : addi
                datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
		
		//Fourth Cycle
                //Stage 1 : or              
		//Stage 2 : NOP
		//Stage 3 : jal
                //Stage 4 : addi
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());              
                
                //Fifth Cycle
                //Stage 1 : jr              
		//Stage 2 : or
		//Stage 3 : NOP
                //Stage 4 : jal
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
		assertEquals(10,rBank.getValue("$t2"));
                
                //Sixth Cycle
                //Stage 1 : NOP              
		//Stage 2 : jr
		//Stage 3 : or
                //Stage 4 : NOP
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
                
                //Seventh Cycle
                //Stage 1 : add              
		//Stage 2 : NOP
		//Stage 3 : jr
                //Stage 4 : or
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
                
                //Eigth Cycle
                //Stage 1 : andi            
		//Stage 2 : add
		//Stage 3 : NOP
                //Stage 4 : jr
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
		assertEquals(10,rBank.getValue("$s3"));
                
                //Ninth Cycle
                //Stage 1 : j              
		//Stage 2 : andi
		//Stage 3 : add
                //Stage 4 : NOP
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
                
                //Tenth Cycle
                //Stage 1 : NOP              
		//Stage 2 : j
		//Stage 3 : andi
                //Stage 4 : add
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
                
                //Eleventh Cycle
                //Stage 1 : NOP              
		//Stage 2 : NOP
		//Stage 3 : j
                //Stage 4 : andi
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
                assertEquals(20,rBank.getValue("$s2"));		
                
                //Twelfth Cycle
                //Stage 1 : NOP              
		//Stage 2 : NOP
		//Stage 3 : NOP
                //Stage 4 : j
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
		assertEquals(2,rBank.getValue("$t1"));
                
                //Thirteenth Cycle
                //Stage 1 : NOP              
		//Stage 2 : NOP
		//Stage 3 : NOP
                //Stage 4 : NOP
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
                
    }

    @Test
        public void shouldSimulatedJump() throws InvalidInstructionException, UnreachableLabelException{
                textSegment.create(Arrays.asList(
				"j Exit",
                                "addi $t0, $zero, 2",
				"Exit: "));
                
                Datapath datapath = new Datapath(textSegment.getInstructions(), textSegment.getLabels());
                
                //First Cycle
		//Stage 1 : addi
		//Stage 2: NOP
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
                
                //Second Cycle
		//Stage 1 : jal
		//Stage 2 : addi
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
		
		//Third Cycle
		//Stage 1 : NOP
		//Stage 2 : jal
		//Stage 3 : addi
                datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
                
                //Fourth Cycle
		//Stage 1 : NOP
		//Stage 2 : jal
		//Stage 3 : addi
                datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
                
                //Fifth Cycle
		//Stage 1 : NOP
		//Stage 2 : jal
		//Stage 3 : addi
                datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
                System.out.println(datapath.getIdEx().getInstructionFetched().getOperation());
                System.out.println(datapath.getExMem().getInstructionFetched().getOperation());
                System.out.println(datapath.getMemWb().getInstructionFetched().getOperation());
    }
}
