package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that extends Element and is used for storing string elements inside of
 * echo and forloop nodes.
 * 
 * @author Ante Spajic
 *
 */
public class ElementConstantString extends Element {

	private String value;

	/**
	 * Setter method for value property. Given value must start and end with "
	 * sign.
	 * 
	 * @param value
	 *            Desired value of the token
	 */
	public ElementConstantString(String value) {
		this.value = value;
	}

	/**
	 * Returns the value of the token.
	 * 
	 * @return value of the token
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Returns the value of the token.
	 * 
	 * @return value of the token
	 */
	@Override
	public String asText() {
		return value;
	}
}
