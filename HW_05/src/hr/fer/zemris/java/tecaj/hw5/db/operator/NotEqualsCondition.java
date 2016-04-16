package hr.fer.zemris.java.tecaj.hw5.db.operator;

/**
 * Comparison strategy that checks if two given values are not equal.
 * 
 * @author Ante Spajic
 *
 */
public class NotEqualsCondition implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		if (value1 == null || value2 == null) {
			throw new IllegalArgumentException("Values of comparison can't be null");
		}
		return !COLLATOR.equals(value1,value2);
	}

}
