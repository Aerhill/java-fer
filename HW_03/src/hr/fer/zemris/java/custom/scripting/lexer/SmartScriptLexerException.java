package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Custom runtime exception to notify the user that an error inside Lexer has
 * occurred and if its not caught by the user program will be terminated.
 * 
 * @author Ante Spajic
 *
 */
public class SmartScriptLexerException extends RuntimeException {

	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -3486739111634531536L;

	public SmartScriptLexerException() {
	}

	public SmartScriptLexerException(String message) {
		super(message);
	}
}
