package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The tree command expects a single argument: directory name and prints a tree
 * (each directory level shifts output two charatcers to the right). tree lists
 * the contents of directories in a tree-like format. It's a really neat and
 * useful program you can use at the command line to view the structure of your
 * file system.
 * 
 * @author Ante Spajic
 *
 */
public class TreeCommand extends AbstractCommand {

	/**
	 * Creates a new tree command
	 */
	public TreeCommand() {
		super("tree", Arrays.asList(
				"The tree command expects a single argument: directory name and prints a tree (each directory level shifts output two charatcers to the right)."));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			Path path = Paths.get(arguments);
			if (!Files.isDirectory(path)) {
				throw new RuntimeException("Argument must be a directory");
			}
			Files.walkFileTree(Paths.get(arguments), new TreeVisitor(env));
		} catch (IOException e) {
			throw new RuntimeException("Couldn't write to environment");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * SimpleFileVisitor that traverses file tree and writes tree-like
	 * structure.
	 * 
	 * @author Ante Spajic
	 *
	 */
	private static class TreeVisitor implements FileVisitor<Path> {
		private int level;
		private Environment env;

		TreeVisitor(Environment env) {
			this.env = env;
		}

		private void print(Path file) throws IOException {
			if (level == 0) {
				env.writeln(file.normalize().toAbsolutePath().toString());
			} else {
				env.writeln(String.format("%" + (2 * level) + "s%s", "", file.getFileName()));
			}
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			print(dir);
			level++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			print(file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}
	}

}
