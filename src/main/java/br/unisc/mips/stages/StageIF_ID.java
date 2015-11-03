package br.unisc.mips.stages;

import br.unisc.mips.instructions.Instruction;
import br.unisc.mips.instructions.InstructionNOP;

/**
 * Variables are based on:
 * http://fetweb.ju.edu.jo/staff/cpe/jafar/Teaching/CPE335/06%20mipspipeline%20I.pdf
 */
public class StageIF_ID {

	private Instruction instructionFetched = new InstructionNOP();
    private int PCSrc = 0;
	
	public Instruction getInstructionFetched() {
		return instructionFetched;
	}
	
	public void setInstructionFetched(Instruction currentInstruction) {
		this.instructionFetched = currentInstruction;
	}

    public void setPCSrc(int value){
    	this.PCSrc = value;
    }
        
    public int getPCSrc(){
    	return PCSrc;
    }
}
