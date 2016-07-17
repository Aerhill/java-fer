package hr.fer.zemris.java.zi.prob1;

import java.awt.Color;

public class Krug {

	private int cx;
	private int cy;
	private int r;
	private Color obrub;
	private Color ispuna;
	
	public Krug(int cx, int cy, int r, Color obrub, Color ispuna) {
		this.cx = cx;
		this.cy = cy;
		this.r = r;
		this.obrub = obrub;
		this.ispuna = ispuna;
	}

	public int getCx() {
		return cx;
	}

	public void setCx(int cx) {
		this.cx = cx;
	}

	public int getCy() {
		return cy;
	}

	public void setCy(int cy) {
		this.cy = cy;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public Color getObrub() {
		return obrub;
	}

	public void setObrub(Color obrub) {
		this.obrub = obrub;
	}

	public Color getIspuna() {
		return ispuna;
	}

	public void setIspuna(Color ispuna) {
		this.ispuna = ispuna;
	}

	
}
