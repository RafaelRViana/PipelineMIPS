package br.unisc.mips;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unisc.mips.tools.StringUtils;

public class DataSegment {

	private Map<String, DataInformation> data;
	
	public void create(List<String> dataInstructions) {
		data = new HashMap<String, DataInformation>();
		
		for(String dataInstruction : dataInstructions) {
			
			String name = dataInstruction.substring(0, dataInstruction.indexOf(":"));
			String value = dataInstruction.substring(dataInstruction.indexOf(":") + 1).trim();
			
			//We need to prevent possible double spaces in the lines. eg: .word   0x0. It will break split.
			value = StringUtils.removeDoubleSpaces(value);
			
			String[] parameters = value.split(" ");
			
			DataType type = DataType.valueOf(parameters[0].replace(".", "").toUpperCase());
			data.put(name, new DataInformation(type, parameters[1]));
		}
	}
	
	public DataInformation get(String key) {
		return data.get(key);
	}
	
}