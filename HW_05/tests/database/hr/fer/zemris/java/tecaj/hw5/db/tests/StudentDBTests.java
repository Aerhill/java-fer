package hr.fer.zemris.java.tecaj.hw5.db.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.db.QueryFilter;
import hr.fer.zemris.java.tecaj.hw5.db.StudentDatabase;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.operator.LikeCondition;

public class StudentDBTests {

	private static StudentDatabase database;
	private StudentRecord record = new StudentRecord("0000000048", "Rezić", "Bruno", 5);
	private String[] operators = { ">", "<", "=", "!=", ">=", "<=", "LIKE" };
	static {
		try {
			database = new StudentDatabase(Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8));
		} catch (IOException e) {
			System.out.println("Couldn't load a database for tests");
			System.exit(1);
		}
	}

	@Test
	public void testIndexQuery() {
		assertEquals("Akšamović Marin", database.forJMBAG("0000000001").toString());
		assertEquals("Kos-Grabar Ivo", database.forJMBAG("0000000029").toString());
		assertEquals("Krušelj Posavec Bojan", database.forJMBAG("0000000031").toString());
	}

	@Test
	public void testQuery() {
		QueryFilter q = new QueryFilter("query lastName=\"Rezić\"");
		assertEquals(1, database.filter(q).size());
		assertEquals("Rezić Bruno", database.filter(q).get(0).toString());

		q = new QueryFilter("query                  jmbag>\"0\"");
		assertEquals(63, database.filter(q).size());
	}

	@Test
	public void testQueryWithOperators() {
		for (int i = 0; i < operators.length; i++) {
			database.filter(new QueryFilter("query lastName" + operators[i] + "\"Rezić\""));
			database.filter(new QueryFilter("query firstName	" + operators[i] + "	\"Rezić\""));
			database.filter(new QueryFilter(" 	query 	lastName " + operators[i] + "\"Re		zić\""));
			database.filter(new QueryFilter("query jmbag		" + operators[i] + "\"	Rez*ić\"	"));
			database.filter(new QueryFilter(" query lastName" + operators[i] + "\"Rezić\"			"));
			database.filter(new QueryFilter("query lastName" + operators[i] + "\"R*\"	"));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidQueries() {
		QueryFilter q1 = new QueryFilter("qvery lastName=\"Bog\"");
		QueryFilter q2 = new QueryFilter("query lstName=\"Bog\"");
		QueryFilter q3 = new QueryFilter("query lastName=\"B*\"");
		QueryFilter q4 = new QueryFilter("query lastName<\"B*g\"");
		QueryFilter q5 = new QueryFilter("qvery lastname LIKE \"B**\"");
		QueryFilter[] qs = { q1, q2, q3, q4, q5 };
		for (int i = 0; i < qs.length; i++) {
			database.filter(qs[i]);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void wrongOperandTest() {
		QueryFilter filter = new QueryFilter("query lala=sef");
		assertEquals(filter.accepts(new StudentRecord(null, null, null, 0)), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void wrongArgumentTest() {
		QueryFilter filter = new QueryFilter("query lala!!sef");
		assertEquals(filter.accepts(new StudentRecord(null, null, null, 0)), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void wrongArgumentOperatorTest() {
		QueryFilter filter = new QueryFilter("query lalaXsef");
		assertEquals(filter.accepts(new StudentRecord(null, null, null, 0)), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void wrongArgumentQueryTest() {
		QueryFilter filter = new QueryFilter("querynjo firstName=\"sef\"");
		assertEquals(filter.accepts(new StudentRecord(null, null, null, 0)), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void wrongArgumentIndexQueryTest() {
		QueryFilter filter = new QueryFilter("indexquery firstName=\"sef\"");
		assertEquals(filter.accepts(new StudentRecord(null, null, null, 0)), null);
	}

	@Test
	public void firstNameTest() {
		QueryFilter filter = new QueryFilter("query firstName=\"Bruno\"");
		assertEquals(filter.accepts(record), true);
	}

	@Test
	public void jmbagNameTest() {
		QueryFilter filter = new QueryFilter("query lastName=\"Rezić\"");
		assertEquals(filter.accepts(record), true);
	}

	@Test
	public void jmbagAndFirstNameTest() {
		QueryFilter filter = new QueryFilter("query lastName=\"Rezić\" AND jmbag=\"0000000048\"");
		assertEquals(filter.accepts(record), true);
	}

	@Test
	public void jmbagAndLastNameTest() {
		QueryFilter filter = new QueryFilter("query firstName=\"Bruno\" AND jmbag=\"0000000048\"");
		assertEquals(filter.accepts(record), true);
	}

	@Test
	public void LikeAndLastNameTest() {
		QueryFilter filter = new QueryFilter("query firstName LIKE \"B*\" AND lastName LIKE \"R*ć\"");
		assertEquals(filter.accepts(record), true);
	}

	@Test
	public void LikeAndLastNameRevertTest() {
		QueryFilter filter = new QueryFilter("query firstName =\"Sef\" AND lastName LIKE \"R*ć\"");
		assertEquals(filter.accepts(record), false);
	}

	@Test
	public void lastNameTest() {
		QueryFilter filter = new QueryFilter("query jmbag=\"0000000048\"");
		assertEquals(filter.accepts(record), true);
	}

	@Test
	public void likeComparisonTest() {
		String value = "Ante";
		assertEquals("Expected true", true, new LikeCondition().satisfied(value, "A*"));
	}
}
