package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;

/**
 * This is a wrapper for a shell command that contains the {@link ShellCommand}
 * name and description. Implementation is in the classes that extend this one.
 * 
 * @author Ante Spajic
 *
 */
public abstract class AbstractCommand implements ShellCommand {

	private String name;
	private List<String> description;

	/**
	 * Creates a new abstract command with provided name and command description
	 * lines.
	 * 
	 * @param name
	 *            Name of the command.
	 * @param description
	 *            Description of the command.
	 */
	protected AbstractCommand(String name, List<String> description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
