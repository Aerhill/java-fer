package hr.fer.zemris.java.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener servlet that stores application startup time into application attributes.
 * 
 * @author Ante Spajic
 *
 */
@WebListener
public class Information implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("applicationStart", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {}

}
