package br.unisc.mips.exception;

public class FileScannerException extends Exception {

	private static final long serialVersionUID = 4687814525717342578L;

	public static final String ERROR_OPENING_FILE = "Error opening the file";
	public static final String ERROR_CLOSING_FILE = "Error closing the file";
	
	public FileScannerException(String message, Throwable t) {
		super(String.format("%s - Cause: %s", message, t != null ? t.getLocalizedMessage() : "Cause didn't localized."), t);
	}
	
}