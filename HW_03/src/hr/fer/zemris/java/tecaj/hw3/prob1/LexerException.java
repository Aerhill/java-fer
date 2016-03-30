package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Exception used to let user known parsing of a token couldn't be done.
 * 
 * @author Ante Spajic
 *
 */
public class LexerException extends RuntimeException {

    /**
     * Generated serial version ID
     */
    private static final long serialVersionUID = -3418417678755472299L;

    public LexerException() {
    }
    
    public LexerException(String message) {
	super(message);
    }
    
}
