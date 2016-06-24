package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple servlet that redirects from index to proper main page.
 * 
 * @author Ante Spajic
 *
 */
@WebServlet(urlPatterns = { "/", "/index.jsp", "/servleti", "/servleti/" })
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = -2603440439632343203L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("/blog/servleti/main");
	}
}
