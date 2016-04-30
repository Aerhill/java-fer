package hr.fer.zemris.java.tecaj.hw6.demo5;

/**
 * <tt>StudentRecord</tt> represents information sheet of one student for one
 * class.
 * 
 * @author Ante Spajic
 *
 */
public class StudentRecord {

	// these are self explanatory
	private String jmbag;
	private String prezime;
	private String ime;
	private double brojBodMI;
	private double brojBodZI;
	private double brojBodLV;
	private int ocjena;

	/**
	 * Public constructor receives all necessary information about student's
	 * participation in one class.
	 * 
	 * @param jmbag
	 *            Students JMBAG
	 * @param prezime
	 *            Students last name
	 * @param ime
	 *            Students first name
	 * @param brojBodMI
	 *            score on Meduispit
	 * @param brojBodZI
	 *            score on Zavrsni ispit
	 * @param brojBodLV
	 *            score on Laboratoriji
	 * @param ocjena
	 *            final grade
	 */
	public StudentRecord(String jmbag, String prezime, String ime, double brojBodMI, double brojBodZI, double brojBodLV,
			int ocjena) {
		super();
		this.jmbag = jmbag;
		this.prezime = prezime;
		this.ime = ime;
		this.brojBodMI = brojBodMI;
		this.brojBodZI = brojBodZI;
		this.brojBodLV = brojBodLV;
		this.ocjena = ocjena;
	}

	/**
	 * Returns students jmbag
	 * 
	 * @return students jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns students surname
	 * 
	 * @return students surname
	 */
	public String getPrezime() {
		return prezime;
	}

	/**
	 * Returns the name of student
	 * 
	 * @return students name
	 */
	public String getIme() {
		return ime;
	}

	/**
	 * Returns the score on midterm
	 * 
	 * @return score on midterm
	 */
	public double getBrojBodMI() {
		return brojBodMI;
	}

	/**
	 * Returns the score on finals
	 * 
	 * @return Finals score
	 */
	public double getBrojBodZI() {
		return brojBodZI;
	}

	/**
	 * Returns the score on lab.
	 * 
	 * @return Lab score.
	 */
	public double getBrojBodLV() {
		return brojBodLV;
	}

	/**
	 * Returns the final grade.
	 * 
	 * @return final grade
	 */
	public int getOcjena() {
		return ocjena;
	}

	@Override
	public String toString() {
		return ime + " " + prezime + " ukBroj bod " + (brojBodLV + brojBodMI + brojBodZI) + " ocjena " + ocjena;
	}
}
