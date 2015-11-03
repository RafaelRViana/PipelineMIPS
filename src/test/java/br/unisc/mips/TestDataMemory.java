package br.unisc.mips;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestDataMemory {

	@Test
	public void shouldAddDataToMemory() {
		DataMemory memory = DataMemory.getInstance();
		memory.write(1, 10);
		memory.write(10, 1);
		memory.write(9, 5);
		
//		for(Entry<Integer, Integer> data : memory.getAddresses().entrySet() ) {
//			System.out.println(data.getKey());
//			System.out.println(data.getValue());
//		}
		
		assertEquals(10, memory.read(1));
		assertEquals(1, memory.read(10));
		assertEquals(5, memory.read(9));
	}
	
}