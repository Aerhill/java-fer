package hr.fer.zemris.java.lir.prob3;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/nabaviHistogram")
public class CrtajHistogram extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		double omC = (double) req.getServletContext().getAttribute("omC");
		double omR = (double) req.getServletContext().getAttribute("omR");
		double omT = (double) req.getServletContext().getAttribute("omT");
		
		BufferedImage bImage = new BufferedImage(300,300,BufferedImage.TYPE_INT_RGB);
		
		Histogram hg = new Histogram(omC, omR, omT);
		
		System.out.println(omC);
		System.out.println(omR);
		System.out.println(omT);
		
		
		hg.paintComponent(bImage.createGraphics());
		ImageIO.write(bImage, "png", resp.getOutputStream());
	}
}
