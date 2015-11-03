package br.unisc.mips;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unisc.mips.exception.InvalidInstructionException;
import br.unisc.mips.instructions.Instruction;
import br.unisc.mips.instructions.parser.InstructionParser;

public class TextSegment {

	private List<Instruction> instructions;
	private Map<String, Integer> labels;
	
	public void create(List<String> stringInstructions) throws InvalidInstructionException {
		InstructionParser parser = new InstructionParser();
		
		instructions = new ArrayList<Instruction>();
		labels = new HashMap<String, Integer>();
		
		for(int index = 0; index < stringInstructions.size(); index++) {
			String s = stringInstructions.get(index);
			
			//Verifico se contem uma label na instru‹o
			if(s.contains(":")) {
				String[] temp = s.split("\\:");
				labels.put(temp[0].trim(), index);
				
				//Se n‹o tiver instru‹o depois da label, ela n‹o ter‡ a posi‹o 1 do vetor
				if(temp.length > 1) {
					s = temp[1].trim(); //o novo valor da instrucao para ser processada n‹o ter‡ a label
				} else {
					s = "";  //se n‹o tiver deixo vazio, que isso ir‡ virar um NOP no parser
				}
			}
			
			instructions.add(parser.parse(s));
		}
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}
	
	public Instruction getInstruction(int index) {
		return instructions.get(index);
	}
	
	public Integer getLabelLine(String label) {
		return labels.get(label);
	}
	
	public Map<String, Integer> getLabels() {
		return labels;
	}
	
}