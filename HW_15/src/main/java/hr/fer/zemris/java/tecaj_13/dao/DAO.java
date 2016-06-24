package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Data access object interface, provides necessary database 
 * operations for this homework.
 * 
 * @author Ante Spajic
 *
 */
public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Dohvaća listu svih registriranih korisnika bloga
	 * 
	 * @return lista registriranih bloggera
	 * @throws DAOException
	 */
	List<BlogUser> getBlogUsers() throws DAOException;
	
	/**
	 * Returns the user with provided nick
	 * @param nick user's nick
	 * @return BlogUser associated with provided nick or null
	 */
	BlogUser getUser(String nick);

	/**
	 * This method creates a new user in a database.
	 * 
	 * @param user user to be created in a database
	 */
	void createNewUser(BlogUser user);

	/**
	 * Returns all entries associated with provided user
	 * 
	 * @param user user to get all the entries for
	 * @return list of blogentries for the user
	 */
	List<BlogEntry> getBlogEntriesForUser(BlogUser user);

	/**
	 * Creates a new blog entry in a database.
	 * 
	 * @param entry entry to be created in a database
	 */
	void createNewBlogEntry(BlogEntry entry);

	/**
	 * Updates an existing blog entry inside a database.
	 * 
	 * @param entry entry to be updated
	 */
	void updateBlogEntry(BlogEntry entry);

	/**
	 * Adds a new comment to database.
	 * 
	 * @param comment comment to be added to db
	 */
	void addNewComment(BlogComment comment);
}