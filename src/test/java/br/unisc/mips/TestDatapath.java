package br.unisc.mips;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import br.unisc.mips.exception.InvalidInstructionException;
import br.unisc.mips.exception.UnreachableLabelException;
import br.unisc.mips.instructions.Instruction;
import br.unisc.mips.instructions.InstructionIType;
import br.unisc.mips.instructions.InstructionJType;
import br.unisc.mips.instructions.InstructionRType;

public class TestDatapath {

	private static RegisterBank rBank = RegisterBank.getInstance();
	private DataMemory memory = DataMemory.getInstance();
	
	@Test
	public void shouldProcessFirstStep() throws UnreachableLabelException, InvalidInstructionException {
		Instruction first = new InstructionIType("li $s0, 15");
		Instruction second = new InstructionIType("li $s1, 25");
		Instruction third = new InstructionRType("add $s2, $s1, $s0");
		Instruction fourth = new InstructionIType("addi $s3, $s2, 15");
		
		Datapath datapath = new Datapath(Arrays.asList(first, second, third, fourth), new HashMap<String, Integer>());
		
		datapath.moveForward();
		
		assertEquals("li", datapath.getIfId().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getIdEx().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getExMem().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getMemWb().getInstructionFetched().getOperation());
		
		datapath.moveForward();
		
		assertEquals("li", datapath.getIfId().getInstructionFetched().getOperation());
		assertEquals("li", datapath.getIdEx().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getExMem().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getMemWb().getInstructionFetched().getOperation());
		
		datapath.moveForward();
		
		assertEquals("add", datapath.getIfId().getInstructionFetched().getOperation());
		assertEquals("li", datapath.getIdEx().getInstructionFetched().getOperation());
		assertEquals("li", datapath.getExMem().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getMemWb().getInstructionFetched().getOperation());
		
		assertEquals(15, datapath.getExMem().getALUOut());
		
		datapath.moveForward();
		
		assertEquals("addi", datapath.getIfId().getInstructionFetched().getOperation());
		assertEquals("add", datapath.getIdEx().getInstructionFetched().getOperation());
		assertEquals("li", datapath.getExMem().getInstructionFetched().getOperation());
		assertEquals("li", datapath.getMemWb().getInstructionFetched().getOperation());
		
		assertEquals(25, datapath.getExMem().getALUOut());
		
		datapath.moveForward();
		
		assertEquals("NOP", datapath.getIfId().getInstructionFetched().getOperation());
		/*assertEquals("addi", datapath.getIdEx().getInstructionFetched().getOperation());
		assertEquals("add", datapath.getExMem().getInstructionFetched().getOperation());
		assertEquals("li", datapath.getMemWb().getInstructionFetched().getOperation());
		
		assertEquals(25, datapath.getExMem().getALUOut());
		assertEquals(15, rBank.getValue("s0")); //J� carregou o valor de 15 para o banco de registradores no quinto ciclo
		assertEquals(0, rBank.getValue("s1")); //Mas, ainda n�o carregou o valor do s1...
		*/
		//datapath.moveForward();
	}
	
	
	@Test
	public void shoudSimulateLoadWordWithoutHazard() throws UnreachableLabelException, InvalidInstructionException {
		Instruction first = new InstructionIType("lw $t0, 20($s1)");
		
		Datapath datapath = new Datapath(Arrays.asList(first), new HashMap<String, Integer>());
		memory.write(20, 15); //na posi��o 20 vou colocar o inteiro 15
		
		//First stage
		datapath.moveForward();
	
		//Second Stage
		datapath.moveForward();
		
		//Load Word soma o valor do imeadiato com o valor do registrador
		
		//Third Stage
		datapath.moveForward();
		assertEquals(20, datapath.getExMem().getALUOut()); //$s0 estava como zero, por isso a soma � 20 que � a constante
 		assertTrue(datapath.getExMem().isMemToReg());
		assertTrue(datapath.getExMem().isMemRead());
		assertFalse(datapath.getExMem().isMemWrite());
 		assertEquals(0, rBank.getValue("t0")); //o t0 n�o foi inicializado
 		
		//Fourth Stage
		datapath.moveForward();
		assertEquals(15, datapath.getMemWb().getMemoryData());
		
		//Fifth Stage
		datapath.moveForward();
		assertEquals(15, rBank.getValue("t0"));
	}
	
	@Test
	public void shouldSimulateStoreWordWithoutHazard() throws UnreachableLabelException, InvalidInstructionException {
		Instruction first = new InstructionIType("sw $t0, 15($s1)");
		
		Datapath datapath = new Datapath(Arrays.asList(first), new HashMap<String, Integer>());
		rBank.setValue("t0", 7);
		
		//First Stage
		datapath.moveForward();
		
		//Second Stage
		datapath.moveForward();
		assertEquals(new Integer(0), datapath.getIdEx().getReadData1());
		assertEquals(new Integer(15), datapath.getIdEx().getReadData2());
		
		//Third Stage
		datapath.moveForward();
		//o valor do registrador ser� registrado no endere�o 15 da mem�ria.
		assertEquals(15, datapath.getExMem().getALUOut()); //$s1 estava como zero, por isso a soma � 15 (que � a constante)
		assertTrue(datapath.getExMem().isMemWrite());
		assertFalse(datapath.getExMem().isMemRead());
		assertFalse(datapath.getExMem().isRegWrite());
		
		//Fourth Stage
		datapath.moveForward();
		assertEquals(7, memory.read(15)); //grava o valor do registrador na mem�ria
	}
	
	@Test
	public void shouldSimulateJump() throws InvalidInstructionException, UnreachableLabelException {
		Instruction first = new InstructionJType("j LABEL");
		Instruction second = new InstructionIType("addi $s1, $zero, 15");
		Instruction third = new InstructionRType("add $s2, $zero, $s3");
	
		Map<String, Integer> labels = new HashMap();
		labels.put("LABEL", 3);
		
		Datapath datapath = new Datapath(Arrays.asList(first, second, third), labels);
	
		//First Cycle
		//Stage 1 : Jump
		//Stage 2: NOP
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
		
		//Second Cycle
		//Stage 1 : NOP
		//Stage 2: Jump
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
		
		//Third Stage
		//Stage 1 : addi $s2
		//Stage 2 : NOP
		//Stage 3 : Jump
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
		
		//Fourth Stage
		datapath.moveForward();
		System.out.println(datapath.getIfId().getInstructionFetched().getOperation());
		
		//addi $s2, $zero, 20
	}
	
	/**
	 * Exemplo do slide passado em aula
	 * @throws InvalidInstructionException 
	 * @throws UnreachableLabelException 
	 */
	@Test
	public void shouldSimulateLWHazard() throws UnreachableLabelException, InvalidInstructionException {
		Instruction first = new InstructionIType("lw $s2, 20($s1)");
		Instruction second = new InstructionRType("and $s4, $s2, $s5");
		Instruction third = new InstructionRType("or $s8, $s2, s6");
		Instruction fourth = new InstructionRType("add $s7, $s4, $s2");
		
		Datapath datapath = new Datapath(Arrays.asList(first, second, third, fourth), new HashMap<String, Integer>());
		
		datapath.moveForward();
		
		fail("Terminar de implementar o teste que cause uma bolha no pipeline para esperar a instru��o lw");
	}
	
	@Test
	public void shouldExecuteAddiOperation() throws UnreachableLabelException, InvalidInstructionException {
		Instruction first = new InstructionIType("addi $s0, $zero, 23");
		
		Datapath datapath = new Datapath(Arrays.asList(first), new HashMap<String, Integer>());
		
		datapath.moveForward();
		
		assertEquals("addi", datapath.getIfId().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getIdEx().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getExMem().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getMemWb().getInstructionFetched().getOperation());
		
		datapath.moveForward();
		
		assertEquals("NOP", datapath.getIfId().getInstructionFetched().getOperation());
		assertEquals("addi", datapath.getIdEx().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getExMem().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getMemWb().getInstructionFetched().getOperation());
		
		datapath.moveForward();
		
		assertEquals("NOP", datapath.getIfId().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getIdEx().getInstructionFetched().getOperation());
		assertEquals("addi", datapath.getExMem().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getMemWb().getInstructionFetched().getOperation());
		
		assertEquals(23, datapath.getExMem().getALUOut());
		
		datapath.moveForward();
		
		assertEquals("NOP", datapath.getIfId().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getIdEx().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getExMem().getInstructionFetched().getOperation());
		assertEquals("addi", datapath.getMemWb().getInstructionFetched().getOperation());
		
		assertEquals(0, rBank.getValue("s0"));
		
		datapath.moveForward();
		
		assertEquals("NOP", datapath.getIfId().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getIdEx().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getExMem().getInstructionFetched().getOperation());
		assertEquals("NOP", datapath.getMemWb().getInstructionFetched().getOperation());
		
		assertEquals(23, rBank.getValue("s0")); //S� modificou o valor no banco de registradores no quinto est�gio
	}
	
//	@Test
//	public void shouldProcessBackward() {
//		datapath.moveBackward();
//		
//		System.out.println(datapath.getFirstStage().getOperation());
//		System.out.println(datapath.getSecondStage().getOperation());
//		System.out.println(datapath.getThirdStage().getOperation());
//		System.out.println(datapath.getFourthStage().getOperation());
//		System.out.println(datapath.getFifthStage().getOperation());
//		
//		assertEquals("NOP", datapath.getFirstStage().getOperation());
//		assertEquals("addi", datapath.getSecondStage().getOperation());
//		assertEquals("add", datapath.getThirdStage().getOperation());
//		assertEquals("li", datapath.getFourthStage().getOperation());
//		assertEquals("li", datapath.getFifthStage().getOperation());
//	}
//	
//	@Test
//	public void shouldProcessBackwardTwoTimes() {
//		datapath.moveBackward();
//		
//		assertEquals("addi", datapath.getFirstStage().getOperation());
//		assertEquals("add", datapath.getSecondStage().getOperation());
//		assertEquals("li", datapath.getThirdStage().getOperation());
//		assertEquals("li", datapath.getFourthStage().getOperation());
//		assertEquals("NOP", datapath.getFifthStage().getOperation());
//	}
//	
//	@Test
//	public void shouldProcessBackwardThreeTimes() {
//		datapath.moveBackward();
//		
//		assertEquals("add", datapath.getFirstStage().getOperation());
//		assertEquals("li", datapath.getSecondStage().getOperation());
//		assertEquals("li", datapath.getThirdStage().getOperation());
//		assertEquals("NOP", datapath.getFourthStage().getOperation());
//		assertEquals("NOP", datapath.getFifthStage().getOperation());
//	}
//	
//	@Test
//	public void shouldProcessBackwardFourTimes() {
//		datapath.moveBackward();
//		
//		assertEquals("li", datapath.getFirstStage().getOperation());
//		assertEquals("li", datapath.getSecondStage().getOperation());
//		assertEquals("NOP", datapath.getThirdStage().getOperation());
//		assertEquals("NOP", datapath.getFourthStage().getOperation());
//		assertEquals("NOP", datapath.getFifthStage().getOperation());
//	}
//	
//	@Test
//	public void shouldProcessBackwardFiveTimes() {
//		datapath.moveBackward();
//		
//		assertEquals("li", datapath.getFirstStage().getOperation());
//		assertEquals("NOP", datapath.getSecondStage().getOperation());
//		assertEquals("NOP", datapath.getThirdStage().getOperation());
//		assertEquals("NOP", datapath.getFourthStage().getOperation());
//		assertEquals("NOP", datapath.getFifthStage().getOperation());
//	}
//	
//	@Test
//	public void shouldProcessBackwardSixTimes() {
//		datapath.moveBackward();
//		
//		assertEquals("NOP", datapath.getFirstStage().getOperation());
//		assertEquals("NOP", datapath.getSecondStage().getOperation());
//		assertEquals("NOP", datapath.getThirdStage().getOperation());
//		assertEquals("NOP", datapath.getFourthStage().getOperation());
//		assertEquals("NOP", datapath.getFifthStage().getOperation());
//	}
	
}