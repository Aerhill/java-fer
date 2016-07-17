package hr.fer.zemris.java.lir.prob1;

public class Circle {

	private int r;

	public Circle(int r) {
		super();
		this.r = r;
	}
	
	public double getPovrsina() {
		return (double)(r*r*Math.PI);
	}
}
