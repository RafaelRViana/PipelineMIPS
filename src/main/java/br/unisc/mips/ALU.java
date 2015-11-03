package br.unisc.mips;

import br.unisc.mips.exception.InvalidInstructionException;

public class ALU {

	RegisterBank rBank = RegisterBank.getInstance();
	
	public Integer execute(String operation, Integer first, Integer second) throws InvalidInstructionException {
		if("add".equals(operation) || "addi".equals(operation) || "addiu".equals(operation) || "addu".equals(operation)) {
			return first + second;
		}
		
		else if("and".equals(operation) || "andi".equals(operation)) {
			return first & second;
		}
		
		else if("beq".equals(operation)) {
			if (first == second){
				return 1;
			}
			else{
				return 0;
			}
		}
		
		else if("bgez".equals(operation)) {
			if (first >= 0){
				return 1;
			}
			else{
				return 0;
			}
		}
		
		else if("bgtz".equals(operation)) {
			if (first > 0){
				return 1;
			}
			else{
				return 0;
			}
		}
		
		else if("blez".equals(operation)) {
			if (first <= 0){
				return 1;
			}
			else{
				return 0;
			}
		}
		
		else if("bltz".equals(operation)) {
			if (first < 0){
				return 1;
			}
			else{
				return 0;
			}
		}
		
		else if("bne".equals(operation)) {
			if (first != second){
				return 1;
			}
			else{
				return 0;
			}
		}
		
		else if("j".equals(operation)) {
			return 1;
		}
		
		else if("jal".equals(operation)) {
			return 1;
		}
		
		else if("jr".equals(operation)) {
			return first;
		}
		
		else if("li".equals(operation)) {
			return first;
		}
		
		else if("lui".equals(operation)) {
			return second << 16;
		}
		
		else if("lw".equals(operation)) {
			return first + second;
		}
		
		else if("nor".equals(operation)) {
			return ~(first | second);
		}
		
		else if("or".equals(operation)) {
			return first | second;
		}
		
		else if("ori".equals(operation)) {
			return first | second;
		}
		
		else if("sll".equals(operation)) {
			return first << second;
		}
		
		else if("slt".equals(operation) || "slti".equals(operation)) {
			if (first < second){
				return 1;
			}
			else{
				return 0;
			}
		}
		
		else if("sra".equals(operation) || "srl".equals(operation)) {
			return first >> second;
		}
		
		else if("sub".equals(operation) || "subu".equals(operation)) {
			return first - second;
		}
		
		else if("sw".equals(operation)) {
			return first + second;
		}
		
		else if("xor".equals(operation) || "xori".equals(operation)) {
			return first ^ second;
		}
		
		else if("NOP".equals(operation)) {
			//Do nothing...
			return 0;
		}
		
		throw new InvalidInstructionException(String.format(InvalidInstructionException.INSTRUCTION_NOT_SUPPORTED, operation));
	}
	
}