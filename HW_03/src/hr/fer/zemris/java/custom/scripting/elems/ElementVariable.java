package hr.fer.zemris.java.custom.scripting.elems;

/**
 * 
 * Class that extends Element and is used for storing element variables inside
 * of echo and forloop nodes.
 * 
 * @author Ante Spajic
 *
 */
public class ElementVariable extends Element {

	private String name;

	/**
	 * Class constructor specifying the name of the variable.
	 *
	 * @param name
	 *            name of the variable
	 * @throws IllegalArgumentException
	 *             if <code>null</code> tries to be set as a variable name
	 */
	public ElementVariable(String name) {
		if (name == null) {
			throw new IllegalArgumentException(
					"Can't create a variable token with null as a name.");
		}
		this.name = name;
	}

	/**
	 * Getter function for the class property <code>name</code>.
	 *
	 * @return name of a variable
	 */
	public String getName() {
		return name;
	}

	@Override
	public String asText() {
		return name;
	}
}
