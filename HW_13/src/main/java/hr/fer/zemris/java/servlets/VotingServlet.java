package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet prepares and serves a jsp page on which a band voting is cast.
 * 
 * @author Ante Spajic
 *
 */
@WebServlet(urlPatterns = { "/glasanje" })
public class VotingServlet extends HttpServlet {
	private static final long serialVersionUID = -2086931568597427727L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		Map<String, Band> bendovi = new HashMap<>();
		for (String line : lines) {
			String[] pair = line.split("\\t");
			Band band = new Band();
			band.setId(pair[0]);
			band.setName(pair[1]);
			band.setLink(pair[2]);
			bendovi.put(band.getId(), band);
		}
		req.getSession().setAttribute("bendovi", bendovi);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
