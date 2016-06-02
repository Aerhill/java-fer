package hr.fer.zemris.java.webserver.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Tests for request context, due to lack of creativity and time this is all I
 * came up with. Sorry if you are not satisfied. :(
 * 
 * @author Ante Spajic
 *
 */
public class RequestContextTests {

	RequestContext rc;
	ByteArrayOutputStream output;

	{
		Map<String, String> parameters = new HashMap<>();
		parameters.put("param1", "value1");
		parameters.put("param2", "value2");
		output = new ByteArrayOutputStream();
		rc = new RequestContext(output, parameters, new HashMap<>(), new ArrayList<>());
		rc.setPersistentParameter("user", "Antimond");
		rc.setPersistentParameter("password", "Antimond");
		rc.setTemporaryParameter("a", "1");
		rc.setTemporaryParameter("b", "2");
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorTest() {
		new RequestContext(null, null, null, null);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void immutableSets() {
		Set<String> mutable = rc.getPersistentParameterNames();
		Set<String> immutable = rc.getTemporaryParameterNames();
		Set<String> norm = rc.getParameterNames();
		immutable.add("test1");
		mutable.add("test");
		norm.add("");
	}

	@Test
	public void headerTest() throws IOException {
		rc.write("Test".getBytes(StandardCharsets.UTF_8));
		rc.write("šž.");
		rc.write("Super.".getBytes(StandardCharsets.UTF_8));
		ByteArrayOutputStream expected = new ByteArrayOutputStream();
		String header = "HTTP/1.1 200 OK\r\n" + "Content-Type:text/html; charset=UTF-8\r\n" + "\r\n";
		expected.write(header.getBytes(StandardCharsets.ISO_8859_1));
		expected.write("Testšž.Super.".getBytes(StandardCharsets.UTF_8));
		assertEquals(expected.toString(), output.toString());
		output.close();
		expected.close();
	}

	@Test
	public void paramsTest() {
		Set<String> pers = rc.getPersistentParameterNames();
		Set<String> temp = rc.getTemporaryParameterNames();
		Set<String> norm = rc.getParameterNames();
		assertEquals(2, pers.size());
		assertEquals(2, temp.size());
		assertEquals(2, norm.size());
	}

	@Test
	public void getParams() {
		assertEquals("value1", rc.getParameter("param1"));
		assertEquals("Antimond", rc.getPersistentParameter("user"));
		assertEquals("1", rc.getTemporaryParameter("a"));
	}

	@Test
	public void removeParams() {
		Set<String> pers = rc.getPersistentParameterNames();
		Set<String> temp = rc.getTemporaryParameterNames();
		assertEquals(2, pers.size());
		assertEquals(2, temp.size());
		rc.removePersistentParameter("user");
		rc.removeTemporaryParameter("a");
		assertEquals(1, pers.size());
		assertEquals(1, temp.size());
	}

	@Test(expected = RuntimeException.class)
	public void headerGeneratedTest() throws IOException {
		rc.write("Test".getBytes(StandardCharsets.UTF_8));
		rc.write("šž.");
		rc.write("Super.".getBytes(StandardCharsets.UTF_8));
		ByteArrayOutputStream expected = new ByteArrayOutputStream();
		String header = "HTTP/1.1 200 OK\r\n" + "Content-Type:text/html; charset=UTF-8\r\n" + "\r\n";
		expected.write(header.getBytes(StandardCharsets.ISO_8859_1));
		expected.write("Testšž.Super.".getBytes(StandardCharsets.UTF_8));
		assertEquals(expected.toString(), output.toString());
		// this should throw runtime because header has been generated
		rc.setEncoding("UTF-8");

		output.close();
		expected.close();
	}

	@Test
	public void emptySetsTest() {
		RequestContext r = new RequestContext(output, null, null, null);

		assertTrue(r.getPersistentParameterNames().isEmpty());
		assertTrue(r.getParameterNames().isEmpty());
	}
	
	@Test
	public void cookiesTest() throws IOException {
		rc = new RequestContext(output, null, null, null);
		rc.addRCCookie(new RCCookie("user", "Antisa", null, null, null));
		rc.addRCCookie(new RCCookie("user", "Antisa", 3600, null, null));
		rc.addRCCookie(new RCCookie("user", "Antisa", 3600, "fer", null));
		rc.addRCCookie(new RCCookie("user", "Antisa", 3600, "fer", "/"));
		rc.addRCCookie(new RCCookie("sid", "abcd", null, "fer", "/", true));
		
		rc.write("Test.");
		rc.write("Super.".getBytes(StandardCharsets.UTF_8));
		ByteArrayOutputStream expected = new ByteArrayOutputStream();
		String header = "HTTP/1.1 200 OK\r\n"
				+ "Content-Type:text/html; charset=UTF-8\r\n"
				+ "Set-Cookie: user=Antisa\r\n"
				+ "Set-Cookie: user=Antisa; Max-Age=3600\r\n"
				+ "Set-Cookie: user=Antisa; Domain=fer; Max-Age=3600\r\n"
				+ "Set-Cookie: user=Antisa; Domain=fer; Path=/; Max-Age=3600\r\n"
				+ "Set-Cookie: sid=abcd; Domain=fer; Path=/; HttpOnly\r\n"
				+ "\r\n";
		expected.write(header.getBytes(StandardCharsets.ISO_8859_1));
		expected.write("Test.Super.".getBytes(StandardCharsets.UTF_8));

		assertEquals("Sadržaji stremova nisu isti", expected.toString(), output.toString());
		output.close();;
		expected.close();;
	}
}
