package hr.fer.zemris.java.zi.prob1.model;

import java.awt.Color;

import hr.fer.zemris.java.zi.prob1.Krug;

public interface ModelCrteza {
	
	// dodaje novi krug u model:
	void dodajKrug(int cx, int cy, int r, Color obrub, Color ispuna);

	// vraća krug koji model pamti pod navedenim rednim brojem:
	Krug dohvati(int index);

	// vraća broj krugova u modelu:
	int brojKrugova();

	// briše iz modela zadani krug:
	void ukloniKrug(int index);

	// postavi selektirani krug (samo jedan može biti selektiran); -1 postavlja
	// da ništa nije selektirano:
	void postaviSelektirani(int index);

	// vraća indeks selektiranog kruga ili -1 ako ništa nije selektirano:
	int dohvatiIndeksSelektiranogKruga();

	// dohvaća redni broj kruga najbližeg(1); vidi pojašnjenje ispod predanoj
	// točki:
	int najblizi(int tx, int ty);
	
	void addListener(ModelCrtezaListener listener);
	
	void removeListener(ModelCrtezaListener listener);
}
