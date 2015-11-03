package br.unisc.mips;

import br.unisc.mips.stages.StageEX_MEM;
import br.unisc.mips.stages.StageID_EX;
import br.unisc.mips.stages.StageMEM_WB;

/**
 * 1. EX Hazard:
 * if (EX/MEM.RegWrite
 * and (EX/MEM.RegisterRd != 0)
 * and (EX/MEM.RegisterRd = ID/EX.RegisterRs))
 * ForwardA = 10
 *
 * if (EX/MEM.RegWrite
 * and (EX/MEM.RegisterRd != 0)
 * and (EX/MEM.RegisterRd = ID/EX.RegisterRt))
 * ForwardB = 10
 *
 * 2. MEM hazard:
 * if (MEM/WB.RegWrite
 * and (MEM/WB.RegisterRd != 0)
 * and (EX/MEM.RegisterRd != ID/EX.RegisterRs)
 * and (MEM/WB.RegisterRd = ID/EX.RegisterRs))
 *
 * ForwardA = 01
 *
 * if (MEM/WB.RegWrite
 * and (MEM/WB.RegisterRd != 0)
 * and (EX/MEM.RegisterRd != ID/EX.RegisterRt)
 * and (MEM/WB.RegisterRd = ID/EX.RegisterRt))
 *
 * ForwardB = 01
 * 
 * http://inside.mines.edu/~crader/cs341/Chapter6.htm
 */
public class ForwardingUnit {

	//TODO Preciso de uma variavel para dizer para interface que a unidade esta ativa para dizer a interface
	
	public boolean isThereEXHazard(StageEX_MEM exMem, StageID_EX idEx) {
//		if(exMem.isRegWrite() && !exMem.getRegisterDestination().equals("zero") && (exMem.getRegisterDestination().equals(idEx.geti))) {
//			forwardA = 10; //10 em binario
//		}
//		
//		if(exMem.isRegWrite() && !exMem.getRegisterDestination().equals("zero") $$ (exMem.getRegisterDestination().equals(idex.get))) {
//			forwardB = 10; //10 em binario o que sao esses sinais de controle??
//		}
		
		return false;
	}
	
	public boolean isThereMEMHazard(StageMEM_WB memWb, StageID_EX idEx, StageEX_MEM exMem) {
//		if(memWb.isRegWrite() && memWb.getRegisterDestination().equals("zero") && (exMem.getRegisterDestination().equals())) {
//			forwardA = 01;
//		}
//		if(memWb.isRegWrite() && memWb.getRegisterDestination().equals("zero") && (exMem.getRegisterDestination().equals())) {
//			forwardB = 01;
//		}
		
		return false;
	}
	
}