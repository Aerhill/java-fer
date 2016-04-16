package hr.fer.zemris.java.tecaj.p5;

import java.util.HashSet;
import java.util.Set;

public class Demo2 {

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
		
		@Override
		public int hashCode() {
			return sifra.hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Zaposlenik)) return false;
			Zaposlenik drugi = (Zaposlenik)obj;
			return this.sifra.equals(drugi.sifra);
		}
	}
	
	public static void main(String[] args) {
		
		Set<Zaposlenik> skup = new HashSet<>();
		
		skup.add(new Zaposlenik("Ante", "Antić", "0001"));
		skup.add(new Zaposlenik("Željko", "Željkić", "0002"));
		skup.add(new Zaposlenik("Janko", "Jankić", "0003"));
		skup.add(new Zaposlenik("Joško", "Joškić", "0004"));
		
		Zaposlenik zap = new Zaposlenik("Janko", "Jankić", "0001");
		
		skup.forEach(System.out::println);
		
		System.out.println("Sadržim " + zap + "? " + skup.contains(zap));
	}
}
