package hr.fer.zemris.cmdapps.trazilica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Encapsulates content of one file which contributes to vocabulary of search
 * engine and for whose content search can also be performed. Key component of
 * {@link DocumentNode} is it's tf-idf vector. More information about tf-idf can
 * be found <a href="https://en.wikipedia.org/wiki/Tf–idf">here</a>.
 * 
 * @author Ante Spajić
 */
public class DocumentNode {
	
	/** File name. */
	private String fileName;
	/** Retrieved file tokens. */
	private List<String> fileTokens;
	/** File's tf-idf vector. */
	private List<Double> tfIdfVector;
	
	/**
	 * Public constructor receives file name and tokens retrieved from file
	 * which this instance of {@link DocumentNode} will encapsulate.
	 * 
	 * @param fileName file name
	 * @param fileTokens file tokens
	 */
	public DocumentNode(String fileName, List<String> fileTokens) {
		this.fileName = fileName;
		this.fileTokens = fileTokens;
	}
	
	/**
	 * Returns file name.
	 * 
	 * @return file name
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * Sets file name.
	 * 
	 * @param fileName file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * File tokens retrieved from file which this {@link DocumentNode}
	 * encapsulates.
	 * 
	 * @return file tokens
	 */
	public List<String> getFileTokens() {
		return Collections.unmodifiableList(fileTokens);
	}
	
	/**
	 * Sets file tokens retrieved from file which this {@link DocumentNode}
	 * encapsulates.
	 * 
	 * @param fileTokens new file tokens
	 */
	public void setFileTokens(List<String> fileTokens) {
		this.fileTokens = fileTokens;
	}
	
	/**
	 * Tf-idf (term frequency-inverse document frequency) vector calculated for
	 * this {@link DocumentNode}.
	 * 
	 * @return tf-idf vector
	 */
	public List<Double> getTfIdfVector() {
		return tfIdfVector;
	}
	
	/**
	 * Calculates value of tf-idf (term frequency-inverse document frequency)
	 * vector for file which this {@link DocumentNode} encapsulates.
	 * 
	 * @param vocabulary vocabulary
	 */
	public void calculateTfIdfVector(Vocabulary vocabulary) {
		Set<String> vocabularyWords = vocabulary.getVocabularyWords();
		tfIdfVector = new ArrayList<>(vocabularyWords.size());
		int position = 0;			
		for(String term : vocabularyWords) {
			
			if (!fileTokens.contains(term)) {
				tfIdfVector.add(position++, new Double(0));
				continue;
			}

			int tf = VectorUtil.tf(term, fileTokens);
			double idf = vocabulary.getInverseDocumentFrequency(term);
			tfIdfVector.add(position++, new Double(tf * idf));
		}
	}
	
	@Override
	public String toString() {
		return fileTokens.toString();
	}
}
