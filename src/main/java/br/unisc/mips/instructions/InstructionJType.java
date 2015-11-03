package br.unisc.mips.instructions;

import java.util.Arrays;
import java.util.List;

import br.unisc.mips.exception.InvalidInstructionException;

public class InstructionJType implements Instruction {

	private static final List<String> SUPPORTED_INSTRUCTIONS = Arrays.asList("j", "jal", "jalr");
	
	private String operation = "";
	
	private String label = "";

	public InstructionJType(String instruction) throws InvalidInstructionException {
		String[] separateOperationAndRegisters = instruction.split(" ", 2);
		operation = separateOperationAndRegisters[0];
		label = separateOperationAndRegisters[1];
	}
	
	public static List<String> getSupportedInstructions() {
		return SUPPORTED_INSTRUCTIONS;
	}
	
	public String getOperation() {
		return operation;
	}

	public String getLabel() {
		return label;
	}
	
}
