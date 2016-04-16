package hr.fer.zemris.java.tecaj.hw5.db.getter;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Strategy to obtain the FirstName field from a {@link StudentRecord}
 * 
 * @author Ante Spajic
 */
public class FirstNameFieldGetter implements IFieldValueGetter {

	@Override
	public String get(StudentRecord record) {
		if (record == null) {
			throw new IllegalArgumentException("Record must not be null.");
		}
		return record.getFirstName();
	}

}
