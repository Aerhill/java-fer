package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing a piece of textual data. It inherits from Node class.
 * 
 * @author Ante Spajic
 *
 */
public class TextNode extends Node {

	private String text;

	/**
	 * Constructor for TextNode, receives text as only parameter.
	 * 
	 * @param text
	 *            Value of the text node
	 */
	public TextNode(String text) {
		this.text = text;
		this.text = this.text.replace("\\", "\\\\");
		this.text = this.text.replace("{", "\\{");
	}

	/**
	 * Getter for this nodes text property.
	 * 
	 * @return This nodes text
	 */
	public String getValue() {
		return text;
	}
	
	@Override
	public String asText() {
		return text;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
