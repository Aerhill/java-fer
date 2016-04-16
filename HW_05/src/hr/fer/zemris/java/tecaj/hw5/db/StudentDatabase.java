package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;

/**
 * Class that represents a simple database filled only with
 * {@link StudentRecord} records inside. This database can be indexqueried on a
 * jmbag with forJmbag method and for all other fields with a filter method that
 * takes an {@link IFilter} to filter this database based on a query.
 * 
 * @author Ante Spajic
 *
 */
public class StudentDatabase {

	/**
	 * Index map for fast retrieving with a jmbag.
	 */
	private SimpleHashtable<String, StudentRecord> index;
	/**
	 * Collection of student records.
	 */
	private List<StudentRecord> records;

	/**
	 * Public constructor that takes a list containing database records that are
	 * indexed on a jmbag and stored inside this class.
	 * 
	 * @param database
	 *            List with lines from a loaded database file.
	 */
	public StudentDatabase(List<String> database) {
		records = new ArrayList<>(database.size());
		index = new SimpleHashtable<>();
		for (String string : database) {
			String[] row = string.split("\\t+");
			if (row.length != 4) {
				throw new IllegalArgumentException("Database is not valid or properly formatted");
			}
 			StudentRecord record = new StudentRecord(row[0], row[1], row[2], Integer.parseInt(row[3]));
			records.add(record);
			index.put(row[0], record);
		}
	}

	/**
	 * Method that performs a fast retrieval from database index with a jmbag
	 * key.
	 * 
	 * @param jmbag
	 *            Jmbag of a student we want to retrieve a record for.
	 * @return record {@link StudentRecord} of a student with provided jmbag or
	 *         null if student with provided jmbag is not stored inside this
	 *         database.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.containsKey(jmbag) ? index.get(jmbag) : null;
	}

	/**
	 * Method that performs a filter on this database and returns records from
	 * this database that meet filtering condition.
	 * 
	 * @param filter
	 *            Filter that we want this this database to be filtered on.
	 * @return list List of records that meet filtering conditions.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredList = new ArrayList<>();
		for (StudentRecord studentRecord : records) {
			if (filter.accepts(studentRecord)) {
				filteredList.add(studentRecord);
			}
		}
		return filteredList;
	}
}
