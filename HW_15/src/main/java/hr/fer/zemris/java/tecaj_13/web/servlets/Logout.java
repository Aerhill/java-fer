package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that handles the logout process, simply invalidates the session and
 * redirects the user to landing page.
 * 
 * @author Ante Spajic
 *
 */
@WebServlet(urlPatterns = "/servleti/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 8529730865742941617L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		req.getRequestDispatcher("/servleti/main").forward(req, resp);
	}
}
