package hr.fer.zemris.java.tecaj.p5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Demo3 {

	public static class Zaposlenik implements Comparable<Zaposlenik> {
		private String ime;
		private String prezime;
		private String sifra;

		public static final Comparator<Zaposlenik> PO_IMENU = (z1, z2) -> z1.getIme().compareTo(z2.getIme());
		public static final Comparator<Zaposlenik> PO_PREZIMENU = (z1, z2) -> z1.getPrezime()
				.compareTo(z2.getPrezime());
		public static final Comparator<Zaposlenik> PO_SIFRI = (z1, z2) -> z1.getSifra().compareTo(z2.getSifra());

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
			if (!(obj instanceof Zaposlenik))
				return false;
			Zaposlenik drugi = (Zaposlenik) obj;
			return this.sifra.equals(drugi.sifra);
		}

		@Override
		public int compareTo(Zaposlenik o) {
			return -this.sifra.compareTo(o.sifra);
		}

	}

	private static class MojKomparator implements Comparator<Zaposlenik> {

		@Override
		public int compare(Zaposlenik o1, Zaposlenik o2) {
			return o1.getIme().substring(1).compareTo(o2.getIme().substring(1));
		}
	}

	private static class KomparatorPoImenu implements Comparator<Zaposlenik> {

		@Override
		public int compare(Zaposlenik o1, Zaposlenik o2) {
			return o1.getIme().compareTo(o2.getIme());
		}
	}

	private static class KomparatorPoPrezimenu implements Comparator<Zaposlenik> {

		@Override
		public int compare(Zaposlenik o1, Zaposlenik o2) {
			return o1.getPrezime().compareTo(o2.getPrezime());
		}
	}

	private static class OkreniKomparator<Z> implements Comparator<Z> {

		private Comparator<Z> original;

		public OkreniKomparator(Comparator<Z> original) {
			super();
			this.original = original;
		}

		@Override
		public int compare(Z o1, Z o2) {
			return -original.compare(o1, o2);
		}

	}

	private static class KompozitniKomparator<Z> implements Comparator<Z> {

		private List<Comparator<Z>> komparatori;

		@SafeVarargs
		public KompozitniKomparator(Comparator<Z> prvi, Comparator<Z>... ostali) {
			komparatori = new ArrayList<>();
			komparatori.add(prvi);
			for (Comparator<Z> k : ostali) {
				komparatori.add(k);
			}
		}

		@Override
		public int compare(Z o1, Z o2) {
			for (Comparator<Z> comparator : komparatori) {
				int rez = comparator.compare(o1, o2);
				if (rez != 0)
					return rez;
			}
			return 0;
		}
	}

	public static void main(String[] args) {

		Comparator<Zaposlenik> reverzni = Collections.reverseOrder(new KomparatorPoImenu());
		Comparator<Zaposlenik> reverzni2 = new KomparatorPoImenu().reversed();

		Comparator<Zaposlenik> kompozitni = new KomparatorPoImenu().reversed()
				.thenComparing(new KomparatorPoPrezimenu());
		// Set<Zaposlenik> skup = new TreeSet<Zaposlenik>(new
		// KompozitniKomparator<>(
		// new OkreniKomparator<>(new KomparatorPoImenu()),
		// new KomparatorPoPrezimenu()));
		Set<Zaposlenik> skup = new TreeSet<Zaposlenik>(
				Zaposlenik.PO_IMENU.reversed().thenComparing(Zaposlenik.PO_PREZIMENU));

		skup.add(new Zaposlenik("Ante", "Antić", "0001"));
		skup.add(new Zaposlenik("Željko", "Željkić", "0002"));
		skup.add(new Zaposlenik("Janko", "Jankić", "0003"));
		skup.add(new Zaposlenik("Joško", "Joškić", "0004"));

		Zaposlenik zap = new Zaposlenik("Janko", "Jankić", "0001");

		skup.forEach(System.out::println);

		System.out.println("Sadržim " + zap + "? " + skup.contains(zap));
	}
}
