package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The program cat is a standard Unix utility that reads files sequentially,
 * writing them to standard output. The name is derived from its function to
 * catenate/concatenate and list files.
 * 
 * @author Ante Spajic
 *
 */
public class CatCommand extends AbstractCommand {

	/**
	 * Creates a new cat
	 */
	public CatCommand() {
		super("cat", Arrays.asList("This command opens given file and writes its content to console."));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Pattern pattern = Pattern.compile("\\s*((\"(.+)\")|(\\S+))(\\s+([-a-zA-Z0-9]+))?\\s*");
		Matcher matcher = pattern.matcher(arguments);
		try {
			if (matcher.find()) {
				Path file = null;
				Charset charset = Charset.defaultCharset();
				if (matcher.group(3) != null) {
					file = Paths.get(matcher.group(3));
				} else {
					file = Paths.get(matcher.group(4));
				}

				if (matcher.group(6) != null) {
					try {
						charset = Charset.forName(matcher.group(6));
					} catch (Exception e) {
						env.writeln("Unsupported charset");
						return ShellStatus.CONTINUE;
					}
				}

				BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(file.toFile()), charset));
				String line;
				while ((line = br.readLine()) != null) {
					env.writeln(line);
				}
				br.close();

			} else {
				env.writeln("Illegal cat command");
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not write to shell");
		}

		return ShellStatus.CONTINUE;
	}

}
