package hr.fer.zemris.java.custom.collections;

/**
 * Thrown by methods in the <code>ObjectStack</code> class to indicate that the
 * stack is empty.
 *
 * @author Ante Spajic
 */
public class EmptyStackException extends RuntimeException {
	private static final long serialVersionUID = 5084686378493302095L;

	/**
	 * Constructs a new <code>EmptyStackException</code> with <tt>null</tt> as
	 * its error message string.
	 */
	public EmptyStackException() {
	}

	/**
	 * Constructs a new <code>EmptyStackException</code> with provided message
	 * as its error message string.
	 * 
	 * @param string
	 *            Message describing exception. It is written in stack trace to
	 *            user.
	 */
	public EmptyStackException(String string) {
		super(string);
	}
}
