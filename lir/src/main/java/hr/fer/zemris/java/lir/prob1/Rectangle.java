package hr.fer.zemris.java.lir.prob1;

public class Rectangle {

	private int h;
	private int w;
	public Rectangle(int h, int w) {
		super();
		this.h = h;
		this.w = w;
	}
	
	public double getPovrsina() {
		
		
		return (double)h*w;
	}
}
