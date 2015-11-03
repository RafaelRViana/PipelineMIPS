package br.unisc.mips.instructions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InstructionRType implements Instruction {

	private static final List<String> RD_RS_RT = Arrays.asList("add", "addu", "and", "nor", "or", "slt", "sltu", "sub", "subu", "xor");
	private static final List<String> RS_RT = Arrays.asList("div", "divu", "mult", "multu");
	private static final List<String> RD_RS = Arrays.asList("jalr");
	private static final List<String> RS = Arrays.asList("jr");
	private static final List<String> RD_RT_SA = Arrays.asList("sll", "sra", "srl");
	
	private String operation = "";
	
	private String rs = "";
	
	private String rt = "";
	
	private String rd = "";
	
	private String shamt = "";
	
	public InstructionRType(String instruction) {
		String[] separateOperationAndRegisters = instruction.split(" ", 2);
		operation = separateOperationAndRegisters[0];
		
		String[] registers = separateOperationAndRegisters[1].split(",");
		
		if(RD_RS_RT.contains(operation)) {
			rd = registers[0].trim();
			rs = registers[1].trim();
			rt = registers[2].trim();
		} else if(RS_RT.contains(operation)) {
			rs = registers[0].trim();
			rt = registers[1].trim();
		} else if(RD_RS.contains(operation)) {
			rd = registers[0].trim();
			rs = registers[1].trim();
		} else if(RS.contains(operation)) {
			rs = registers[0].trim();
		} else if(RD_RT_SA.contains(operation)) {
			rd = registers[0].trim();
			rt = registers[1].trim();
			shamt = registers[2].trim();
		}
	}

	public static List<String> getSupportedInstructions() {
		List<String> SUPPORTED_INSTRUCTIONS = new ArrayList<String>();
		SUPPORTED_INSTRUCTIONS.addAll(RD_RS_RT);
		SUPPORTED_INSTRUCTIONS.addAll(RS_RT);
		SUPPORTED_INSTRUCTIONS.addAll(RD_RS);
		SUPPORTED_INSTRUCTIONS.addAll(RS);
		SUPPORTED_INSTRUCTIONS.addAll(RD_RT_SA);	
		
		return SUPPORTED_INSTRUCTIONS;
	}
	
	public String getOperation() {
		return operation;
	}

	public String getRs() {
		return rs;
	}

	public String getRt() {
		return rt;
	}

	public String getRd() {
		return rd;
	}

	public String getShamt() {
		return shamt;
	}

}
