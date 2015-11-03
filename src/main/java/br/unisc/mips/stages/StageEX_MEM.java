package br.unisc.mips.stages;

import br.unisc.mips.instructions.Instruction;
import br.unisc.mips.instructions.InstructionNOP;

/**
 * Variables are based on:
 * http://fetweb.ju.edu.jo/staff/cpe/jafar/Teaching/CPE335/06%20mipspipeline%20I.pdf
 */
public class StageEX_MEM {

	private Instruction instructionFetched = new InstructionNOP(); /** it is not necessary but will help us to test */
	
	private int branchAddress = 0;
	private int ALUOut = 0;
	

	private int zero = 0;
	private String registerDestination = "";
	private Integer regRtContents = 0; //para transmitir o valor no store word
	
	private boolean memToReg = false;
	private boolean regWrite = false;
	private boolean memRead = false;
	private boolean memWrite = false;
	private boolean branch = false;
	
	public boolean isMemWrite() {
		return memWrite;
	}
	
	public void  setMemWrite(boolean memWrite) {
		this.memWrite = memWrite;
	}

	public boolean isMemRead() {
		return memRead;
	}
	
	public void setMemRead(boolean memRead) {
		this.memRead = memRead;
	}

	public int getALUOut() {
		return ALUOut;
	}
	
	public void setALUOut(int aluResult) {
		this.ALUOut = aluResult;
	}

	public boolean isMemToReg() {
		return memToReg;
	}

	public void setMemToReg(boolean memToReg) {
		this.memToReg = memToReg;
	}

	public boolean isRegWrite() {
		return regWrite;
	}

	public void setRegWrite(boolean regWrite) {
		this.regWrite = regWrite;
	}

	public boolean isBranch() {
		return branch;
	}

	public void setBranch(boolean branch) {
		this.branch = branch;
	}

	public String getRegisterDestination() {
		return registerDestination;
	}

	public void setRegisterDestination(String registerDestination) {
		this.registerDestination = registerDestination;
	}

	public int getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(int branchAddress) {
		this.branchAddress = branchAddress;
	}

	public Instruction getInstructionFetched() {
		return instructionFetched;
	}

	public void setInstructionFetched(Instruction instructionFetched) {
		this.instructionFetched = instructionFetched;
	}

	public Integer getRegRtContents() {
		return regRtContents;
	}

	public void setRegRtContents(Integer regRtContents) {
		this.regRtContents = regRtContents;
	}
	
        public void setZero(int value){
                this.zero = value;
        }
        
        public int getZero(){
                return zero;
        }
        
}
