package hr.fer.zemris.java.tecaj2;

public class Pravokutnik extends GeometrijskiLik {

	private int vrhX;
	private int vrhY;
	private int sirina;
	private int visina;

	public Pravokutnik(String ime, int vrhX, int vrhY, int sirina, int visina) {
		super(ime);
		this.vrhX = vrhX;
		this.vrhY = vrhY;
		this.sirina = sirina;
		this.visina = visina;
	}

	@Override
	public double getPovrsina() {
		return sirina * visina;
	}

	@Override
	public double getOpseg() {
		return 2 * (sirina + visina);
	}

	public int getVrhX() {
		return vrhX;
	}

	public int getVrhY() {
		return vrhY;
	}

	public int getSirina() {
		return sirina;
	}

	public int getVisina() {
		return visina;
	}

}
