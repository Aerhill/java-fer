package hr.fer.zemris.java.simplecomp;

import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Klasa koja sadrži statičke metode za provjeravanje registara preko njihovih
 * deskriptora. Pomoću uputa iz razreda iz {@link InstructionArgument} određuje
 * se radi li se o indirektnom adresiranju (ako je 24 bit upaljen u
 * deskriptoru). Metoda {@link #getRegisterIndex(int)} koja dohvaća indeks
 * registra iz njegovog deskriptora(zadnji bajt unutar deskriptora). Metoda
 * {@link #getRegisterOffset(int)} koja vraća pomak ako se radi o indirektnom
 * adresiranju.
 * 
 * @author Ante Spajic
 *
 */
public class RegisterUtil {

	/**
	 * Metoda vraća indeks registra iz predanog deskriptora.
	 * 
	 * @param registerDescriptor
	 * @return index registra pročitan iz descriptora
	 */
	public static int getRegisterIndex(int registerDescriptor) {
		return (registerDescriptor & 0xFF);
	}

	/**
	 * Metoda provjerava na osnovu deskriptora provjerava radi li se o
	 * indirektnom adresiranju.
	 * 
	 * @param registerDescriptor
	 *            deskriptor iz kojeg se provjerava radi li se o indirektnom
	 *            adresiranju
	 * @return <code>True</code> ako je predani deskriptor indirektna adresa,
	 *         false inače.
	 */
	public static boolean isIndirect(int registerDescriptor) {
		return (registerDescriptor & 0x1000000) != 0;
	}

	/**
	 * Metoda koja iz predanog deskriptora vadi pomak koji je potrebno
	 * koristiti.
	 * 
	 * @param registerDescriptor
	 *            deskriptor iz kojega se vadi pomak
	 * @return vraća offset registra pročitan iz descriptora
	 */
	public static int getRegisterOffset(int registerDescriptor) {
		return (short) (registerDescriptor >> 8 & 0xFFFF);
	}
}
