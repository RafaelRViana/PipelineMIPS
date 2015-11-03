package br.unisc.mips.stages;

import br.unisc.mips.instructions.Instruction;
import br.unisc.mips.instructions.InstructionNOP;

/**
 * Variables are based on:
 * http://fetweb.ju.edu.jo/staff/cpe/jafar/Teaching/CPE335/06%20mipspipeline%20I.pdf
 */
public class StageID_EX {

	private Instruction instructionFetched = new InstructionNOP();
	private Integer readData1 = 0;
	private Integer readData2 = 0;
	private Integer regRtContents = 0;
    private String label = "";
	
	private boolean memToReg = false;
	private boolean regWrite = false;
	private boolean memRead = false;
	private boolean memWrite = false;
	private boolean branch = false;
	
	/**
	 * As write in the presentation above (slide 12). The ALUSrc is used to selected either a register or a constant.
	 * We are working with integers directly. So, this control isn't necessary for us.
	 * http://www.cs.ucr.edu/~junyang/teach/161_F05/slides/Lec12-Single-Cycle-dp.pdf
	 * private boolean ALUSrc = false; 
	 */
	
	private String registerDestination = "";
	private String ALUOp = "NOP";
	
	public Instruction getInstructionFetched() {
		return instructionFetched;
	}
	
	public void setInstructionFetched(Instruction instructionFetched) {
		this.instructionFetched = instructionFetched;
	}

	public String getALUOp() {
		return ALUOp;
	}
	
	public void setALUOp(String aLUOp) {
		this.ALUOp = aLUOp;
	}

	public Integer getReadData1() {
		return readData1;
	}
	
	public void setReadData1(Integer readData1) {
		this.readData1 = readData1;
	}
	
	public Integer getReadData2() {
		return readData2;
	}

	public void setReadData2(Integer readData2) {
		this.readData2 = readData2;
	}

	public boolean isRegWrite() {
		return regWrite;
	}
	
	public void setRegWrite(boolean regWrite) {
		this.regWrite = regWrite;
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

	public boolean isMemRead() {
		return memRead;
	}

	public void setMemRead(boolean memRead) {
		this.memRead = memRead;
	}

	public boolean isMemWrite() {
		return memWrite;
	}

	public void setMemWrite(boolean memWrite) {
		this.memWrite = memWrite;
	}

	public boolean isBranch() {
		return branch;
	}

	public void setBranch(boolean branch) {
		this.branch = branch;
	}

	public Integer getRegRtContents() {
		return regRtContents;
	}

	public void setRegRtContents(Integer regRtContents) {
		this.regRtContents = regRtContents;
	}

        public void setLabel(String labelName){
                this.label = labelName;
        }
        
        public String getLabel(){
                return label;
        }
}
