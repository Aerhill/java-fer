package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The mkdir command takes a single argument: directory name, and creates the
 * appropriate directory structure.
 * 
 * @author Ante Spajic
 *
 */
public class MkdirCommand extends AbstractCommand implements ShellCommand {

	/**
	 * Creates a new mkdir command
	 */
	public MkdirCommand() {
		super("mkdir", Arrays.asList("MKDIR creates any intermediate directories in the path, if needed."));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			Pattern pattern = Pattern.compile("[\\*?>:<|]+");
			Matcher matcher = pattern.matcher(arguments);
			if (!matcher.find()) {
				Path path = Paths.get(arguments.replaceAll("\"", ""));
				Files.createDirectories(path);
			} else {
				env.writeln("Invalid filename");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ShellStatus.CONTINUE;
	}

}
