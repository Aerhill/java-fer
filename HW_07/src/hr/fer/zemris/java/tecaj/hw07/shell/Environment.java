package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;

/**
 * This interface is a representation of an environment that stands as a layer
 * between operating system and shell which enables the user to use certain
 * commands and execute them.
 * 
 * @author Ante Spajic
 *
 */
public interface Environment {

	/**
	 * Reads a line from a stdin.
	 * 
	 * @return String read from an input.
	 * @throws IOException
	 */
	String readLine() throws IOException;

	/**
	 * Writes a string to a stdout.
	 * 
	 * @param text
	 *            text to be written.
	 * @throws IOException
	 */
	void write(String text) throws IOException;

	/**
	 * Writes a string to a stdout and starts a new line.
	 * 
	 * @param text
	 * @throws IOException
	 */
	void writeln(String text) throws IOException;

	/**
	 * Returns a list of commands that can be executed in this environment.
	 * 
	 * @return List of commands available in this environment.
	 */
	Iterable<ShellCommand> commands();

	/**
	 * Returns a multiple line symbol that is written to an environment when the
	 * user is writing in multiple lines.
	 * 
	 * @return Symbol that lets user know that he's writing in mutliple lines.
	 */
	Character getMultilineSymbol();

	/**
	 * Sets the multiple line symbol.
	 * 
	 * @param symbol
	 *            New symbol for multiple line.
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Symbol that is in the beginning of a new prompt line.
	 * 
	 * @return New prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * Sets a new prompt symbol.
	 * 
	 * @param symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Symbol that lets the user write in more than one line.
	 * 
	 * @return Symbol that allows the user to write in more than one line.
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets the new symbol that will let the user to write in more than one
	 * line.
	 * 
	 * @param symbol
	 *            Sets a new symbol for morelines.
	 */
	void setMorelinesSymbol(Character symbol);
}
