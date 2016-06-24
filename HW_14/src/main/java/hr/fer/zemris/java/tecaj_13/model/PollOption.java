package hr.fer.zemris.java.tecaj_13.model;

/**
 * Model opcije ankete. Obični java bean sa getterima i setterima za sva polja
 * koja sadrži unutar baze podataka.
 * 
 * @author Ante Spajic
 *
 */
public class PollOption {

	private long ID;
	private String optionTitle;
	private String optionLink;
	private long pollID;
	private long votesCount;

	public PollOption() {
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getOptionTitle() {
		return optionTitle;
	}

	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	public String getOptionLink() {
		return optionLink;
	}

	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	public long getPollID() {
		return pollID;
	}

	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	public long getVotesCount() {
		return votesCount;
	}

	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

}
