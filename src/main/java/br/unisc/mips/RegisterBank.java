package br.unisc.mips;

import net.vidageek.mirror.dsl.Mirror;

public class RegisterBank {

	private static RegisterBank instance;

	private RegisterBank() { }

	public static RegisterBank getInstance() {
		if (instance == null)
			instance = new RegisterBank();
		return instance;
	}
	   
	/**
	 * constant 0
	 */
	private int zero = 0;

	/**
	 * assembler temporary
	 */
	private int at = 0;
	
	/**
	 * value for function returns and expression evaluation
	 */
	private int v0 = 0;
	
	/**
	 * value for function returns and expression evaluation
	 */
	private int v1 = 0;
	
	/**
	 * function argument
	 */
	private int a0 = 0;
	
	/**
	 * function argument
	 */
	private int a1 = 0;
	
	/**
	 * function argument
	 */
	private int a2 = 0;
	
	/**
	 * function argument
	 */
	private int a3 = 0;
	
	/**
	 * temporary
	 */
	private int t0 = 0;
	
	/**
	 * temporary
	 */
	private int t1 = 0;
	
	/**
	 * temporary
	 */
	private int t2 = 0;
	
	/**
	 * temporary
	 */
	private int t3 = 0;
	
	/**
	 * temporary
	 */
	private int t4 = 0;
	
	/**
	 * temporary
	 */
	private int t5 = 0;
	
	/**
	 * temporary
	 */
	private int t6 = 0;
	
	/**
	 * temporary
	 */
	private int t7 = 0;
	
	/**
	 * temporary
	 */
	private int s0 = 0;
	
	/**
	 * temporary
	 */
	private int s1 = 0;
	
	
	/**
	 * temporary
	 */
	private int s2 = 0;
	
	/**
	 * temporary
	 */
	private int s3 = 0;
	
	/**
	 * temporary
	 */
	private int s4 = 0;
	
	/**
	 * temporary
	 */
	private int s5 = 0;
	
	/**
	 * temporary
	 */
	private int s6 = 0;
	
	/**
	 * temporary
	 */
	private int s7 = 0;
	
	/**
	 * reserved for OS kernel
	 */
	private int k0 = 0;
	
	/**
	 * reserved for OS kernel
	 */
	private int k1 = 0;
	
	/**
	 * global pointer
	 */
	private int gp = 0;
	
	/**
	 * stack pointer
	 */
	private int sp = 0;
	
	/**
	 * frame pointer
	 */
	private int fp = 0;
	
	/**
	 * return address
	 */
	private int ra = 0;

	public int getValue(String registerName) {
		return (Integer) new Mirror().on(this).get().field(registerName.replace("$", ""));
	}
	
	public void setValue(String registerName, Integer value) {
		new Mirror().on(this).set().field(registerName.replace("$", "")).withValue(value);	
	}
	
}