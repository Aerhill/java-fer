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
 * Servlet that handles login process, processes the login 
 * form and accepts the user if he entered correct data that exists in the database.
 * 
 * @author Ante Spajic
 *
 */
@WebServlet(urlPatterns="/servleti/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = -3372998016205100487L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = req.getParameter("neek");
		String pass = req.getParameter("pass");
		if( nick == null || nick.trim().isEmpty() || pass == null){
			req.getSession().setAttribute("error", "You must enter nick and password");
		} else {
			nick=nick.trim();
			BlogUser enteredUser = DAOProvider.getDAO().getUser(nick);
			if (enteredUser == null) {
				req.getSession().setAttribute("error", "You must register first, that nick does not exists");
			} else if (PasswordUtil.checkPassword(pass, enteredUser.getPasswordHash())) {
				req.getSession().setAttribute("current.user.id", enteredUser.getId());
				req.getSession().setAttribute("current.user.fn", enteredUser.getFirstName());
				req.getSession().setAttribute("current.user.ln", enteredUser.getLastName());
				req.getSession().setAttribute("current.user.nick", enteredUser.getNick());
			} else {
				req.getSession().setAttribute("error", "Wrong password man");
			}
		}
		resp.sendRedirect("../main");
	}
	
}
