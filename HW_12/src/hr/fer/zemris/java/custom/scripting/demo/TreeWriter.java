package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * TreeWriter demonstrates proper lexing and parsing of a smart script document
 * using a visitor pattern.
 * 
 * @author Ante Spajic
 *
 */
public class TreeWriter {

	/**
	 * Entry point to the program.
	 * 
	 * @param args
	 *            Path to the document to be parsed.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Invalid number of arugments");
			return;
		}

		String filepath = args[0];
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document! \n" + e.getMessage());
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		DocumentNode document = parser.getDocumentNode();
		document.accept(new WriterVisitor());
	}

	/**
	 * The Class WriterVisitor implements nodevisitor to print parsed text.
	 */
	public static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.asText());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node.asText());
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node.asText());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			System.out.print(node.asText());
		}

	}
}
