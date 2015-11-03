package br.unisc.mips.exception;

public class UnreachableLabelException extends Exception {

	private static final long serialVersionUID = -475353747181339966L;

	public static final String UNREACHABLE_LABEL = "The label %s was not found.";
	
	public UnreachableLabelException(String message) {
		super(message);
	}

}
