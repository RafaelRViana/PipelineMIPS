package br.unisc.mips;

public class DataInformation {

	private DataType type;
	
	private String value;
	
	public DataInformation(DataType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public DataType getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
}