package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command ls takes a single argument – directory – and writes a directory
 * listing"
 * 
 * @author Ante Spajic
 *
 */
public class LsCommand extends AbstractCommand {

	/**
	 * Creates a new ls command
	 */
	public LsCommand() {
		super("ls", Arrays.asList("Command ls takes a single argument – directory – and writes a directory listing"));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Path path = Paths.get(arguments.trim());
		if (!Files.isDirectory(path)) {
			throw new RuntimeException("Must be a directory");
		}
		try {
			Files.list(path).forEach(file -> {
				try {
					env.writeln(getPrettyPrint(file));
				} catch (IOException e) {
					throw new RuntimeException("Couldn't write to shell");
				}
			});
		} catch (IOException e) {
			throw new RuntimeException("Couldn't write to shell");
		}

		return ShellStatus.CONTINUE;
	}

	private String getPrettyPrint(Path file) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BasicFileAttributeView faView = Files.getFileAttributeView(file, BasicFileAttributeView.class,
					LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attributes = faView.readAttributes();
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

			StringBuilder sb = new StringBuilder();
			sb.append(Files.isDirectory(file) ? "d" : "-");
			sb.append(Files.isReadable(file) ? "r" : "-");
			sb.append(Files.isWritable(file) ? "w" : "-");
			sb.append(Files.isExecutable(file) ? "x" : "-");
			sb.append(" ");
			sb.append(String.format("%10d", Files.size(file)));
			sb.append(" ");
			sb.append(formattedDateTime);
			sb.append(" " + file.getFileName());
			return sb.toString();

		} catch (IOException e) {
			throw new RuntimeException("Could not read file attributes");
		}
	}

}
