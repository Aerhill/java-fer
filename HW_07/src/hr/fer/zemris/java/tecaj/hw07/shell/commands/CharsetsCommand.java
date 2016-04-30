package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Lists names of supported charsets for your Java platform.
 * 
 * @author Ante Spajic
 *
 */
public class CharsetsCommand extends AbstractCommand {

	/**
	 * creates a new charsets command.
	 */
	public CharsetsCommand() {
		super("charsets", Arrays.asList("lists names of supported charsets for your Java platform"));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Charset.availableCharsets().keySet().forEach(c -> {
			try {
				env.writeln(c);
			} catch (IOException e) {
				throw new RuntimeException("Can't write to shell");
			}
		});
		return ShellStatus.CONTINUE;
	}

}
