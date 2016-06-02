package hr.fer.zemris.java.webserver;

/**
 * The Interface IWebWorker is implemented by worker classes that are invoked by
 * the server after the proper request asking for that operation has been
 * received. Server routes the work to the class that takes the context and
 * executes its job and delivers the result to client(browser).
 * 
 * @author Ante Spajic
 */
public interface IWebWorker {

	/**
	 * Process the received request and extract all the necessary info from it
	 * and execute the action and deliver the result to client.
	 *
	 * @param context
	 *            the context with necessary data to do necessary job
	 */
	void processRequest(RequestContext context);
}
