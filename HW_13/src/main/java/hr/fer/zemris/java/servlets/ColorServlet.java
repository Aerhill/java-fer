package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet handles color actions such as displaying menu containing
 * available colors and setting them as a bacckground.
 * 
 * @author Ante Spajic
 *
 */
@WebServlet(urlPatterns = { "/setcolor", "/colors" })
public class ColorServlet extends HttpServlet {
	private static final long serialVersionUID = -4016715543201066201L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!req.getRequestURL().toString().endsWith("colors")) {
			String color = req.getParameter("color") == null ? "white" : (String) req.getParameter("color");
			if (!color.equals("red") && !color.equals("green") && !color.equals("cyan") && !color.equals("white")) {
				color = "white";
			}
			req.getSession(true).setAttribute("pickedBgCol", color);
			req.getRequestDispatcher("index.jsp").forward(req, resp);
		} else {
			req.getRequestDispatcher("/WEB-INF/pages/colors.jsp").forward(req, resp);
		}

	}
}