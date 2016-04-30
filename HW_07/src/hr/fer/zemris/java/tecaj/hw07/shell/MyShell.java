package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.CatCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CopyCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ExitCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HelpCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.LsCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.MkdirCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.SymbolCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.TreeCommand;

/**
 * Program that acts as a simple shell that can execute following built-in
 * commands: charsets, cat, ls, tree, copy, mkdir, hexdump.
 */
public class MyShell {
	/**
	 * Shell commands maps
	 */
	private static Map<String, ShellCommand> commands;

	static {
		commands = new HashMap<>();
		ShellCommand[] cc = { 
				new CharsetsCommand(),
				new CatCommand(), 
				new ExitCommand(), 
				new LsCommand(), 
				new TreeCommand(),
				new HexdumpCommand(), 
				new MkdirCommand(), 
				new HelpCommand(), 
				new SymbolCommand(), 
				new CopyCommand() };
		for (ShellCommand c : cc) {
			commands.put(c.getCommandName(), c);
		}
	}

	/**
	 * Implementation of a Environment class.
	 */
	public static class EnvironmentImpl implements Environment {

		private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		private Character promptSymbol = '>';
		private Character multilineSymbol = '|';
		private Character morelinesSymbol = '\\';

		@Override
		public String readLine() throws IOException {
			String ret = reader.readLine();
			return ret;
		}

		@Override
		public void write(String input) throws IOException {
			writer.write(input);
			writer.flush();
		}

		@Override
		public void writeln(String line) throws IOException {
			writer.write(line);
			writer.newLine();
			writer.flush();

		}

		@Override
		public Iterable<ShellCommand> commands() {
			return Collections.unmodifiableCollection(commands.values());
		}

		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			this.multilineSymbol = symbol;
		}

		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			this.promptSymbol = symbol;
		}

		@Override
		public Character getMorelinesSymbol() {
			return morelinesSymbol;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			this.morelinesSymbol = symbol;
		}

	}

	public static Environment environment = new EnvironmentImpl();

	/**
	 * Entry point to a program.
	 *
	 * @param args
	 *            unused command line arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		environment.writeln("Welcome to MyShell v 1.0");
		while (true) {
			environment.write(environment.getPromptSymbol().toString() + " ");
			String line = environment.readLine();
			while (line.endsWith(environment.getMorelinesSymbol().toString())) {
				environment.write(environment.getMultilineSymbol().toString() + " ");
				line = line.substring(0, line.length() - 1) + environment.readLine();
			}
			String cmd = "";
			String arg = "";
			if (line.contains(" ")) {
				String[] split = line.split(" ", 2);
				cmd = split[0];
				arg = split[1];
			} else {
				cmd = line;
			}
			ShellCommand shellCommand = commands.get(cmd.toLowerCase());
			if (shellCommand == null) {
				environment.writeln("Unknown command!");
				continue;
			}
			try {
				if (shellCommand.executeCommand(environment, arg).equals(ShellStatus.TERMINATE)) {
					break;
				}
			} catch (RuntimeException e) {
				System.err.println(e.getMessage());
			}
		}
		environment.writeln("Thank you for using this shell. Goodbye!");
	}
}
