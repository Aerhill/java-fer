package hr.fer.zemris.java.tecaj_13;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * This servlet creates a downloadable .xls file containing voting results.
 * 
 * @author Ante Spajic
 *
 */
@WebServlet(urlPatterns = { "/glasanje-xls" })
public class GlasanjeXlsServlet extends HttpServlet {
	private static final long serialVersionUID = -6037088280259593935L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=voting-results.xls");
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("New spreadsheet");
		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Bend");
		rowhead.createCell(1).setCellValue("Broj glasova");
		List<PollOption> rezultati = DAOProvider.getDao().dohvatiOpcije((Long)req.getSession().getAttribute("pollID"));
		int i = 1;
		for (PollOption option : rezultati) {
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(option.getOptionTitle());
			row.createCell(1).setCellValue(option.getVotesCount());
			i++;
		}
		hwb.write(resp.getOutputStream());
		hwb.close();
	}

}
