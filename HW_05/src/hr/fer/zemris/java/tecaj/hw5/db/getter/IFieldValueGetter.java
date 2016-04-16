package hr.fer.zemris.java.tecaj.hw5.db.getter;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Strategy interface responsible for obtaining requested field value from given
 * {@link StudentRecord}.
 * 
 * @author Ante Spajic
 *
 */
public interface IFieldValueGetter {

	/**
	 * This method is responsible for obtaining this field from given record.
	 * 
	 * @param record
	 *            Record who's field is to be obtained.
	 * @return Value of the field.
	 */
	String get(StudentRecord record);
}
