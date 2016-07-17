package hr.fer.zemris.java.lir.prob1;

public class Triangle {

	private int hypo;
	private int kat;
	public Triangle(int hypo, int kat) {
		super();
		this.hypo = hypo;
		this.kat = kat;
	}
	
	public double getPovrsina() {
		double c = Math.sqrt(hypo*hypo - kat*kat);
		return (c * kat)/2;
	}
}
