package hr.fer.zemris.java.tecaj_11.servleti;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrviServlet extends HttpServlet {
	private static final long serialVersionUID = 8265578904075070081L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8"); 
		PrintWriter out = resp.getWriter();
		out.write("<html><body><h1>Prva aplikacija </h1>");
		out.write("<p>Dobrodošli u prvu web-aplikaciju!!!");
		out.write("</body></html>");
	}
}
