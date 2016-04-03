package hr.fer.zemris.java.graphics.shapes;

/**
 * 
 * 
 * @author Ante Spajic
 *
 */
public class Rectangle extends GeometricShape {

	private int x;
	private int y;
	private int height;
	private int width;
	
	public Rectangle(int x, int y, int height, int width) {
		if (height <= 0 || width <= 0) {
			throw new IllegalArgumentException("Dimension of rectangle cannot be negative");
		}
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public boolean containsPoint(int x, int y) {
		// TODO
		return false;
	}

}
