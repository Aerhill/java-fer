package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that extends Element and is used for storing operator elements inside
 * of echo and forloop nodes.
 * 
 * @author Ante Spajic
 *
 */
public class ElementOperator extends Element {

	private String symbol;

	/**
	 * Constructor for this element which sets symbol property. Given symbol
	 * must be one of these: +,-,*,/.
	 * 
	 * @param symbol
	 *            desired value of the token
	 */

	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Returns the value of the element.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return symbol;
	}
	
	/**
	 * Returns the value of the element.
	 * 
	 * @return the value
	 */
	@Override
	public String asText() {
		return symbol;
	}
}
