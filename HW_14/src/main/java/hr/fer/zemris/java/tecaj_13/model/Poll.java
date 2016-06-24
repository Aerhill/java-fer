package hr.fer.zemris.java.tecaj_13.model;

/**
 * Model ankete za ovu aplikaciju. Sadrži settere i gettere za sve stupce koje
 * ima u bazi podataka.
 * 
 * @author Ante Spajic
 *
 */
public class Poll {

	private long ID;
	private String title;
	private String message;

	public Poll() {
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
