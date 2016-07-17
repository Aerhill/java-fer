package hr.fer.zemris.cmdapps.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * Class that is responsible for building a really good {@link Vocabulary}. 
 * Vocabulary is built with buidVocabulary method.
 * 
 * @author Ante Spajić
 */
public class VocabularyBuilder {

	/** Thread pool used for speed enhancements during vocabulary build. */
	private static ExecutorService pool;

	/**
	 * Factory method creates and populates instance of {@link Vocabulary} class
	 * with information stored in directory whose path is passed as argument of
	 * this function.
	 * 
	 * @param directoryPath
	 *            path to directory with vocabulary files
	 * @return populated vocabulary
	 */
	public static Vocabulary buildVocabulary(String directoryPath) throws IOException {

		Path pathToDirectory = Paths.get(directoryPath);
		Path pathToStoppingWords = Paths.get(Main.RELATIVE_PATH_TO_STOPPING_WORDS);

		if (!Files.isDirectory(pathToDirectory)) {
			throw new RuntimeException("Path to directory given does not lead to a directory.");
		}

		if (!Files.isRegularFile(pathToStoppingWords)) {
			throw new RuntimeException("Path to stopping words given does not lead to a file.");
		}

		pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), (r) -> {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		});

		Vocabulary vocabulary = new Vocabulary();

		loadStoppingWords(vocabulary, pathToStoppingWords);

		loadDocuments(vocabulary, pathToDirectory);

		vocabulary.calculateInverseDocumentFrequency();

		computeVectors(vocabulary);

		System.out.println("Vocabulary size: " + vocabulary.getVocabularyWords().size());

		return vocabulary;
	}

	/**
	 * Fills vocabulary with stopping words. Stopping words are declared in file
	 * with path given.
	 * 
	 * @param vocabulary
	 *            vocabulary to be populated with stopping words
	 * @param pathToStoppingWords
	 *            path to file with declared stopping words
	 */
	private static void loadStoppingWords(Vocabulary vocabulary, Path pathToStoppingWords) throws IOException {
		List<String> stoppingWords = Files.readAllLines(pathToStoppingWords, StandardCharsets.UTF_8);
		vocabulary.addStoppingWords(stoppingWords);
	}

	/**
	 * Populates vocabulary with documents whose words will build search engine
	 * vocabulary, and whose content can be searched for through search engine.
	 * 
	 * @param vocabulary
	 *            vocabulary to be populated with documents
	 * @param pathToDirectory
	 *            path to directory with documents
	 */
	private static void loadDocuments(Vocabulary vocabulary, Path pathToDirectory) {
		List<Future<Void>> documentParsingResults = new ArrayList<>();
		try {
			Files.walk(pathToDirectory).forEach(file -> {
				if (Files.isRegularFile(file)) {
					documentParsingResults.add(pool.submit(new Parser(vocabulary, file)));
				}
			});
		} catch (IOException e) {
			System.err.println("Unrecoverable exception occurred during read of search engine files.");
			return;
		}

		for (Future<Void> result : documentParsingResults) {
			try { result.get(); } catch (InterruptedException | ExecutionException ignorable) {}
		}
	}

	/**
	 * Worker thread, implementation of {@link Callable} interface, which
	 * performs document parsing and saves file content encapsulated as
	 * {@link DocumentNode} object.
	 * 
	 * @author Ante Spajić
	 */
	private static class Parser implements Callable<Void> {

		/** Vocabulary to which document belongs. */
		private Vocabulary vocabulary;
		/** File to be parsed by this worker. */
		private Path file;

		/**
		 * Constructor receives reference to file which needs to be parsed, and
		 * vocabulary to which document belongs.
		 * 
		 * @param vocabulary
		 *            vocabulary to which document belongs
		 * @param file
		 *            file
		 */
		public Parser(Vocabulary vocabulary, Path file) {
			this.vocabulary = vocabulary;
			this.file = file;
		}

		@Override
		public Void call() throws Exception {
			List<String> fileTokens = DocumentParser.parseFile(file, vocabulary.getStoppingWords());
			DocumentNode documentNode = new DocumentNode(file.getFileName().toString(), fileTokens);
			vocabulary.addVocabularyWords(fileTokens);
			vocabulary.addDocument(documentNode);
			return null;
		}
	}

	/**
	 * Computes necessary TF-IDF document vectors for all documents which
	 * populate vocabulary.
	 * 
	 * @param vocabulary
	 *            vocabulary with documents
	 */
	private static void computeVectors(Vocabulary vocabulary) {
		List<Future<Void>> vectorComputationResults = new ArrayList<>();

		for (DocumentNode document : vocabulary.getDocuments()) {
			vectorComputationResults.add(pool.submit(new VectorComputationJob(vocabulary, document)));
		}

		for (Future<Void> result : vectorComputationResults) {
			try { result.get(); } catch (InterruptedException | ExecutionException ignorable) {}
		}
	}

	/**
	 * Worker thread which performs computation of tf-idf (term
	 * frequency-inverse document frequency) document vectors.
	 * 
	 * @author Ante Spajić
	 */
	private static class VectorComputationJob implements Callable<Void> {

		/** Document vocabulary. */
		private Vocabulary vocabulary;
		/** Document whose tf-idf vector needs to be produced */
		private DocumentNode document;

		/**
		 * Constructor receives reference to document whose tf-idf vector needs
		 * to be produced, and vocabulary to which document belongs.
		 * 
		 * @param vocabulary
		 *            document vocabulary
		 * @param document
		 *            document Document whose tf-idf vector needs to be produced
		 */
		public VectorComputationJob(Vocabulary vocabulary, DocumentNode document) {
			this.vocabulary = vocabulary;
			this.document = document;
		}

		@Override
		public Void call() throws Exception {
			document.calculateTfIdfVector(vocabulary);
			return null;
		}
	}
}
