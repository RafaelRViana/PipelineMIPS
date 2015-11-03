package br.unisc.mips.stages;

import br.unisc.mips.instructions.Instruction;
import br.unisc.mips.instructions.InstructionNOP;

/**
 * Variables are based on:
 * http://fetweb.ju.edu.jo/staff/cpe/jafar/Teaching/CPE335/06%20mipspipeline%20I.pdf
 */
public class StageMEM_WB {
	
	private Instruction instructionFetched = new InstructionNOP(); /** to help us testing **/
	
	private String registerDestination;
	private int ALUOut = 0;
	private int memoryData = 0; //Usado para armazenar dado carregado da mem—ria no load word
	
	private boolean memToReg = false;
	private boolean regWrite = false;
	
	public void setALUOut(int ALUOut) {
		this.ALUOut = ALUOut;
	}
	
	public int getALUOut() {
		return ALUOut;
	}

	public String getRegisterDestination() {
		return registerDestination;
	}

	public void setRegisterDestination(String registerDestination) {
		this.registerDestination = registerDestination;
	}

	public boolean isMemToReg() {
		return memToReg;
	}

	public void setMemToReg(boolean memToReg) {
		this.memToReg = memToReg;
	}

	public int getMemoryData() {
		return memoryData;
	}

	public void setMemoryData(int memoryData) {
		this.memoryData = memoryData;
	}

	public boolean isRegWrite() {
		return regWrite;
	}

	public void setRegWrite(boolean regWrite) {
		this.regWrite = regWrite;
	}

	public Instruction getInstructionFetched() {
		return instructionFetched;
	}

	public void setInstructionFetched(Instruction instructionFetched) {
		this.instructionFetched = instructionFetched;
	}
	
}
