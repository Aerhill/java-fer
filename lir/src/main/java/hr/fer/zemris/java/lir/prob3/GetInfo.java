package hr.fer.zemris.java.lir.prob3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.lir.prob1.Racunalo;
import hr.fer.zemris.java.lir.prob1.Racunalo.MyFile;

@WebServlet(urlPatterns="/getInfoForFile")
public class GetInfo extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getParameter("fileName");
		Path p = Paths.get(req.getServletContext().getRealPath("/WEB-INF/files/"+fileName));
		
		String text = new String(Files.readAllBytes(p));
		MyFile f = Racunalo.calculate(p);
		DecimalFormat df = new DecimalFormat("#.###");
		String translation = "C = " + df.format(f.getUkC()) + "\nR = " + df.format(f.getUkR()) + "\nT = "
				+ df.format(f.getUkT());
		req.getServletContext().setAttribute("text", text);
		req.getServletContext().setAttribute("translation", translation);
		req.getServletContext().setAttribute("name", fileName);
		
		double uk = f.ukupna();
		
		double omC = f.getUkC()/uk;
		double omR = f.getUkR()/uk;
		double omT = f.getUkT()/uk;
		
		req.getServletContext().setAttribute("omC", omC);
		req.getServletContext().setAttribute("omR", omR);
		req.getServletContext().setAttribute("omT", omT);
		
		req.getRequestDispatcher("/WEB-INF/display.jsp").forward(req, resp);
	}
}
