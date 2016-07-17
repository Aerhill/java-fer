package hr.fer.zemris.cmdapps.trazilica;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Vocabulary with which search engine works when parsing user's
 * queries and producing search results. Vocabulary consists of vocabulary words
 * - words which can be searched in documents and which contribute to search
 * results, and stopping words - words that will be disregarded and which do not
 * contribute to search results.
 * 
 * @author Ante Spajić
 */
public class Vocabulary {
	
	private Set<String> vocabulary;
	private Set<String> stopWords;
	private Map<String,Double> inverseDocumentFrequency;
	private List<DocumentNode> documents;
	
	/** Public constructor receives no arguments and initializes vocabulary. */
	public Vocabulary() {
		vocabulary = new LinkedHashSet<>();
		stopWords = ConcurrentHashMap.newKeySet();
		inverseDocumentFrequency = new HashMap<>();
		documents = new CopyOnWriteArrayList<>();
	}
	
	/**
	 * Returns inverse document frequency for word passed as argument. If word
	 * passed is not present in vocabulary, <tt>null</tt> is returned.
	 * 
	 * @param vocabularyWord word whose IDF value is requested
	 * @return IDF value of passed word or <tt>null</tt> if word is not present
	 *         in vocabulary
	 */
	public Double getInverseDocumentFrequency(String vocabularyWord) {
		return inverseDocumentFrequency.get(vocabularyWord);
	}
	
	/** Calculates inverse document frequency value for all words currently present in vocabulary. */
	public void calculateInverseDocumentFrequency() {
		inverseDocumentFrequency = VectorUtil.idf(documents);
	}
	
	/**
	 * Returns unmodifiable list of documents whose words populate this
	 * vocabulary.
	 * 
	 * @return documents
	 */
	public List<DocumentNode> getDocuments() {
		return Collections.unmodifiableList(documents);
	}
	
	/**
	 * Adds document to list of this vocabulary's documents.
	 * 
	 * @param documentNode document to be added
	 */
	public void addDocument(DocumentNode documentNode) {
		documents.add(documentNode);
	}
	
	/**
	 * Collection of words which can be searched in documents and which
	 * contribute to search results. This collection is meant to be used for
	 * read-only purposes and is not locked in terms of synchronization for
	 * speed enhancements.
	 * 
	 * @return words which can be searched
	 */
	public Set<String> getVocabularyWords() {
		return vocabulary;
	}
	
	/**
	 * Updates current vocabulary with new words that can be searched and which
	 * contribute to search result.
	 * 
	 * @param newVocabularyWords words to be added to vocabulary
	 */
	public synchronized void addVocabularyWords(Collection<String> newVocabularyWords) {
		vocabulary.addAll(newVocabularyWords);
	}
	
	/**
	 * Unmodifiable collection of words that will be ignored and which do
	 * not contribute to search results.
	 * 
	 * @return words that will be ignored
	 */
	public Set<String> getStoppingWords() {
		return Collections.unmodifiableSet(stopWords);
	}
	
	/**
	 * Updates this vocabulary's stopping words - words that will be disregarded
	 * and which do not contribute to search results.
	 * 
	 * @param newStoppingWords stopping words to be added to vocabulary
	 */
	public void addStoppingWords(Collection<String> newStoppingWords) {
		stopWords.addAll(newStoppingWords);
	}
}
