package hr.fer.zemris.java.tecaj_13;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;

/**
 * Servlet koji dohvaća ankete iz baze podataka i priprema ih za posluživanje u
 * html dokumentu.
 * 
 * @author Ante Spajic
 *
 */
@WebServlet("/listajAnkete")
public class ListajAnkete extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> ankete = DAOProvider.getDao().dohvatiAnkete();
		req.setAttribute("ankete", ankete);
		req.getRequestDispatcher("/WEB-INF/pages/Ankete.jsp").forward(req, resp);
	}

}