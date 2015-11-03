package br.unisc.mips.tools;

public class StringUtils {

	public static String removeDoubleSpaces(String s) {
		while(s.contains("  ")) {
			s = s.replace("  ", " ");
		}
		return s;
	}
	
}