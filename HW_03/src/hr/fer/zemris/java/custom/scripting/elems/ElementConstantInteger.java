package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that extends Element and is used for storing integer elements inside of
 * echo and forloop nodes.
 * 
 * @author Ante Spajic
 * 
 */
public class ElementConstantInteger extends Element {

	private int value;

	/**
	 * Constructor which sets value property. Given value must be an integer
	 * (positive or negative).
	 * 
	 * @param value
	 *            value to be set for this Element.
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Returns this elements value.
	 * 
	 * @return This elements value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns this elements value string representation.
	 * 
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}
}
