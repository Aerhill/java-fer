package hr.fer.zemris.cmdapps.trazilica;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Offers static methods which assist in operations between tfidf (term
 * frequency-inverse document frequency) document vectors.
 * 
 * @author Ante Spajić
 */
public class VectorUtil {
	
	/**
	 * Holds information about comparison of query to one document.
	 * 
	 * @author Ante Spajić
	 */
	public static class QueryResult implements Comparable<QueryResult> {
		
		/** Similarity between query and document to which it was compared. */
		private Double similarity;
		/** Document to which query was compared. */
		private DocumentNode document;
		
		/**
		 * Receives information about comparison of query to one document.
		 * 
		 * @param similarity similarity between query and document to which it was compared
		 * @param document document to which query was compared
		 */
		public QueryResult(Double similarity, DocumentNode document) {
			this.similarity = similarity;
			this.document = document;
		}
		
		/**
		 * Similarity between query and document to which it was compared.
		 * @return similarity
		 */ 
		public Double getSimilarity() {
			return similarity;
		}
		
		/**
		 * Document to which query was compared.
		 * @return document to which query was compared
		 */
		public DocumentNode getDocument() {
			return document;
		}
		
		@Override
		public int compareTo(QueryResult o) {
			if (this.similarity > o.similarity) return 1;
			else if (this.similarity < o.similarity) return -1;
			else return 0;
		}
	}

	/**
	 * Calculates number of term/word occurrences inside of document from 
	 * which all available terms/words were extracted.
	 * 
	 * @param termToCheck term to be checked
	 * @param allTerms all terms present in document
	 * @return number of term occurrences
	 */
	public static int tf(String termToCheck, List<String> allTerms) {
		int count = 0;
		for(String term : allTerms) {
			if (term.equalsIgnoreCase(termToCheck)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Calculates inverse document frequency of whole vocabulary - all words
	 * present in collection's documents.
	 * 
	 * @param documents collection's documents
	 * @return inverse document frequency of whole vocabulary
	 */
	public static Map<String,Double> idf(List<DocumentNode> documents) {
		Map<String,Double> result = new HashMap<>();
		for (DocumentNode document : documents) {
			Set<String> fileTokens = document.getFileTokens().stream().collect(Collectors.toSet());
			for (String token : fileTokens) {
				result.compute(token.toLowerCase(), (k,v) -> (v == null) ? 1 : v + 1);
			}
		}
		result.replaceAll((k,v) -> Math.log(documents.size() / v));
		return result;
	}
	
	/**
	 * Performs computation that produces objects of type {@link QueryResult}
	 * which encapsulate similarity between user's query, passed as instance of
	 * {@link DocumentNode} object which stores necessary keywords, and
	 * documents available in collection, also instances of {@link DocumentNode}
	 * object. Resulting collection is sorted in order of similarity between
	 * user's query and given documents.
	 * 
	 * @param query user's query
	 * @param documents collection of available documents
	 * @return collection of {@link QueryResult} objects ordered by similarity to available documents
	 */
	public static List<QueryResult> performComputation(DocumentNode query, List<DocumentNode> documents) {
		List<Double> queryVector = query.getTfIdfVector();
		List<QueryResult> result = new ArrayList<>();
		
		for (DocumentNode document : documents) {
			Double similarity = cosineSimilarity(queryVector, document.getTfIdfVector());
			result.add(new QueryResult(similarity, document));
		}
		
		return result.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
	}
 	
	/**
	 * Calculates cosine similarity between two tfidf document vectors. 
	 * 
	 * @param firstDocument tfidf vector of first document
	 * @param secondDocument tfidf vector of second document
	 * @return cosine similarity between two tfidf vectors
	 */
    public static double cosineSimilarity(List<Double> firstDocument, List<Double> secondDocument) {
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;
        double cosineSimilarity = 0.0;

        for (int i = 0, j = firstDocument.size(); i < j; i++) {
        	double firstOperand = firstDocument.get(i);
        	double secondOperand = secondDocument.get(i);
        	
            dotProduct += firstOperand * secondOperand;
            magnitude1 += Math.pow(firstOperand, 2);
            magnitude2 += Math.pow(secondOperand, 2);
        }

        magnitude1 = Math.sqrt(magnitude1);
        magnitude2 = Math.sqrt(magnitude2);

        if (magnitude1 != 0.0 && magnitude2 != 0.0) {
            cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
        } else {
            cosineSimilarity = 0.0;
        }
        return cosineSimilarity;
    }
}
