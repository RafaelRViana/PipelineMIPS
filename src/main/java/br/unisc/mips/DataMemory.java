package br.unisc.mips;

import java.util.Map;
import java.util.TreeMap;

public class DataMemory {

	private static DataMemory instance;

	private DataMemory() { }

	public static DataMemory getInstance() {
		if (instance == null)
			instance = new DataMemory();
		return instance;
	}
	
	private TreeMap<Integer, Integer> addresses = new TreeMap<>();
	
	public int read(int address) {
		if (addresses.containsKey(address)) {
			return addresses.get(address);
		}
		
		return 0;
	}
	
	public void write(int address, int data) {
		addresses.put(address, data);
	}
	
	public int getHighestAddress() {
		return addresses.lastKey();
	}
	
	public Map<Integer, Integer> getAddresses() {
		return addresses;
	}
	
}