package hr.fer.zemris.java.tecaj.hw5.db.getter;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Implementation of a {@link IFieldValueGetter} strategy pattern that returns
 * StudentRecord jmbag string value.
 * 
 * @author Ante Spajic
 *
 */
public class JmbagFieldGetter implements IFieldValueGetter {

	@Override
	public String get(StudentRecord record) {
		if (record == null) {
			throw new IllegalArgumentException("Record must not be null.");
		}
		return record.getJmbag();
	}

}
