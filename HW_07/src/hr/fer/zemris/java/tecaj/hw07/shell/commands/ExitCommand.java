package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command used to finish the work in the environment.
 * 
 * @author Ante Spajic
 *
 */
public class ExitCommand extends AbstractCommand implements ShellCommand {

	/**
	 * Creates a new exit command
	 */
	public ExitCommand() {
		super("exit", Arrays.asList("Exits"));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

}
