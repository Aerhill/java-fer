package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.OutputStream;

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

/**
 * This servlet generates a simple pie chart with static information.
 * 
 * @author Ante Spajic
 *
 */
@WebServlet(urlPatterns = {"/report", "/showreport"})
public class ReportChartServlet extends HttpServlet {
	private static final long serialVersionUID = -9008018144378086360L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getRequestURL().toString().endsWith("showreport")) {
			req.getRequestDispatcher("/WEB-INF/pages/report.jsp").forward(req, resp);
		} else {
			OutputStream out = resp.getOutputStream();
	        resp.setContentType("image/png");
	        JFreeChart chart = ChartFactory.createPieChart3D("OS usage",
					createDataset(),
					true,
					true, false);
	        ChartUtilities.writeChartAsPNG(out, chart, 400, 300);
		}
	}
	
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 29);
		result.setValue("Mac", 20);
		result.setValue("Windows", 51);
		return result;

	}

}
