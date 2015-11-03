package br.unisc.mips;

/**
 * Data Segment Types:
 * 
 * .ascii str
 * This stores str in memory, but without a null terminator.
 *
 * .asciiz str
 * This stores str in memory, but with a null terminator. The "z" refers to zero, which is the ASCII code for the null character. This is how C-style strings are stored.
 *
 * .byte b1, ..., bn
 * Store n bytes contiguously in memory (you get to pick n). I'll assume the values b1,...,bn can be written in either in base 10 or in hex. I'll also assume commas are needed to separate the values. Finally, I assume that the values can be written on more than one line.
 *
 * .halfword h1, ..., hn
 * Store n 16-bit halfwords contiguously in memory (you get to pick n). I'll assume the values h1,...,hn can be written in either in base 10 or in hex. I'll also assume commas are needed to separate the values. I assume that the values can be written on more than one line. Finally, I assume the halfwords are half word aligned in memory, i.e., initial byte stored at addresses divisible by 2.
 *
 * .word w1, ..., wn
 * Store n 32-bit words contiguously in memory (you get to pick n). I'll assume the values w1,...,wn can be written in either in base 10 or in hex. I'll also assume commas are needed to separate the values. I assume that the values can be written on more than one line. Finally, I assume the words are word-aligned in memory, i.e., initial byte stored at addresses divisible by 4.
 *
 * .space numBytes
 * Reserves numBytes of space in memory.
 * 
 */
public enum DataType {

	ASCII, ASCIIZ, BYTE, HALFWORD, WORD, SPACE;
	
}