package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.expression.IConditionalExpression;

/**
 * Class that is used to perform a query filter inside a {@link StudentDatabase}.
 * 
 * @author Ante Spajic
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * An instance of a query parser used by this QueryFilter
	 */
	private QueryParser parser;

	/**
	 * Public constructor that takes a query command and parses it to a valid
	 * filter to be executed upon a {@link StudentDatabase}
	 * 
	 * @param query
	 *            Query command to be executed.
	 */
	public QueryFilter(String query) {
		parser = new QueryParser(query);
	}

	@Override
	public boolean accepts(StudentRecord record) {
		IConditionalExpression expr = parser.getExpression();
		return expr.recordSatisfies(record);
	}

}
