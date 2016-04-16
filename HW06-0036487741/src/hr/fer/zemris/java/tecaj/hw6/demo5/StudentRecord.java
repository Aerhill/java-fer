package hr.fer.zemris.java.tecaj.hw6.demo5;

public class StudentRecord {

	private String jmbag;
	private String prezime;
	private String ime;
	private double brojBodMI;
	private double brojBodZI;
	private double brojBodLV;
	private int ocjena;

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

	public String getJmbag() {
		return jmbag;
	}

	public String getPrezime() {
		return prezime;
	}

	public String getIme() {
		return ime;
	}

	public double getBrojBodMI() {
		return brojBodMI;
	}

	public double getBrojBodZI() {
		return brojBodZI;
	}

	public double getBrojBodLV() {
		return brojBodLV;
	}

	public int getOcjena() {
		return ocjena;
	}

	@Override
	public String toString() {
		return ime + " " + prezime + " ukBroj bod " + (brojBodLV + brojBodMI + brojBodZI) + " ocjena " + ocjena;
	}
}
