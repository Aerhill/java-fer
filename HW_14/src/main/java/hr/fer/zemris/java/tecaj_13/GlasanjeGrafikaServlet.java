package hr.fer.zemris.java.tecaj_13;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * This servlet creates a png chart of votes and provides it to action caller.
 * 
 * @author Ante Spajic
 *
 */
@WebServlet(urlPatterns = { "/glasanje-grafika" })
public class GlasanjeGrafikaServlet extends HttpServlet {
	private static final long serialVersionUID = 8123787877398766183L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OutputStream out = resp.getOutputStream();
		resp.setContentType("image/png");
		List<PollOption> opcije = DAOProvider.getDao().dohvatiOpcije((Long)req.getSession().getAttribute("pollID"));
		JFreeChart chart = ChartFactory.createPieChart3D("Voting",
				createDataset(opcije), true, true, false);
		ChartUtilities.writeChartAsPNG(out, chart, 400, 400);
	}

	private PieDataset createDataset(List<PollOption> options) {
		DefaultPieDataset result = new DefaultPieDataset();
		for (PollOption option : options) {
			result.setValue(option.getOptionTitle(), option.getVotesCount());
		}
		return result;
	}
}
