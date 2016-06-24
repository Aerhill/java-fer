package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * The Class DAOProvider provides the data access object for the user.
 * This is a singleton yes.
 * 
 * @author Ante Spajic
 */
public class DAOProvider {

	/** The dao. */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Gets the dao.
	 *
	 * @return the dao
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}