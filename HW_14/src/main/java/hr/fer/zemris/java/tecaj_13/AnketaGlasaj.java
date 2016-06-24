package hr.fer.zemris.java.tecaj_13;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * Servlet koji se brine oko glasanja, ažurira podatke unutar baze podataka
 * nakon što je netko glasovao.
 * 
 * @author Ante Spajic
 *
 */
@WebServlet(urlPatterns = { "/glasaj" })
public class AnketaGlasaj extends HttpServlet {
	private static final long serialVersionUID = 3713467297566940959L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long id = Long.parseLong(req.getParameter("id"));
		DAOProvider.getDao().azurirajAnketu(id);
		long pollID = (Long) req.getSession().getAttribute("pollID");
		List<PollOption> opcije = DAOProvider.getDao().dohvatiOpcije(pollID);
		List<PollOption> best = new ArrayList<>();
		PollOption max = opcije.get(0);
		for (PollOption pollOption : opcije) {
			if (pollOption.getVotesCount() > max.getVotesCount()) {
				max = pollOption;
			}
		}
		for (PollOption pollOption : opcije) {
			if (pollOption.getVotesCount() == max.getVotesCount()) {
				best.add(pollOption);
			}
		}
		Collections.sort(opcije, (b1, b2) -> Long.compare(b2.getVotesCount(), b1.getVotesCount()));
		req.setAttribute("best", best);
		req.setAttribute("opcije", opcije);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
