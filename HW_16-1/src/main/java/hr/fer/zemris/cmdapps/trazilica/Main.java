package hr.fer.zemris.cmdapps.trazilica;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents simple search engine that receives user queries through
 * command line. Search engine performs search through files present in root
 * directory whose absolute path is given as one and only one command line
 * argument. Top 10 search results are displayed in order of similarity to asked
 * query, along with similarity in form of real number in range [0,1], and
 * absolute path to file. Search engine can also display files mentioned in
 * result list and repeat previously asked query. Keywords are as following:
 * 
 * <ul>
 * <li>query - searches through available files for similarity to query</li>
 * <li>type - displays file mentioned in result list, receives one argument -
 * index of mentioned result file</li>
 * <li>results - repeats previously asked query</li>
 * <li>exit - terminates current search engine session</li>
 * </ul>
 * 
 * @author Ante Spajić
 */
public class Main {

	/** Relative path to file which contains stopping words for vocabulary. */
	public static final String RELATIVE_PATH_TO_STOPPING_WORDS = "./files/hrvatski_stoprijeci.txt";
	/** Absolute path to directory which contains files that can be searched by search engine.  */
	private static Path rootDirectory;
	/** Instance of {@link Vocabulary} used by this search engine. */
	private static Vocabulary vocabulary;
	/** Cache of previously returned results. */
	private static List<VectorUtil.QueryResult> results;

	/**
	 * Entry point of a program.
	 * 
	 * @param args absolute path to directory which contains files that can be searched by search engine
	 */
	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			System.err.println("One command line argument is expected - absolute path to directory with search files.");
			System.err.println("Terminating program.");
			System.exit(1);
		}

		vocabulary = VocabularyBuilder.buildVocabulary(args[0]);
		rootDirectory = Paths.get(args[0]);

		System.out.println("Welcome to simple search engine. Please enter your query here:");
		Scanner sc = new Scanner(System.in);
		while (true) {

			System.out.print("Enter command > ");
			String line = sc.nextLine();

			if (line.equals("exit")) {
				break;
			} else if (line.startsWith("query")) {
				line = line.substring("query".length()).trim();
				executeQuery(line);
			} else if (line.startsWith("type")) {
				line = line.substring("type".length()).trim();
				displayFile(line);
			} else if (line.equals("results")) {
				line = line.substring("results".length());
				displayResults();				
			} else {
				System.err.println("Unknown command " + line);
			}
		}
		sc.close();
		System.out.println("Thank you for using this simple search engine. Have a nice day.");
	}

	/**
	 * Searches through files present in root directory. Top 10 search results
	 * are displayed in order of similarity to asked query, along with
	 * similarity in form of real number in range [0,1], and absolute path to
	 * file.
	 * 
	 * @param query user's query
	 */
	private static void executeQuery(String query) {
		List<String> keywords = DocumentParser.parseText(query, vocabulary.getStoppingWords());

		DocumentNode dn = new DocumentNode(null, keywords);
		System.out.println("Query is: " + dn);
		dn.calculateTfIdfVector(vocabulary);

		results = VectorUtil.performComputation(dn, vocabulary.getDocuments());
		displayResults();
	}

	/**
	 * Displays file mentioned in query result list. File is specified by index
	 * associated with it in result list.
	 * 
	 * @param query users query
	 */
	private static void displayFile(String query) {
		int index = 0;
		try {
			index = Integer.parseInt(query);
		} catch (NumberFormatException e) {
			System.err.println("Value passed is not a valid index number.");
			return;
		}

		if (index < 0 || index > 9) {
			System.err.println("Index out of range.");
			return;
		}

		if (results == null) {
			System.err.println("No query was executed - no file can be shown.");
			return;
		}

		VectorUtil.QueryResult result = null;
		try {
			result = results.get(index);
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Index passed is out of range.");
			return;
		}

		Path filePath = Paths
				.get(rootDirectory.toString() + File.separatorChar + result.getDocument().getFileName());
		try {
			List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
			String headline = rootDirectory.toString() + File.separatorChar + result.getDocument().getFileName();
			for (int i = 0, j = headline.length(); i < j; i++) System.out.print("-");
			System.out.print("\n" + headline + "\n");
			for (int i = 0, j = headline.length(); i < j; i++) System.out.print("-");
			lines.forEach(System.out::println);
		} catch (IOException e) {
			System.err.println("Exception occurred during file retrieval.");
		}
		
	}

	/** Displays formated search engine result from cached value. */
	private static void displayResults() {
		if (results == null) {
			System.err.println("No query was executed - no results can be shown.");
		} else if (!results.isEmpty() && results.get(0).getSimilarity() == 0.0) {
			System.out.println("Query produced no results.");
		} else {
			for (int i = 0, j = results.size(); i < j && i < 10; i++) {
				VectorUtil.QueryResult result = results.get(i);
				if (result.getSimilarity() != 0) {
					System.out.printf("[%2d] (%.4f) %s\n", i, result.getSimilarity(),
							rootDirectory.toString() + File.separatorChar + result.getDocument().getFileName());
				} else {
					break;
				}
			}
		}
	}
}
