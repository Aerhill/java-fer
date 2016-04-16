package hr.fer.zemris.java.tecaj.hw5.db.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.StudentDatabase;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Class that is an implementation of {@link IConditionalExpression} that is
 * used when a query is composed from more than 1 condition separated by and.
 * This is an implementation of Composite pattern based on more conditional
 * expressions.
 * 
 * @author Ante Spajic
 *
 */
public class CompositeConditionalExpression implements IConditionalExpression {

	/**
	 * List of expressions to be executed.
	 */
	private List<IConditionalExpression> expressions;

	/**
	 * Public constructor that takes 1 or more expressions that can be executed.
	 * Record satisfies the condition only if it satisfies all of conditional
	 * expressions provided.
	 * 
	 * @param expressions
	 *            Expressions to be executed on a {@link StudentDatabase}
	 */
	public CompositeConditionalExpression(IConditionalExpression... expressions) {
		this.expressions = new ArrayList<>(Arrays.asList(expressions));
	}

	@Override
	public boolean recordSatisfies(StudentRecord record) {
		for (IConditionalExpression expression : expressions) {
			if (!expression.recordSatisfies(record)) {
				return false;
			}
		}
		return true;
	}

}
