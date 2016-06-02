package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * INodeVisitor stands for a visitor pattern that visits a tree.
 * 
 * @author Ante Spajic
 */
public interface INodeVisitor {
	
	/**
	 * Action to be performed in text node.
	 *
	 * @param node the text node
	 */
	void visitTextNode(TextNode node);
	
	/**
	 * Action to be performed in forloop node.
	 *
	 * @param node the forloop node
	 */
	void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Action to be performed in echo node.
	 *
	 * @param node the echo node
	 */
	void visitEchoNode(EchoNode node);
	
	/**
	 * Action to be performed in document node.
	 *
	 * @param node the dcument node
	 */
	void visitDocumentNode(DocumentNode node);
}
