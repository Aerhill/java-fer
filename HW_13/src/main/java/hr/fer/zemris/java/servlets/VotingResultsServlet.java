package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet serves as a band voting result provider. Voting results are
 * pulled from a glasanjerezultati.txt from server.
 * 
 * @author Ante Spajic
 *
 */
@WebServlet(urlPatterns = { "/glasanje-rezultati" })
public class VotingResultsServlet extends HttpServlet {
	private static final long serialVersionUID = 3042154876504834204L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanjerezultati.txt");
		Path file = Paths.get(fileName);
		if (!Files.exists(file)) {
			Files.createFile(file);
		}
		List<Band> rezultati = new ArrayList<>();
		Map<String, Band> bendovi = (Map<String, Band>) req.getSession().getAttribute("bendovi");
		List<Band> best = new ArrayList<>();
		for (String line : Files.readAllLines(file)) {
			String[] pair = line.split("\\t");
			rezultati.add(bendovi.get(pair[0]));
		}
		Band max = bendovi.get("1");
		for (Band band : bendovi.values()) {
			if (band.getVotes() > max.getVotes()) {
				max = band;
			}
		}
		for (Band band : bendovi.values()) {
			if (band.getVotes() == max.getVotes()) {
				best.add(band);
			}
		}
		Collections.sort(rezultati, (b1, b2) -> Integer.compare(b2.getVotes(), b1.getVotes()));
		req.getSession().setAttribute("best", best);
		req.getSession().setAttribute("rezultati", rezultati);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
