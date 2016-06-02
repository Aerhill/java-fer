package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class <tt>ValueWrapper</tt> represents container which stores one object. If
 * object is of type <tt>Double</tt>, <tt>Integer</tt>, <tt>String</tt> (needs
 * to represent number, integer or double) and <tt>null</tt> value (will be
 * treated as zero), container can provide methods for basic arithmetic
 * operations.
 * 
 * @author Ante Spajic
 *
 */
public class ValueWrapper {

	/**
	 * Constants which define arithmetic operation to be performed between two
	 * objects - numbers.
	 * 
	 */
	private enum Operation {
		INCREMENT, DECREMENT, MULTIPLY, DIVIDE, COMPARE;
	}

	/**
	 * Constants which define a type of stored value.
	 *
	 */
	private enum Type {
		DOUBLE, INTEGER;
	}

	/**
	 * Variable that holds this wrappers value.
	 */
	private Object value;

	/**
	 * Type of a stored value for arithmetic operations.
	 */
	private Type type;

	/**
	 * Creates {@link ValueWrapper} with initial <code>value</code>.
	 * 
	 * @param value
	 *            initial value
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Returns the stored value.
	 * 
	 * @return value
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Sets the value to be stored.
	 * 
	 * @param value
	 *            wanted value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Private helper method that determines the type of the given value.
	 * 
	 * @param value
	 *            tested value
	 * @return type of the value
	 */
	private static Type determineType(Object value) {
		if (value == null || isInteger(value.toString())) {
			return Type.INTEGER;
		} else if (isDouble(value.toString())) {
			return Type.DOUBLE;
		}

		throw new IllegalArgumentException("Value must be an integer, double or a string that represents a number.");
	}

	/**
	 * Compares two objects. Null values are treated as 0. If they are of
	 * different types, they are both converted into {@link Double}, otherwise
	 * they are compared as they are.
	 * 
	 * @param withValue
	 *            value compared to this value
	 * @return negative number (if this value is less then withValue), 0 if they
	 *         are equal, positive number otherwise
	 */
	public int numCompare(Object withValue) {
		return (int) performOperation(withValue, Operation.COMPARE);
	}

	/**
	 * Used when incrementing the stored value with given value
	 * 
	 * @param incValue
	 *            value which is used to perform operation
	 */
	public void increment(Object incValue) {
		value = performOperation(incValue, Operation.INCREMENT);
	}

	/**
	 * Used when decrementing the stored value with given value
	 * 
	 * @param incValue
	 *            value which is used to perform operation
	 */
	public void decrement(Object incValue) {
		value = performOperation(incValue, Operation.DECREMENT);
	}

	/**
	 * Used when multiplying the stored value with given value
	 * 
	 * @param incValue
	 *            value which is used to perform operation
	 */
	public void multiply(Object incValue) {
		value = performOperation(incValue, Operation.MULTIPLY);
	}

	/**
	 * Used when dividing the stored value with given value
	 * 
	 * @param incValue
	 *            value which is used to perform operation
	 */
	public void divide(Object incValue) {
		value = performOperation(incValue, Operation.DIVIDE);
	}

	/**
	 * Private helper method that delegates the operation to the operation
	 * performer on integers or on doubles.
	 * 
	 * @param incValue
	 *            value that will be operated on
	 * @param oper
	 *            operation performed
	 * @return result of the performed operation
	 */
	private Object performOperation(Object incValue, Operation oper) {
		this.type = determineType(value);
		Type typeOfIncValue = determineType(incValue);

		if (type != Type.INTEGER || typeOfIncValue != Type.INTEGER) {
			Double v1 = getDoubleValue(value);
			Double v2 = getDoubleValue(incValue);
			if (oper == Operation.COMPARE) {
				return v1.compareTo(v2);
			} else {
				return doubleOperation(v1, v2, oper);
			}
		} else {
			Integer v1 = getIntegerValue(value);
			Integer v2 = getIntegerValue(incValue);
			return integerOperation(v1, v2, oper);
		}
	}

	/**
	 * Performs a given operation on two double values.
	 * 
	 * @param value1
	 *            first value
	 * @param value2
	 *            second value
	 * @param oper
	 *            operation to perform
	 * @return result of the operation
	 */
	private Double doubleOperation(Double value1, Double value2, Operation oper) {
		switch (oper) {
		case INCREMENT:
			return value1 + value2;
		case DECREMENT:
			return value1 - value2;
		case MULTIPLY:
			return value1 * value2;
		default:
			if (value2 == 0) {
				throw new ArithmeticException("Division by zero");
			}
			return value1 / value2;
		}
	}

	/**
	 * Performs a given operation on two integer values.
	 * 
	 * @param value1
	 *            first value
	 * @param value2
	 *            second value
	 * @param oper
	 *            operation to perform
	 * @return result of the operation
	 */
	private Integer integerOperation(Integer value1, Integer value2, Operation oper) {
		switch (oper) {
		case INCREMENT:
			return value1 + value2;
		case DECREMENT:
			return value1 - value2;
		case MULTIPLY:
			return value1 * value2;
		case DIVIDE:
			if (value2 == 0) {
				throw new ArithmeticException("Division by zero");
			}
			return value1 / value2;
		default:
			return value1.compareTo(value2);
		}
	}

	/**
	 * Returns a double that is the value of given value.
	 * 
	 * @param value
	 *            value being parsed
	 * @return parsed double
	 */
	private static Double getDoubleValue(Object value) {
		return Double.valueOf(value == null ? 0 : Double.parseDouble(value.toString()));
	}

	/**
	 * Returns a integer that is the value of given value.
	 * 
	 * @param value
	 *            value being parsed
	 * @return parsed integer
	 */
	private static Integer getIntegerValue(Object value) {
		return Integer.valueOf(value == null ? 0 : Integer.parseInt(value.toString()));
	}

	/**
	 * Helper method that checks if a given string is parsable as a double.
	 * 
	 * @param string
	 *            String to be checked
	 * @return True if a string is a valid double value, false otherwise.
	 */
	private static boolean isDouble(String string) {
		try {
			Double.parseDouble(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Helper method that checks if a given string is parsable as an integer.
	 * 
	 * @param string
	 *            String to be checked
	 * @return True if a string is a valid integer value, false otherwise.
	 */
	private static boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
