package hr.fer.zemris.java.tecaj.p5;

import java.util.ArrayList;
import java.util.List;

public class Demo4 {

	public static class Zaposlenik {
		private String ime;
		private String prezime;
		private String sifra;

		public Zaposlenik(String ime, String prezime, String sifra) {
			super();
			this.ime = ime;
			this.prezime = prezime;
			this.sifra = sifra;
		}

		public String getIme() {
			return ime;
		}

		public String getPrezime() {
			return prezime;
		}

		public String getSifra() {
			return sifra;
		}

		@Override
		public String toString() {
			return "(" + sifra + ") " + prezime + ", " + ime;
		}
	}
	
	public static void main(String[] args) {
		
		List<Zaposlenik> lista = new ArrayList<>();
		
		lista.add(new Zaposlenik("Ante", "Antić", "0001"));
		lista.add(new Zaposlenik("Željko", "Željkić", "0002"));
		lista.add(new Zaposlenik("Janko", "Jankić", "0003"));
		lista.add(new Zaposlenik("Joško", "Joškić", "0004"));
	
		List<Zaposlenik> nova = new ArrayList<>(lista);
		nova.removeIf(z -> z.getIme().length() < 5);
	}
}
