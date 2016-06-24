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
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * Servlet koji dohvaća opcije za pojedinu anketu koju je korisnik zatražio.
 * Dohvaća te opcije iz baze podataka i priprema ih za ispisivanje u html
 * dokumentu.
 * 
 * @author Ante Spajic
 *
 */
@WebServlet("/glasanje")
public class ListajOpcije extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID = Long.parseLong(req.getParameter("pollID"));
		List<PollOption> opcije = DAOProvider.getDao().dohvatiOpcije(pollID);
		Poll trenutnaAnketa = DAOProvider.getDao().dohvatiAnketu(pollID);
		req.getSession().setAttribute("currPoll", trenutnaAnketa);
		req.getSession().setAttribute("pollID", pollID);
		req.setAttribute("opcije", opcije);
		req.getRequestDispatcher("/WEB-INF/pages/Anketa.jsp").forward(req, resp);
	}

}