package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * This is a JPA entity manager provider, that provides the access to database
 * and enables you to write to database thread safely and locally.
 * 
 * @author Ante Spajic
 *
 */
public class JPAEMProvider {

	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * Gets the entity manager for this thread.
	 *
	 * @return the entity manager bound to current thread
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if (ldata == null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Commits and closes the entity manager bound to this thread and deletes it
	 * from threadlocal map.
	 * 
	 * @throws DAOException
	 */
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if (ldata == null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

	/**
	 * LocalData class that im not sure what does, but it was here so i left it.
	 * 
	 * @author Marko Cupic
	 *
	 */
	private static class LocalData {
		EntityManager em;
	}

}