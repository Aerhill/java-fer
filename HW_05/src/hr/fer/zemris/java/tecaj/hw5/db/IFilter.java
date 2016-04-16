package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Strategy design pattern used for filtering {@link StudentRecord} objects
 * based on provided condition that can be a lambda expression or pre-written
 * strategy.
 * 
 * @author Ante Spajic
 *
 */
public interface IFilter {

	/**
	 * Filtering method that returns true only if this record satisfies the
	 * parsed query condition for filtering.
	 * 
	 * @param record
	 *            StudentRecord to be checked for a filter.
	 * @return True if record satisfies the condition, false otherwise.
	 */
	boolean accepts(StudentRecord record);
}
