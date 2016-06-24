package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * The Class JPAEMFProvider is a entity manager factory provide for our JPA
 * entity manager. This class is nothing special it only wraps the single
 * application entity manager factory.
 * 
 * @author Ante Spajic
 */
public class JPAEMFProvider {

	/** The EntityManagerFactory object. */
	public static EntityManagerFactory emf;

	/**
	 * Gets the EntityManagerFactory.
	 *
	 * @return the EntityManagerFactory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets the EntityManagerFactory.
	 *
	 * @param emf
	 *            the new EntityManagerFactory
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}