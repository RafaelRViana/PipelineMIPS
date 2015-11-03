package br.unisc.mips;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.unisc.mips.exception.FileScannerException;

public class ASMFileReader {
	
	/**
	 * 
	 * Function to read executable lines. I don't use comments and blank lines.
	 * 
	 * @throws FileScannerException 
	 * 
	 */
	public List<String> getExecutableLines(File file) throws FileScannerException {
		BufferedReader br = null;
		List<String> lines = new ArrayList<String>();
		
		try {
			String line;
			br = new BufferedReader(new FileReader(file));
 
			while ((line = br.readLine()) != null) {
				
				//I can't analyze null line
				if(line != null) {
				
					//I need to exclude comments. They don't need to be analyzed.
					line = removeComments(line);
					
					//I don't need empty lines
					if(!("".equals(line))) {
						lines.add(line);
					}
				}
				
			}
			
		} catch (IOException e) {
			throw new FileScannerException(FileScannerException.ERROR_OPENING_FILE, e);
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				throw new FileScannerException(FileScannerException.ERROR_CLOSING_FILE, ex);
			}
		}
		
		return lines;
	}
	
	private String removeComments(String line) {
		if( line.contains("#")) {
			line = line.substring(0, line.indexOf("#"));
		}
		
		return line.trim();
	}
	
}
