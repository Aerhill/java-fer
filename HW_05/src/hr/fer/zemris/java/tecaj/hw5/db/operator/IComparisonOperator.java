package hr.fer.zemris.java.tecaj.hw5.db.operator;

import java.text.Collator;
import java.util.Locale;

/**
 * Strategy interface that is implemented by classes responsible for certain
 * comparison operations.
 * 
 * @author Ante Spajic
 *
 */
public interface IComparisonOperator {

	public static Collator COLLATOR = Collator.getInstance(Locale.forLanguageTag("hr-HR"));
	/**
	 * Compares two values on a certain condition and returns true if a
	 * condition is satisfied.
	 * 
	 * @param value1
	 *            Left side of comparison.
	 * @param value2
	 *            Right side of comparison.
	 * @return True if comparison is satisfied, false otherwise.
	 */
	boolean satisfied(String value1, String value2);
}
