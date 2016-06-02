package hr.fer.zemris.java.custom.scripting.parser;

/**
 * This class is a form of <code>Throwable</code> that indicates conditions that
 * a {@link SmartScriptParser} might want to catch. It is thrown when there's an
 * error in document parsing.
 * 
 * @author Ante Spajic
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * Default generated serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with <code>null</code> as its detail message.
	 */
	public SmartScriptParserException() {
	}

	/**
	 * Constructs a new exception with a specified detail message.
	 * 
	 * @param message
	 *            the detail message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
}
