package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.Arrays;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command responsible for environment symbol variables that can be changed or
 * just written to user.
 * 
 * @author Ante Spajic
 *
 */
public class SymbolCommand extends AbstractCommand {

	/**
	 * Creates a new symbol command
	 */
	public SymbolCommand() {
		super("symbol", Arrays.asList("Gets or sets symbol"));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.split("\\s+");
		if (args.length <= 0 || args.length >= 3) {
			throw new RuntimeException("Invalid arguments for symbol command.");
		}
		try {
			switch (args[0]) {
			case "PROMPT":
				if (args.length == 1) {
					env.writeln("Symbol for PROMPT is " + env.getPromptSymbol() + ".");
				} else if (args.length == 2 && args[1].length() == 1) {
					Character oldChar = env.getPromptSymbol();
					Character newChar = args[1].charAt(0);
					env.setPromptSymbol(newChar);
					env.writeln("Symbol for PROMPT changed from " + oldChar + " to " + newChar + ".");
				} else {
					throw new RuntimeException("Invalid arguments for symbol command.");
				}
				break;
			case "MORELINES":
				if (args.length == 1) {
					env.writeln("Symbol for MORELINES is " + env.getMorelinesSymbol() + ".");
				} else if (args.length == 2 && args[1].length() == 1) {
					Character oldChar = env.getMorelinesSymbol();
					Character newChar = args[1].charAt(0);
					env.setMorelinesSymbol(newChar);
					env.writeln("Symbol for MORELINES changed from " + oldChar + " to " + newChar + ".");
				} else {
					throw new RuntimeException("Invalid arguments for symbol command.");
				}
				break;
			case "MULTILINE":
				if (args.length == 1) {
					env.writeln("Symbol for MULTILINE is " + env.getMultilineSymbol() + ".");
				} else if (args.length == 2 && args[1].length() == 1) {
					Character oldChar = env.getMultilineSymbol();
					Character newChar = args[1].charAt(0);
					env.setMultilineSymbol(newChar);
					env.writeln("Symbol for MULTILINE changed from " + oldChar + " to " + newChar + ".");
				} else {
					throw new RuntimeException("Invalid arguments for symbol command.");
				}
				break;
			default:
				throw new RuntimeException("Invalid symbol identificator.");
			}
		} catch (IOException exception) {
			throw new RuntimeException("Could not write to the environment");
		}
		return ShellStatus.CONTINUE;
	}

}
