package hr.fer.zemris.java.tecaj_7.ispis2;

public interface IObrada<T> {

	int brojStupaca();
	void obradiRedak(String[] elementi);
	T dohvatiRezultat();
}
