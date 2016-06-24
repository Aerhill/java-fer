package hr.fer.zemris.java.tecaj_13.dao;

/**
 * The Class DAOException that wraps all the exceptions associated with dao.
 * 
 * @author Ante Spajic
 */
public class DAOException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new DAO exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new DAO exception.
	 *
	 * @param message the message
	 */
	public DAOException(String message) {
		super(message);
	}
}