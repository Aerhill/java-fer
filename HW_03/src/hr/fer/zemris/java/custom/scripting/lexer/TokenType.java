package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * 
 * Enumeration that defines types of tokens that lexer generates. They are
 * assigned to their corresponding element type. Except those types there are
 * also <code>EOF</code>type which signals that lexer has reached end of given text.
 * 
 * @author Ante Spajic
 *
 */
public enum TokenType {

	FUNCTION, VARIABLE, STRING, TEXT, CONSTANT_INTEGER, CONSTANT_DOUBLE, OPERATOR, EOF, TAG
}
