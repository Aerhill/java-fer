package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The command copies computer files from one directory to another. The
 * destination must be a directory.
 * 
 * @author Ante Spajic
 *
 */
public class CopyCommand extends AbstractCommand {

	/**
	 * Creates a new copy command object
	 */
	public CopyCommand() {
		super("copy", Arrays.asList("Copies file to another location."));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		Pattern pattern = Pattern.compile("\\s*((\"(.+)\")|(\\S+))\\s+((\"(.+)\")|(\\S+))\\s*");
		Matcher matcher = pattern.matcher(arguments);
		try {
			if (matcher.find()) {
				Path apath = Paths.get(matcher.group(3) == null ? matcher.group(4) : matcher.group(3));
				Path bpath = Paths.get(matcher.group(7) == null ? matcher.group(8) : matcher.group(7));
				if (Files.exists(bpath)) {
					env.writeln("Do you want to overwrite? Yes?");
					if (!env.readLine().toLowerCase().equals("yes")) {
						return ShellStatus.CONTINUE;
					}
				}
				try (InputStream inStream = new FileInputStream(apath.toFile());
						OutputStream outStream = new FileOutputStream(bpath.toFile())) {

					byte[] buffer = new byte[1024];
					int length;
					while ((length = inStream.read(buffer)) > 0) {
						outStream.write(buffer, 0, length);
					}
					env.writeln("File has been copied successfully!");
				}
			} else {
				throw new RuntimeException("Invalid copy command");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ShellStatus.CONTINUE;
	}
}
