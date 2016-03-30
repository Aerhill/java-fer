package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration that describes the state that <code>SmartScriptLexer</code> is
 * currently operating in.While in <code>TEXT</code> state lexer treats
 * everything as string, while in <code>TAG</code> it looks for keywords and
 * tags.
 * 
 * @author Ante Spajic
 *
 */
public enum SmartScriptLexerState {
	TEXT, TAG
}
