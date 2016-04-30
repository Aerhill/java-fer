package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * 
 * Dumps data in hexadecimal format.
 * <p>
 * Provides a single function to take an array of bytes and display it in
 * hexadecimal form.
 * <p>
 *
 * @author Ante Spajic
 *
 */
public class HexdumpCommand extends AbstractCommand {

	/**
	 * Creates a new hexdump command.
	 */
	public HexdumpCommand() {
		super("hexdump", Arrays.asList("Produces a hexdump of a file"));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			Path path = Paths.get(arguments.trim());
			if (Files.isDirectory(path)) {
				throw new RuntimeException("Must be a file");
			}

			try (InputStream fis = new BufferedInputStream(new FileInputStream(path.toString()))) {
				byte[] buffer = new byte[4096];
				int n;
				int display_offset = 0;
				while ((n = fis.read(buffer)) != -1) {
					dump(buffer, env, n, display_offset);
					display_offset += n;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Lol");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Dump an array of bytes to an OutputStream. The output is formatted for
	 * human inspection, with a hexadecimal offset followed by the hexadecimal
	 * values of the next 16 bytes of data and the printable ASCII characters
	 * (if any) that those bytes represent printed per each line of output.
	 * <p>
	 * The offset argument specifies the start offset of the data array within a
	 * larger entity like a file or an incoming stream. For example, if the data
	 * array contains the third kibibyte of a file, then the offset argument
	 * should be set to 2048. The offset value printed at the beginning of each
	 * line indicates where in that larger entity the first byte on that line is
	 * located.
	 * <p>
	 * All bytes between the given index (inclusive) and the end of the data
	 * array are dumped.
	 *
	 * @param data
	 *            the byte array to be dumped
	 * @param env
	 *            the environment to which the data is to be written
	 * @param length
	 *            initial index into the byte array
	 * @param display_offset
	 *            position in memory of the given file
	 *
	 * @throws IOException
	 *             is thrown if anything goes wrong writing the data to stream
	 */
	public static void dump(byte[] data, Environment env, int length, long display_offset) throws IOException {

		StringBuilder buffer = new StringBuilder();

		for (int j = 0; j < length; j += 16) {
			int chars_read = length - j;

			if (chars_read > 16) {
				chars_read = 16;
			}
			dump(buffer, display_offset).append(' ');
			for (int k = 0; k < 16; k++) {
				if (k == 8) {
					buffer.append('|');
				}
				if (k < chars_read) {
					dump(buffer, data[k + j]);
				} else {
					buffer.append("  ");
				}
				if (k != 7) {
					buffer.append(' ');
				}
			}
			buffer.append('|');
			for (int k = 0; k < chars_read; k++) {
				if (data[k + j] >= ' ' && data[k + j] < 127) {
					buffer.append((char) data[k + j]);
				} else {
					buffer.append('.');
				}
			}
			buffer.append("\n");
			env.write(buffer.toString());
			buffer.setLength(0);
			display_offset += chars_read;
		}
	}

	private static final char[] hexcodes = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };
	private static final int[] shifts = { 28, 24, 20, 16, 12, 8, 4, 0 };

	/**
	 * Dump a long value into a StringBuilder.
	 *
	 * @param buffer
	 *            the StringBuilder to dump the value in
	 * @param value
	 *            the long value to be dumped
	 * @return StringBuilder containing the dumped value.
	 */
	private static StringBuilder dump(StringBuilder buffer, long value) {
		for (int j = 0; j < 8; j++) {
			buffer.append(hexcodes[(int) (value >> shifts[j]) & 15]);
		}
		return buffer;
	}

	/**
	 * Dump a byte value into a StringBuilder.
	 *
	 * @param buffer
	 *            the StringBuilder to dump the value in
	 * @param value
	 *            the byte value to be dumped
	 * @return StringBuilder containing the dumped value.
	 */
	private static StringBuilder dump(StringBuilder buffer, byte value) {
		for (int j = 0; j < 2; j++) {
			buffer.append(hexcodes[value >> shifts[j + 6] & 15]);
		}
		return buffer;
	}

}
