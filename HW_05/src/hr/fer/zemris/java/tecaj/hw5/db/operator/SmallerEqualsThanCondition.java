package hr.fer.zemris.java.tecaj.hw5.db.operator;

/**
 * Comparison strategy operator that checks if relation "value1 <= value2" is
 * satisfied.
 * 
 * @author Ante Spajic
 *
 */
public class SmallerEqualsThanCondition implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		if (value1 == null || value2 == null) {
			throw new IllegalArgumentException("Values of comparison can't be null");
		}
		return COLLATOR.compare(value1, value2) <= 0;
	}

}
