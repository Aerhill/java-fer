package hr.fer.zemris.java.tecaj.hw07.cryptotest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw07.crypto.Crypto;
//NOTE : all relevant tests of digesting and encrypting are given in task
// ALSO ALL OF THE CODE WAS WRITTEN IN A MIDTERMS WEEK WHEN TIME IS OF THE ESSENCE
public class CryptoTest {

	@Test(expected = IllegalArgumentException.class)
	public void testNoArguments() {
		String[] args = { "lala" };
		Crypto.main(args);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongArguments() {
		String[] args = new String[0];
		Crypto.main(args);
	}

	@Test
	public void hexStringToByte() {
		String lala = "lala";
		byte[] data = Crypto.hexToByte(lala);
		assertEquals(true, data[0] == -6);
		assertEquals(true, data[1] == -6);
	}

	@Test
	public void hexStringtoByte2() {
		String lala = "33";
		byte[] data = Crypto.hexToByte(lala);
		assertEquals(true, data[0] == 51);
	}

	@Test
	public void hexStringtoByte3() {
		String lala = "50";
		byte[] data = Crypto.hexToByte(lala);
		assertEquals(true, data[0] == 80);
	}
}