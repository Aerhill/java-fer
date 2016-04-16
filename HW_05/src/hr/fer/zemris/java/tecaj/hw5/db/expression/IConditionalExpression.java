package hr.fer.zemris.java.tecaj.hw5.db.expression;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Interface for conditional expression strategy that checks if record satisfies
 * a certain condition or multiple conditions.
 * 
 * @author Ante Spajic
 *
 */
public interface IConditionalExpression {

	/**
	 * Method that checks if a student record satisfies the querying condition
	 * and returns true if all conditions are satisfied false otherwise.
	 * 
	 * @param record
	 *            Record that is checked for condition.
	 * @return True if condition is satisfied.
	 */
	boolean recordSatisfies(StudentRecord record);
}
