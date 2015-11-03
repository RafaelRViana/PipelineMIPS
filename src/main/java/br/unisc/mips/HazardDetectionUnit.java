package br.unisc.mips;

/**
 * 
 * Detects load/read hazard, inserts stall if needed.
 * if (ID/EX.MemRead
 * and ((ID/EX.RegisterRt = IF/ID.RegisterRs) or (ID/EX.RegisterRt = IF/ID.RegisterRt)))
 * stall the pipeline
 * The only instruction that reads data memory is a load. Notice this algorithm uses the ID/EX and IF/ID registers, because this type 
 * of hazard is detected during the ID stage. 
 * How to stall? Instruction which contains the hazard is in ID stage. Another instruction has already been fetched in IF stage. SO, 
 * need to stall both of these instructions. Solution: prevent the PC register and the IF/ID pipeline register from changing. If those 
 * registers stay the same, in the next clock cycle the same instruction (containing hazard) will be decoded and the same instruction 
 * (after hazard) will be fetched.
 * http://inside.mines.edu/~crader/cs341/Chapter6.htm
 */
public class HazardDetectionUnit {
	
	public boolean isThereLWHazard() {
		return false;
	}

}