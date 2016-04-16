package hr.fer.zemris.java.tecaj.hw5.db.operator;

/**
 * Comparison operator strategy that checks if two values are in smaller or
 * equal relation where on left side of relation is value1 that is checked if is
 * smaller or equal than value2.
 * 
 * @author Ante Spajic
 *
 */
public class SmallerThanCondition implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		if (value1 == null || value2 == null) {
			throw new IllegalArgumentException("Values of comparison can't be null");
		}
		return COLLATOR.compare(value1, value2) < 0;
	}

}
