package hr.fer.zemris.java.tecaj.hw5.db.operator;

/**
 * The LIKE operator is used in a query to search for a specified pattern in a
 * column of database.
 * 
 * @author Ante Spajic
 *
 */
public class LikeCondition implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		if (value1 == null || value2 == null) {
			throw new IllegalArgumentException("Values can't be null");
		}
		if ((value2.length() - value2.replace("*", "").length()) > 1) {
			throw new UnsupportedOperationException("Only 1 '*' is supported");
		}
		String regex = value2.replace("*", "(.*)");
		return value1.matches(regex);
	}

}
