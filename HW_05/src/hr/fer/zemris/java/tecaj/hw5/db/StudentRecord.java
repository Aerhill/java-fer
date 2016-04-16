package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * This class represents a single Student record inside a StudentDatabase, this
 * record has jmbag, lastName, firstName and a finalGrade value which can be
 * considered columns inside a database.
 * 
 * @author Ante Spajic
 *
 */
public class StudentRecord {

	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;

	/**
	 * Public constructor used to create a new student record.
	 * 
	 * @param jmbag
	 *            Student unique jmbag.
	 * @param lastName
	 *            Students last name.
	 * @param firstName
	 *            Students first name.
	 * @param finalGrade
	 *            Students final grade.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Student jmbag field getter.
	 * 
	 * @return Returns student unique jmbag.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Student lastName field getter.
	 * 
	 * @return Returns students last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Student firstName field getter.
	 * 
	 * @return Returns students first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Students final grade getter.
	 * 
	 * @return Returns students final grade.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return lastName + " " + firstName;
	}

}
