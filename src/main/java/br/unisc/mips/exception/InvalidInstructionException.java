package br.unisc.mips.exception;

public class InvalidInstructionException extends Exception {

	private static final long serialVersionUID = 562073573321589512L;
	
	public static final String INSTRUCTION_NOT_SUPPORTED = "The instruction %s isn't supported.";
	
	public InvalidInstructionException(String message) {
		super(message);
	}
	
}