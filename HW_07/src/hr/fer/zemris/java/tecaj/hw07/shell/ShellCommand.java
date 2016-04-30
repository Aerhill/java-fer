package hr.fer.zemris.java.tecaj.hw07.shell;

import java.util.List;

/**
 * This interface stands for a command that can be executed in
 * {@link Environment}.
 * 
 * @author Ante Spajic
 *
 */
public interface ShellCommand {

	/**
	 * 
	 * @param env
	 *            Environment to execute the command in.
	 * @param arguments
	 *            Command arguments.
	 * @return ShellStatus.Continue or ShellStatus.Terminate if command is exit
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Getter for this ShellCommand's name.
	 * 
	 * @return Returns this commands name
	 */
	String getCommandName();

	/**
	 * Returns the short description for this command.
	 * 
	 * @return List of string describing this command.
	 */
	List<String> getCommandDescription();
}
