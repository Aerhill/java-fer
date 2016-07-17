package hr.fer.zemris.java.zi.prob1.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.zi.prob1.Krug;

public class ModelCrtezaImpl implements ModelCrteza {

	private static double THRESHOLD = 5.0;
	
	
	private List<Krug> krugovi = new ArrayList<>();
	private Krug selected;
	private List<ModelCrtezaListener> listeneri = new ArrayList<>();
	
	@Override
	public void dodajKrug(int cx, int cy, int r, Color obrub, Color ispuna) {
		Krug krug = new Krug(cx,cy,r,obrub,ispuna);
		krugovi.add(krug);
	}

	@Override
	public Krug dohvati(int index) {
		return krugovi.get(index);
	}

	@Override
	public int brojKrugova() {
		return krugovi.size();
	}

	@Override
	public void ukloniKrug(int index) {
		krugovi.remove(index);
		listeneri.forEach(l -> l.notifyNumberChanged(this, krugovi.size()));
	}

	@Override
	public void postaviSelektirani(int index) {
		selected = index == -1 ? null: krugovi.get(index);
		listeneri.forEach(l -> l.notifySelectedChanged(this));
	}

	@Override
	public int dohvatiIndeksSelektiranogKruga() {
		return krugovi.indexOf(selected);
	}

	@Override
	public int najblizi(int tx, int ty) {
		int index = -1;
		for (Krug krug : krugovi) {
			double x = Double.valueOf(tx - krug.getCx());
			double y = Double.valueOf(ty- krug.getCy());
			if(Math.abs(Math.hypot(x, y)-krug.getR()) < THRESHOLD) {
				index = krugovi.indexOf(krug);
			}
		}
		return index;
	}

	@Override
	public void addListener(ModelCrtezaListener listener) {
		listeneri.add(listener);
	}

	@Override
	public void removeListener(ModelCrtezaListener listener) {
		listeneri.remove(listener);
	}
	
}
