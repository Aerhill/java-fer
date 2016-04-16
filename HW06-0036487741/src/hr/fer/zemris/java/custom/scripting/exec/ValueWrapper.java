package hr.fer.zemris.java.custom.scripting.exec;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValueWrapper {

	private Object value;

	public ValueWrapper(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void increment(Object incValue) {
		checkAllowedTypes(value, incValue);
		Boolean firstIsDouble = false;
		Boolean secondIsDouble = false;
		double firstOperand = performChecksAndParse(value, firstIsDouble);
		double secondOperand = performChecksAndParse(incValue, secondIsDouble);
		if (value instanceof Double || incValue instanceof Double) {
			value = Double.valueOf(performOperationDoubles(firstOperand,
					secondOperand, Operation.INCREMENT));
		} else {
			value = Integer.valueOf(performOperationIntegers((int) firstOperand,
					(int) secondOperand, Operation.INCREMENT));
		}
	}

	public void decrement(Object decValue) {
		checkAllowedTypes(value, decValue);
		Boolean firstIsDouble = false;
		Boolean secondIsDouble = false;
		double firstOperand = performChecksAndParse(value, firstIsDouble);
		double secondOperand = performChecksAndParse(decValue, secondIsDouble);
		if (firstIsDouble || secondIsDouble) {
			value = Double.valueOf(performOperationDoubles(firstOperand,
					secondOperand, Operation.DECREMENT));
		} else {
			value = Integer.valueOf(performOperationIntegers((int) firstOperand,
					(int) secondOperand, Operation.DECREMENT));
		}
	}

	public void multiply(Object mulValue) {
		checkAllowedTypes(value, mulValue);
		Boolean firstIsDouble = false;
		Boolean secondIsDouble = false;
		double firstOperand = performChecksAndParse(value, firstIsDouble);
		double secondOperand = performChecksAndParse(mulValue, secondIsDouble);
		if (firstIsDouble || secondIsDouble) {
			value = Double.valueOf(performOperationDoubles(firstOperand,
					secondOperand, Operation.MULTIPLY));
		} else {
			value = Integer.valueOf(performOperationIntegers((int) firstOperand,
					(int) secondOperand, Operation.MULTIPLY));
		}
	}

	public void divide(Object divValue) {
		checkAllowedTypes(value, divValue);
		Boolean firstIsDouble = false;
		Boolean secondIsDouble = false;
		double firstOperand = performChecksAndParse(value, firstIsDouble);
		double secondOperand = performChecksAndParse(divValue, secondIsDouble);
		if (value instanceof Double || divValue instanceof Double) {
			value = Double.valueOf(performOperationDoubles(firstOperand,
					secondOperand, Operation.DIVIDE));
		} else {
			value = Integer.valueOf(performOperationIntegers((int) firstOperand,
					(int) secondOperand, Operation.DIVIDE));
		}
	}

	public void numCompare(Object withValue) {
		checkAllowedTypes(value, withValue);
		Boolean firstIsDouble = false;
		Boolean secondIsDouble = false;
		double firstOperand = performChecksAndParse(value, firstIsDouble);
		double secondOperand = performChecksAndParse(withValue, secondIsDouble);
		if (value instanceof Double || withValue instanceof Double) {
			Double result = Double.valueOf(performOperationDoubles(firstOperand,
					secondOperand, Operation.COMPARE));
			System.out.println("Result of comparison: " + result);
		} else {
			Integer result = Integer
					.valueOf(performOperationIntegers((int) firstOperand,
							(int) secondOperand, Operation.COMPARE));
			System.out.println("Result of comparison: " + result);
		}
	}

	/**
	 * Private method performs parsing of value to be used in arithmetic
	 * operation. Parsed and returned value is of double format. Method will
	 * also set flag that signalizes whether number parsed was initially of type
	 * <tt>Integer</tt> or <tt>Double</tt>.
	 * 
	 * @param value
	 *            value to be parsed
	 * @param isDouble
	 *            flag which signalizes initial type of value
	 * @return value represented as number of type <tt>Double</tt>
	 */
	private double performChecksAndParse(Object value, Boolean isDouble) {
		// Check for String.
		if (value instanceof String) {
			checkAllowedStrings(((String) value).trim(), isDouble);
			return Double.parseDouble((String) value);
			// Checking for Integer.
		} else if (value instanceof Double) {
			isDouble = true;
			return (Double) value;
		} else if (value instanceof Integer) {
			isDouble = false;
			return (Double) value;
			// Value must be zero.
		} else {
			isDouble = false;
			return 0;
		}
	}

	/**
	 * Private method checks whether values on which arithmetic operation is
	 * trying to be performed are of allowed type. Allowed types are
	 * <tt>Integer</tt>, <tt>Double</tt>, <tt>String</tt> and <tt>null</tt>
	 * value.
	 * 
	 * @param currentValue
	 *            value in wrapper
	 * @param givenValue
	 *            value passed for operation
	 * @throws RuntimeException
	 *             if values are not of allowed type
	 */
	private void checkAllowedTypes(Object currentValue, Object givenValue) {
		// Checking current value.
		if (!(currentValue == null || currentValue instanceof Integer
				|| currentValue instanceof Double
				|| currentValue instanceof String)) {
			throw new RuntimeException(
					"Arithmetic operation can not be performed on given types of arguments.");
		}
		// Checking passed value.
		if (!(givenValue == null || givenValue instanceof Integer
				|| givenValue instanceof Double
				|| givenValue instanceof String)) {
			throw new RuntimeException(
					"Arithmetic operation can not be performed on given types of arguments.");
		}
	}

	/**
	 * Private method checks whether passed string can be interpreted as number
	 * of type <tt>Integer</tt> or <tt>Double</tt>. If string represents number
	 * of type <tt>Double</tt>, flag will be raised, otherwise flag will be
	 * downed.
	 * 
	 * @param value
	 *            string to be analyzed
	 * @param isDouble
	 *            flag for <tt>Double</tt> value
	 * @throws RuntimeException
	 *             if string can not be interpreted as number
	 */
	private void checkAllowedStrings(String value, Boolean isDouble) {
		Pattern regex = Pattern
				.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
		Matcher matcher = regex.matcher(value);
		if (!matcher.find()) {
			throw new RuntimeException("Invalid number format inside string.");
		}
		Pattern regex2 = Pattern.compile("[eE.]");
		Matcher matcher2 = regex2.matcher(value);
		if (matcher2.find()) {
			isDouble = true;
		} else {
			isDouble = false;
		}
	}

	/**
	 * Private method which performs one of arithmetic operations on values of
	 * type <tt>Double</tt> passed as arguments. Arithmetic operations are
	 * defined by enumeration <tt>Operation</tt>.
	 * 
	 * @param value1
	 *            first operand
	 * @param value2
	 *            second operand
	 * @param operation
	 *            type of arithmetic operation
	 * @return result of arithmetic operation
	 * @throws ArithmeticException
	 *             if division by zero is trying to be performed
	 */
	private double performOperationDoubles(double value1, double value2,
			Operation operation) {
		switch (operation) {
		case INCREMENT:
			return value1 + value2;
		case DECREMENT:
			return value1 - value2;
		case MULTIPLY:
			return value1 * value2;
		case DIVIDE:
			if (value2 == 0) {
				throw new ArithmeticException(
						"Division with 0 can not be performed.");
			}
			return value1 / value2;
		default:
			return Double.compare(value1, value2);
		}
	}

	/**
	 * Private method which performs one of arithmetic operations on values of
	 * type <tt>Integer</tt> passed as arguments. Arithmetic operations are
	 * defined by enumeration <tt>Operation</tt>.
	 * 
	 * @param value1
	 *            first operand
	 * @param value2
	 *            second operand
	 * @param operation
	 *            type of arithmetic operation
	 * @return result of arithmetic operation
	 * @throws ArithmeticException
	 *             if division by zero is trying to be performed
	 */
	private int performOperationIntegers(int value1, int value2,
			Operation operation) {
		switch (operation) {
		case INCREMENT:
			return value1 + value2;
		case DECREMENT:
			return value1 - value2;
		case MULTIPLY:
			return value1 * value2;
		case DIVIDE:
			if (value2 == 0) {
				throw new ArithmeticException(
						"Division with 0 can not be performed.");
			}
			return value1 / value2;
		default:
			return Integer.compare(value1, value2);
		}
	}
}
