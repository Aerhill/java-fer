package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node representing from Node class. a command which generates some textual
 * output dynamically. It inherits from Node class.
 * 
 * @author Ante Spajic
 *
 */
public class EchoNode extends Node {

	private Element[] elements;

	/**
	 * Constructs a new echo node. Parameter elements must contain valid element
	 * objects.
	 * 
	 * @param elements
	 *            Array of elements of this node.
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Getter method for elements property
	 * 
	 * @return An array of elements
	 */
	public Element[] getElements() {
		return elements;
	}

	/**
	 * Returns a text representation of echo node in
	 * <code>{$= (elements) $}</code> form. Example:
	 * <code>{$= i i * @sin "0.000" @decfmt $}</code>
	 * 
	 * @return a text representation of the node
	 */
	public String asText() {
		String str = "{$=";
		for (Element t : elements) {
			str += t.asText() + " ";
		}
		str += "$}";
		return str;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
