package br.unisc.mips.instructions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InstructionIType implements Instruction {

	private static final List<String> RT_RS_IMMED = Arrays.asList("addi", "addiu", "andi", "ori", "slti", "sltiu", "xori");
	private static final List<String> RS_RT_LABEL = Arrays.asList("beq", "bne");
	private static final List<String> RS_LABEL = Arrays.asList("bgez", "bgtz", "blez", "bltz");
	private static final List<String> RT_IMMED = Arrays.asList("lb", "lbu", "lh", "lhu", "li", "lui");
	private static final List<String> LW = Arrays.asList("lw", "sw");
	
 	private String operation = "";
	
	private String rs = "";
	
	private String rt = "";
	
	private String immediate = ""; 
	
	private String label = "";
	
	public InstructionIType(String instruction) {
		String[] separateOperationAndRegisters = instruction.split(" ", 2);
		operation = separateOperationAndRegisters[0];
		
		String[] registers = separateOperationAndRegisters[1].split(",");
		
		if(RT_RS_IMMED.contains(operation)) {
			rt = registers[0].trim();
			rs = registers[1].trim();
			immediate = registers[2].trim();
		} else if(RS_RT_LABEL.contains(operation)) {
			rs = registers[0].trim();
			rt = registers[1].trim();
			label = registers[2].trim();
		} else if(RS_LABEL.contains(operation)) {
			rs = registers[0].trim();
			label = registers[1].trim();
		} else if(RT_IMMED.contains(operation)) {
			rt = registers[0].trim();
			immediate = registers[1].trim();
		} else if(LW.contains(operation)) {
			rt = registers[0].trim();
			
			String[] secondPart = registers[1].replace("(", "").replace(")", "").split("\\$");
			immediate = secondPart[0].trim();
			rs = "$" + secondPart[1].trim();
		}
	}
	
	public static List<String> getSupportedInstructions() {
		List<String> SUPPORTED_INSTRUCTIONS = new ArrayList<String>();
		SUPPORTED_INSTRUCTIONS.addAll(RT_RS_IMMED);
		SUPPORTED_INSTRUCTIONS.addAll(RS_RT_LABEL);
		SUPPORTED_INSTRUCTIONS.addAll(RS_LABEL);
		SUPPORTED_INSTRUCTIONS.addAll(RT_IMMED);
		SUPPORTED_INSTRUCTIONS.addAll(LW);
		
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

	public String getImmediate() {
		return immediate;
	}
	
	public String getLabel() {
		return label;
	}
	
}
