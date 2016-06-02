package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing an entire document. It inherits from Node class.
 * 
 * @author Ante Spajic
 *
 */
public class DocumentNode extends Node {

	@Override
	public String asText() {
		String str = "";
		int n = numberOfChildren();
		if (n > 0) {
			for (int i = 0; i < n ; i++) {
				str += getChild(i).asText();
			}
		}
		return str;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}

}
