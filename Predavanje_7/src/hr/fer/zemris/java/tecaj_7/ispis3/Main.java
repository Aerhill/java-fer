package hr.fer.zemris.java.tecaj_7.ispis3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	static class Upis {
		Predmet predmet;
		Student student;
		public Upis(Predmet predmet, Student student) {
			super();
			this.predmet = predmet;
			this.student = student;
		}
	}

	static class Predmet {
		String sifra;
		String naziv;
		public Predmet(String sifra, String naziv) {
			super();
			this.sifra = sifra;
			this.naziv = naziv;
		}
	}

	static class Student {
		String jmbag;
		String ime;
		String prezime;
		public Student(String jmbag, String ime, String prezime) {
			super();
			this.jmbag = jmbag;
			this.ime = ime;
			this.prezime = prezime;
		}
	}

	public static void main(String[] args) throws IOException {
		
		if(args.length!=3) {
			System.err.println("Očekujem tri argumenta.");
			System.exit(0);
		}

		Map<String,Student> studentMap = ucitajStudente(args[0]);
		Map<String,Predmet> predmetMap = ucitajPredmete(args[1]);
		List<Upis> upisi = ucitajUpise(args[2], studentMap, predmetMap);
		
		for(Upis u : upisi) {
			System.out.println(String.format("| %-40s | %-20s | %-20s |", u.predmet.naziv, u.student.prezime, u.student.ime));
		}
		
	}

	private static Map<String, Student> ucitajStudente(String fileName) {
		ObradaDatoteke<Map<String, Student>> obrada = new ObradaDatoteke<Map<String, Student>>(){
			
			private Map<String, Student> mapa = new HashMap<>();
			
			@Override
			protected int brojStupaca() {
				return 3;
			}

			@Override
			protected void obradiRedak(String[] elementi) {
				Student s = new Student(elementi[0],elementi[2],elementi[1]);
				mapa.put(s.jmbag, s);
				
			}

			@Override
			protected Map<String, Student> dohvatiRezultat() {
				return mapa;
			}
			
		};
		
		return obrada.obradi(fileName);
	}

	private static List<Upis> ucitajUpise(String fileName,
			Map<String, Student> studentMap, Map<String, Predmet> predmetMap) {
		ObradaDatoteke<List<Upis>> obrada = new ObradaDatoteke<List<Upis>>(){
			
			private List<Upis> upisi = new ArrayList<>();
			
			@Override
			protected int brojStupaca() {
				return 2;
			}

			@Override
			protected void obradiRedak(String[] elementi) {
				Student s = studentMap.get(elementi[0]);
				if(s == null) {
					throw new RuntimeException("Student s jmbagom " +elementi[0] + " ne postoji");
				}
				Predmet p = predmetMap.get(elementi[1]);
				if(p == null) {
					throw new RuntimeException("Predmet sa šifrom " + elementi[1] + " ne postoji");
				}
				upisi.add(new Upis(p,s));
				
			}

			@Override
			protected List<Upis> dohvatiRezultat() {
				return upisi;
			}
			
		};
		
		return obrada.obradi(fileName);
		
	}

	private static Map<String, Predmet> ucitajPredmete(String fileName) {
		ObradaDatoteke<Map<String, Predmet>> obrada = new ObradaDatoteke<Map<String, Predmet>>(){
			
			private Map<String, Predmet> mapa = new HashMap<>();
			
			@Override
			protected int brojStupaca() {
				return 2;
			}

			@Override
			protected void obradiRedak(String[] elementi) {
				Predmet p = new Predmet(elementi[0],elementi[1]);
				mapa.put(p.sifra, p);
				
			}

			@Override
			protected Map<String, Predmet> dohvatiRezultat() {
				return mapa;
			}
			
		};
		
		return obrada.obradi(fileName);
	}

}
