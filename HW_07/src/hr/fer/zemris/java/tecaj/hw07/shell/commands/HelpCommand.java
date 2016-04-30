package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.Arrays;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Help command with no arguments lists all available commands in environment or
 * if an argument is provided, description of provided commands is written.
 * 
 * @author Ante Spajic
 *
 */
public class HelpCommand extends AbstractCommand {

	/**
	 * Creates a new help command
	 */
	public HelpCommand() {
		super("help", Arrays.asList("Helps you"));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] argumentss = arguments.split("\\s+");
		try {
			if (arguments == null || arguments.trim().isEmpty()) {
				for (ShellCommand sc : env.commands()) {
					writeLOL(env, sc);
				}
			} else {
				for (ShellCommand sc : env.commands()) {
					for (String string : argumentss) {
						if (sc.getCommandName().equals(string)) {
							writeLOL(env, sc);
						}
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Couldn't write to env");
		}
		return ShellStatus.CONTINUE;
	}

	private void writeLOL(Environment env, ShellCommand sc) throws IOException {
		env.writeln(sc.getCommandName() + "\t");
		sc.getCommandDescription().forEach(s -> {
			try {
				env.writeln(s);
			} catch (Exception e) {
			}
		});
	}
}
