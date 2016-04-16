package hr.fer.zemris.java.tecaj.hw5.db.expression;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.getter.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.operator.IComparisonOperator;

/**
 * Class that represents a conditional expression for database query. This class
 * is a wrapper for an expression and holds the table column information,
 * condition on which to perform the query and the string literal which
 * represents a piece of data from a table column we want to check.
 * 
 * @author Ante Spajic
 *
 */
public class ConditionalExpression implements IConditionalExpression {

	private IFieldValueGetter fieldGetter;
	private String stringLiteral;
	private IComparisonOperator comparisonOperator;

	/**
	 * Public constructor for conditional expression that takes in three
	 * arguments, fieldGetter is a field from a database record we want to
	 * compare with a provided string literal based on provided conditional
	 * operator.
	 * 
	 * @param fieldGetter
	 *            Field from a record for comparison that represents a table
	 *            column in a database.
	 * @param stringLiteral
	 *            String literal we compare the field with.
	 * @param comparisonOperator
	 *            Operator that is performed on a relation between field and
	 *            string literal.
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		this.comparisonOperator = comparisonOperator;
		this.stringLiteral = stringLiteral;
		this.fieldGetter = fieldGetter;
	}

	/**
	 * Returns this expressions Comparison operator.
	 * 
	 * @return Comparison operator of this expression.
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * Returns the string literal part of this expression.
	 * 
	 * @return String literal part of expression.
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Returns this expressions field getter.
	 * 
	 * @return FieldGetter value.
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	@Override
	public boolean recordSatisfies(StudentRecord record) {
		return comparisonOperator.satisfied(fieldGetter.get(record), stringLiteral);
	}

}
