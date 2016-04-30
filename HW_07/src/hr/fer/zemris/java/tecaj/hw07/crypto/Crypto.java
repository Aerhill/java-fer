package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This program allows the user to encrypt/decrypt given file using the AES
 * crypto algorithm and the 128-bit encryption key or calculate and check the
 * SHA-256 file digest. Encryption keys and initialization vectors are
 * byte-arrays each having 16 bytes. In the above example it is expected from
 * the user to provide these as hex-encoded texts.
 * 
 * @author Ante Spajic
 *
 */
public class Crypto {

	/**
	 * Entry point to a program.
	 * 
	 * @param args
	 *            Checksha, encrypt or decrypt with paths to files.
	 */
	public static void main(String[] args) {
		if(args.length != 2 || args.length != 3) {
			throw new IllegalArgumentException("Too few arguments");
		}
		try (Scanner sc = new Scanner(System.in)) {
			String command = args[0];
			switch (command.toLowerCase()) {
			case "checksha":
				System.out.println("Please provide expected sha-256 digest for hw07part2.pdf: ");
				checksha(args[1], sc.next().trim());
				break;
			case "encrypt":
				encrypt(args[1], args[2]);
				break;
			case "decrypt":
				decrypt(args[1], args[2]);
				break;
			default:
				throw new IllegalArgumentException("Unknown command");
			}
		}
	}

	/**
	 * Method that decrypts an input file and writes it to another file
	 * preserving the input file as it was given.
	 * 
	 * @param inputFile
	 *            File to be decrypted
	 * @param outputFile
	 *            File to save the decrypted file to.
	 */
	private static void decrypt(String inputFile, String outputFile) {
		Cipher cipher = getCipher(false);
		write(cipher, inputFile, outputFile);
		System.out.println("Decryption completed. Generated file " + outputFile + " based on file" + inputFile + ".");
	}

	/**
	 * Method that encrypts an input file and writes the encrypted version to an
	 * output file preserving the input file as it was given.
	 * 
	 * @param inputFile
	 *            File to be encrypted
	 * @param outputFile
	 *            File to save the encrypted file to.
	 */
	private static void encrypt(String inputFile, String outputFile) {
		Cipher cipher = getCipher(true);
		write(cipher, inputFile, outputFile);
		System.out.println("Decryption completed. Generated file " + outputFile + " based on file" + inputFile + ".");
	}

	/**
	 * Method that calculates and checks the SHA-256 file digest of a provided
	 * input file and compares it to an expected SHA-256 output.
	 * 
	 * @param inputfile
	 *            String to check the SHA-256 of
	 * @param expectation
	 *            Expected sha.
	 */
	private static void checksha(String inputfile, String expectation) {
		try (FileInputStream fis = new FileInputStream(inputfile);) {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] buffer = new byte[4096];
			int nread = 0;
			while ((nread = fis.read(buffer)) != -1) {
				md.update(buffer, 0, nread);
			}

			byte[] mdbytes = md.digest();
			String filesha = byteToHex(mdbytes);
			if (filesha != null) {
				if (filesha.equals(expectation)) {
					System.out.println("Digesting completed. Digest of hw07test.bin matches expected digest.");
				} else {
					System.out.println(
							"Digesting completed. Digest of hw07test.bin does not match the expected digest. Digest was: "
									+ filesha);
				}
			} else {
				System.out.println("Couldn't calculate sha");
			}
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the decrypted/encrypted file to destination path.
	 * 
	 * @param cipher
	 *            the cipher
	 * @param inputFile
	 *            Input file
	 * @param outputFile
	 *            Output file
	 */
	private static void write(Cipher cipher, String inputFile, String outputFile) {
		try (FileInputStream fis = new FileInputStream(inputFile);
				FileOutputStream out = new FileOutputStream(outputFile)) {
			byte[] buffer = new byte[4096];
			int nread = 0;
			while ((nread = fis.read(buffer)) != -1) {
				out.write(cipher.update(buffer, 0, nread));
			}
			out.write(cipher.doFinal());
		} catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Transforms a byte array to a hex string.
	 * 
	 * @param mdbytes
	 *            Byte array to transform
	 * @return Hex string representation of a byte array
	 */
	public static String byteToHex(byte[] mdbytes) {
		StringBuilder sb = new StringBuilder(mdbytes.length * 2);
		for (byte b : mdbytes) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	/**
	 * Transforms a hex string to byte array.
	 * 
	 * @param keyText
	 *            Hex string representation.
	 * @return Byte array representing give hex string.
	 */
	public static byte[] hexToByte(String keyText) {
		int len = keyText.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4)
					+ Character.digit(keyText.charAt(i + 1), 16));
		}
		return data;
	}

	/**
	 * Prepares a cipher object.
	 * 
	 * @param encrypt
	 *            True to set the cipher for encryption, false for decryption
	 * @return Prepared cipher
	 */
	private static Cipher getCipher(boolean encrypt) {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.print(">");
			String keyText = sc.nextLine().trim();
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			System.out.print(">");
			String ivText = sc.nextLine().trim();
			SecretKeySpec keySpec = new SecretKeySpec(hexToByte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(hexToByte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			return cipher;
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
