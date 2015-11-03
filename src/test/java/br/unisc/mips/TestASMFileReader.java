package br.unisc.mips;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import br.unisc.mips.exception.FileScannerException;

public class TestASMFileReader {

	private static ASMFileReader reader;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		reader = new ASMFileReader();
	}
	
	@Test(expected=FileScannerException.class)
	public void shouldThrowsExceptionOpeningAFileThatDontExists() throws FileScannerException {
		reader.getExecutableLines(new File("file-not-exists.txt"));
	}

	@Test
	public void shouldReadExecutableLinesInTheCode() throws FileScannerException {
		List<String> linhasDoArquivo = reader.getExecutableLines(new File("src/test/resources/exemplo1.m"));
	
		assertEquals(".text", linhasDoArquivo.get(0));
		assertEquals(9, linhasDoArquivo.size());
	}
		
}