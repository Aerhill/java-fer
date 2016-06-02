package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class EchoParams simply outputs back to user parameters it obtained, in
 * form of an HTML table.
 * 
 * @author Ante Spajic
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		try {
			if (context.getParameterNames().isEmpty()) {
				context.write("<p style = 'font-size:32;color:red;'>I expected parameters, yo");
				return;
			}
			context.write("<table>");
			for (String name : context.getParameterNames()) {
				context.write("<tr><td>" + name + "</td><td>" + context.getParameter(name) + "</td></tr>");
			}
			context.write("</table>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
