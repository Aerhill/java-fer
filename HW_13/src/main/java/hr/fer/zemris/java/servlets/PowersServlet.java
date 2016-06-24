package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This servlet generates an .xls file that contains numbers from a to b and
 * their n-th powers.
 * 
 * @author Ante Spajic
 *
 */
@WebServlet(urlPatterns = { "/powers" })
public class PowersServlet extends HttpServlet {
	private static final long serialVersionUID = -6039845942760539452L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			int a = 0;
			int b = 0;
			int n = 0;
			a = Integer.parseInt(req.getParameter("a"));
			b = Integer.parseInt(req.getParameter("b"));
			n = Integer.parseInt(req.getParameter("n"));
			if ((a > 100 || a < -100) || (b > 100 || b < -100) || (n < 1 || n > 5)) {
				req.getSession().setAttribute("errorMessage", "Atribut van dopuštenih granica");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			if (a > b) {
				b = a + b;
				a = b - a;
				b = b - a;
			}
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition", "attachment; filename=POTENCIJE.xls");
			HSSFWorkbook hwb = new HSSFWorkbook();
			for (int i = 1; i <= n; i++) {
				HSSFSheet sheet = hwb.createSheet("Sheet " + i);
				HSSFRow rowhead = sheet.createRow(0);
				rowhead.createCell(0).setCellValue("Number");
				String power = getNumber(i);
				rowhead.createCell(1).setCellValue(power + " power");
				for (int k = 1, j = a; j < b; j++, k++) {
					HSSFRow row = sheet.createRow(k);
					row.createCell(0).setCellValue(j);
					row.createCell(1).setCellValue(Math.pow(j, i));
				}
			}
			hwb.write(resp.getOutputStream());
			hwb.close();
		} catch (NumberFormatException e) {
			req.getSession().setAttribute("errorMessage", "Why you do that");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		} catch (Exception ex) {
		}
	}

	/**
	 * Returns ordinal number string from provided number.
	 * 
	 * @param i
	 *            number to turn into string
	 * @return Ordinal number string
	 */
	private String getNumber(int i) {
		if (i == 1) {
			return "1-st";
		} else if (i == 2) {
			return "2-nd";
		} else if (i == 3) {
			return "3-rd";
		} else {
			return i + "-th";
		}
	}

}
