package hr.fer.zemris.cmdapps.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * Class used to parse text documents for search engine. Result
 * is collection of {@link String}.
 * 
 * @author Ante Spajić
 */
public class DocumentParser {
	
	/** Regular expression used for filtering and spliting document text. */
	public static final String FILTER_REGEX = "[^\\p{L}]";
	
	/**
	 * Analyzes line of text and produces words. Filter in terms of removing
	 * unallowed words is applied on resulting collection.
	 * 
	 * @param text text to be analyzed
	 * @param stoppingWords words to be filtered out
	 * @return collection of words produced from text
	 */
	public static List<String> parseText(String text, Collection<String> stoppingWords) {
		return filterText(text, stoppingWords);
	}
	
	/**
	 * Analyzes text present in given file and produces words. Filter in terms
	 * of removing unallowed words is applied on resulting collection.
	 * 
	 * @param file file whose content is to be analyzed
	 * @param stoppingWords words to be filtered out
	 * @return collection of words produced from text
	 */
	public static List<String> parseFile(Path file, Collection<String> stoppingWords) throws IOException {
		List<String> result = new ArrayList<>();
		List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
		for (String line : lines) {
			result.addAll(filterText(line, stoppingWords));
		}
		return result;
	}
	
	/**
	 * Method that analyzes one line of text and produces words. Filter in
	 * terms of removing unallowed words is applied on resulting collection.
	 * 
	 * @param text text to be analyzed
	 * @param stoppingWords words to be filtered out
	 * @return collection of words produced from text
	 */
	private static List<String> filterText(String text, Collection<String> stoppingWords) {
		String[] tokens = text.trim().split(FILTER_REGEX);
		List<String> result = new ArrayList<>();
		
		for (String word : tokens) {
			word = word.trim();
			if (!word.isEmpty() && !stoppingWords.contains(word.toLowerCase())) {
				result.add(word.toLowerCase());
			}
		}
		
		return result;
	}
}
