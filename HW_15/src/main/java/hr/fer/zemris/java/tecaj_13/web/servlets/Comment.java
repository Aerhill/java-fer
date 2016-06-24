package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * This servlet handles comment posting, it is called when a new comment is
 * posted, it fetches the comment adds it to his parent blogentry list and
 * stores it into a database.
 * 
 * @author Ante Spajic
 *
 */
@WebServlet(urlPatterns = "/servleti/comment")
public class Comment extends HttpServlet {
	private static final long serialVersionUID = 1869242672654445886L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String eid = req.getParameter("eid");
		String email = req.getParameter("email");
		String message = req.getParameter("message");
		if (email.trim().isEmpty() || message.trim().isEmpty()) {
			resp.sendError(400, "Invalid comment");
		}
		Long id = null;
		try {
			id = Long.valueOf(eid);
		} catch (Exception ignorable) {
		}
		if (id != null) {
			BlogComment comment = new BlogComment();
			comment.setUsersEMail(email);
			comment.setMessage(message);
			comment.setPostedOn(new Date());
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
			comment.setBlogEntry(entry);
			entry.getComments().add(comment);
			DAOProvider.getDAO().addNewComment(comment);
			DAOProvider.getDAO().updateBlogEntry(entry);
		} else {
			req.getRequestDispatcher("main").forward(req, resp);
			return;
		}
		resp.sendRedirect("author/" + req.getParameter("nick") + "/" + eid);
	}
}
