package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Class used to lexically analyze given text and atomically divide it into
 * smaller lexical parts to generate tokens for {@link SmartScriptParser}. This
 * lexer has two states: TEXT and TAG state. While inside TEXT state everything
 * is treated as text and generated as 1 String token. This behavior is default
 * and it is changed when lexer encounters "{$" tag when enters TAG state. While
 * in tag state lexer has a set of rules to further tokenize the text. Lexer
 * stays in TAG state until it encounters closing tag "$}". State can also be
 * changed manually with setState method with appropriate LexerStates.
 * 
 * @author Ante Spajic
 *
 */
public class SmartScriptLexer {

	/**
	 * Loaded text as an char data array.
	 */
	private char[] data;
	/**
	 * Current index in text.
	 */
	private int currentIndex;
	/**
	 * Current state of lexer
	 */
	private SmartScriptLexerState state;
	/**
	 * Variable to store lexing tokens.
	 */
	private ScriptToken token;

	/**
	 * Public constructor for this lexer, only argument is the text that you
	 * want to lexically analyze.
	 * 
	 * @param text
	 *            Text to be lexically analyzed.
	 */
	public SmartScriptLexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Provided text cannot be null");
		}
		text = text.trim();
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = SmartScriptLexerState.TEXT;
	}

	/**
	 * Sets this lexer's state, allowed states are TEXT and TAG. While in TEXT
	 * state lexer treats everything as text and the TAG state is entered when
	 * "{$" is encountered in text.
	 * 
	 * @param state
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null || !(state instanceof SmartScriptLexerState)) {
			throw new IllegalArgumentException(
					"Invalid state provided, state cannot be: " + state);
		}
		this.state = state;
	}

	/**
	 * Depending on the current state of this lexer this method analyzes word
	 * until next whitespace and returns according ScriptToken
	 * 
	 * @return Next token from given text
	 */
	public ScriptToken nextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new SmartScriptLexerException(
					"No more tokens to be generated.");
		}
		if (currentIndex == data.length) {
			return new ScriptToken(TokenType.EOF, null);
		}

		switch (state) {
		case TEXT:
			token = processText();
			break;
		case TAG:
			token = processTag();
			break;
		}

		return token;
	}

	/**
	 * When in TEXT state nextToken method delegates work to this method which
	 * treats everything until "{$" tag as text and generates textTokens from
	 * given text.
	 * 
	 * @return Processed script token with according Element.
	 */
	private ScriptToken processText() {
		try {
			if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
				setState(SmartScriptLexerState.TAG);
				currentIndex += 2;
				token = new ScriptToken(TokenType.TAG, new ElementConstantString("TAG"));
			} else {
				token = textToken();
			}
		} catch (Exception e) {
			throw new SmartScriptLexerException("Invalid opening tag.");
		}

		return token;
	}

	/**
	 * When in TAG state nextToken method delegates work to this method which
	 * analyzes language tags and returns according script tokens that represent
	 * elements from given text.
	 * 
	 * @return Processed script token with according Element.
	 */
	private ScriptToken processTag() {
		// skip whitespaces
		while (Character.toString(data[currentIndex]).matches("\\s+")
				&& currentIndex < data.length - 1) {
			currentIndex++;
		}
		if (data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
			setState(SmartScriptLexerState.TEXT);
			currentIndex += 2;
			return new ScriptToken(TokenType.TAG, new ElementConstantString("TAG"));
		}

		if (data[currentIndex] == '=' && token.getType() == TokenType.TAG) {
			currentIndex++;
			return new ScriptToken(TokenType.STRING, new ElementConstantString("="));
		} else if (data[currentIndex] == '@') {
			return functionToken();
		} else if (data[currentIndex] == '"') {
			return stringToken();
		} else if (Character.isLetter(data[currentIndex])) {
			return variableToken();
		} else if (Character.isDigit(data[currentIndex])) {
			return numberToken();
		} else {
			// in case of symbol '-', method will check if there is a number
			// afterwards and treat it as a number
			return symbolToken();
		}
	}

	/**
	 * Method which generates tokens which represent mathematical operators
	 * power(^), division(/), multiplication(*), addition(+) and subtraction(-).
	 * 
	 * @return Descriptive ScriptToken with according ElementOperator from
	 *         analyzed text.
	 */
	private ScriptToken symbolToken() {
		char symbol = data[currentIndex];

		if (symbol == '-' && currentIndex + 1 < data.length
				&& Character.isDigit(data[currentIndex + 1])) {
			return numberToken();
		} else if (symbol == '-' || symbol == '+' || symbol == '*'
				|| symbol == '/' || symbol == '^') {
			return new ScriptToken(TokenType.OPERATOR, new ElementOperator(
					String.valueOf(data[currentIndex++])));
		} else {
			throw new SmartScriptLexerException("Symbol (operator) is invalid.");
		}
	}

	/**
	 * Method which generates number tokens that can be integer or double
	 * precision values. If number is invalid an exception is thrown.
	 * 
	 * @return Descriptive ScriptToken with ElementConstantDouble or
	 *         ElementConstantInteger value.
	 */
	private ScriptToken numberToken() {
		StringBuilder sb = new StringBuilder();
		if (data[currentIndex] == '-') {
			sb.append(data[currentIndex++]);
		}

		int dotCounter = 0;
		while (currentIndex < data.length
				&& (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.')) {
			if (data[currentIndex] == '.') {
				dotCounter++;
			}
			sb.append(data[currentIndex++]);
		}

		if (dotCounter > 1) {
			throw new SmartScriptLexerException(
					"Watch it with those dots, numbers only contain 1 dot, no more.");
		}
		String value = sb.toString();
		if (value.contains(".")) {
			try {
				return new ScriptToken(TokenType.CONSTANT_DOUBLE,
						new ElementConstantDouble(Double.parseDouble(value)));
			} catch (Exception e) {
				throw new SmartScriptLexerException("Couldnt parse double");
			}
		} else {
			try {
				return new ScriptToken(TokenType.CONSTANT_DOUBLE,
						new ElementConstantInteger(Integer.parseInt(value)));
			} catch (Exception e) {
				throw new SmartScriptLexerException("Couldnt parse integer");
			}
		}

	}

	/**
	 * This method analyzes variables within document inside tag state and
	 * returns according variable token.
	 * 
	 * @return Descriptive ScriptToken with according ElementVariable.
	 */
	private ScriptToken variableToken() {
		if (!Character.isLetter(data[currentIndex])) {
			throw new SmartScriptLexerException(
					"Your variable name is not valid, it is invalid");
		}

		StringBuilder sb = new StringBuilder();
		while ((Character.isLetter(data[currentIndex])
				|| Character.isDigit(data[currentIndex]) || data[currentIndex] == '_')
				&& currentIndex < data.length) {
			sb.append(data[currentIndex++]);
		}

		return new ScriptToken(TokenType.VARIABLE, new ElementVariable(
				sb.toString()));
	}

	/**
	 * This method analyzes given function within given document inside tag
	 * state and returns according token.
	 * 
	 * @return Descriptive ScriptToken with given ElementFunction inside.
	 */
	private ScriptToken functionToken() {
		StringBuilder sb = new StringBuilder();
		// skip the '@' sign
		currentIndex++;
		while ((Character.isLetter(data[currentIndex])
				|| Character.isDigit(data[currentIndex]) || data[currentIndex] == '_')
				&& currentIndex < data.length) {
			sb.append(data[currentIndex++]);
		}
		return new ScriptToken(TokenType.FUNCTION, new ElementFunction(
				sb.toString()));
	}

	/**
	 * This method generates text tokens in text state.
	 * 
	 * @return Descriptive ScriptToken containing ElementString in text state.
	 */
	private ScriptToken textToken() {
		StringBuilder sb = new StringBuilder();
		while (currentIndex < data.length && data[currentIndex] != '{') {
			// if its not a valid escape sequence throws an exception otherwise
			// it just keeps building
			if (!checkForEscape(sb)) {
				sb.append(data[currentIndex++]);
			}
		}

		return new ScriptToken(TokenType.TEXT, new ElementConstantString(sb.toString()));
	}

	/**
	 * This method generates string tokens in tag state.
	 * 
	 * @return Descriptive ScriptToken containing ElementString
	 */
	private ScriptToken stringToken() {
		StringBuilder sb = new StringBuilder();
		// skip first annotation sign
		currentIndex++;
		while (currentIndex < data.length && data[currentIndex] != '"') {
			if (!checkForEscape(sb)) {
				sb.append(data[currentIndex++]);
			}
		}
		// skip last annotation sign
		currentIndex++;
		return new ScriptToken(TokenType.STRING, new ElementConstantString(
				sb.toString()));
	}

	/**
	 * Helper method that check if the escape sequence in text is correct and
	 * throws an exception to notify the user that invalid escape sequence has
	 * been provided and the document does not comply with lexing rules.
	 *
	 * @throws SmartScriptLexerException
	 *             If escape sequence is incorrect.
	 */
	private boolean checkForEscape(StringBuilder sb) {
		if (currentIndex + 1 >= data.length) {
			throw new SmartScriptLexerException(
					"Invalid escape sequence. Sequence out of bounds.");
		}
		if (data[currentIndex] != '\\') {
			return false;
		}
		char next = data[currentIndex+1];
		switch (state) {
		case TEXT:
			if(next == '\\') sb.append("\\");
			else if(next == '{') sb.append("{");
			else throw new SmartScriptLexerException("Invalid TEXT escape sequence.");
			currentIndex+=2;
			return true;
		case TAG:
			if (next == '\\') sb.append("\\");
            else if (next == '"') sb.append("\"");
            else if (next == 'r') sb.append("\r");
            else if (next == 'n') sb.append("\n");
            else throw new SmartScriptLexerException("Invalid TAG escape sequence.");
            currentIndex += 2;
            return true;
		default:
			throw new SmartScriptLexerException(
					"You provided invalid escape sequence cannot lex the text ");
		}
	}

}
