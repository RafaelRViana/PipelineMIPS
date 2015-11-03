package br.unisc.mips.exception;

public class Jump extends RuntimeException {

	private static final long serialVersionUID = -5430755466255657177L;

	public Jump(String label) {
		this.label = label;
	}

	private String label;
	
	public String getLabel() {
		return label;
	}
	
}