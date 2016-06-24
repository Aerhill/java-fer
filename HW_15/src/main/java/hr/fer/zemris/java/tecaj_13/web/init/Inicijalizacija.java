package hr.fer.zemris.java.tecaj_13.web.init;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMFProvider;

/**
 * Listener that is fired when an application is launched, this listener only
 * prepares a database connection and stores the {@link EntityManager} into
 * application context.
 * 
 * @author Ante Spajic
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.za.blog");
		sce.getServletContext().setAttribute("my.application.emf", emf);
		JPAEMFProvider.setEmf(emf);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);
		EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute("my.application.emf");
		if (emf != null) {
			emf.close();
		}
	}
}