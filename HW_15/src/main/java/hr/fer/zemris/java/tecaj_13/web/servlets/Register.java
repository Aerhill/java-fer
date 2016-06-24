package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.servlets.utils.PasswordUtil;

/**
 * Servlet that handles displaying registration form and processing registration forms for new users.
 * 
 * @author Ante Spajic
 *
 */
@WebServlet(urlPatterns = "/servleti/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 3024069739286848952L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/register.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String name = req.getParameter("name");
		String surname = req.getParameter("surname");
		String nick = req.getParameter("neek");
		String email = req.getParameter("email");
		String pass = req.getParameter("pass");
		boolean allGood = true;
		if (name == null || pass == null || surname == null || nick == null || email == null) {
			resp.sendError(400);
			allGood = false;
		}
		if (name.trim().isEmpty()) {
			req.getSession().setAttribute("registrationError", "Name can't be empty");
			allGood = false;
		}
		if (surname.trim().isEmpty()) {
			req.getSession().setAttribute("registrationError", "Surname can't be empty");
			allGood = false;
		}
		if (nick.trim().isEmpty() || DAOProvider.getDAO().getUser(nick) != null) {
			req.getSession().setAttribute("registrationError", "Invalid nick");
			allGood = false;
		}
		if (pass.trim().isEmpty()) {
			req.getSession().setAttribute("registrationError", "Password can't be empty");
			allGood = false;
		}
		if (allGood) {
			BlogUser user = new BlogUser();
			user.setFirstName(name);
			user.setLastName(surname);
			user.setNick(nick);
			user.setEmail(email);
			String hashed = PasswordUtil.hashPassword(pass);
			if (hashed == null) {
				resp.sendError(500);
			}
			user.setPasswordHash(hashed);
			DAOProvider.getDAO().createNewUser(user);
			resp.sendRedirect("../main");
		} else {
			doGet(req,resp);
		}
		
	}

}
