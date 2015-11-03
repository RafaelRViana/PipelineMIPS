package br.unisc.mips.instructions.parser;

import br.unisc.mips.exception.InvalidInstructionException;
import br.unisc.mips.instructions.Instruction;
import br.unisc.mips.instructions.InstructionIType;
import br.unisc.mips.instructions.InstructionJType;
import br.unisc.mips.instructions.InstructionNOP;
import br.unisc.mips.instructions.InstructionRType;

public class InstructionParser {

	public Instruction parse(String instruction) throws InvalidInstructionException {
		
		if( "".equals(instruction.trim()) ) {
			return new InstructionNOP();
		}
		
		String operation = instruction.split(" ")[0].trim();
		
		if(isInstructionRType(operation))
			return new InstructionRType(instruction);	
		else if(isInstructionIType(operation))
			return new InstructionIType(instruction);
		else if(isInstructionJType(operation))
			return new InstructionJType(instruction);
		
		throw new InvalidInstructionException(String.format(InvalidInstructionException.INSTRUCTION_NOT_SUPPORTED, instruction));
	}
	
	private boolean isInstructionRType(String operation) {
		return InstructionRType.getSupportedInstructions().contains(operation);
	}
	
	private boolean isInstructionJType(String operation) {
		return InstructionJType.getSupportedInstructions().contains(operation);
	}
	
	private boolean isInstructionIType(String operation) {
		return InstructionIType.getSupportedInstructions().contains(operation);
	}
	
}