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

	/**
	 * Public constructor that creates a new rectangle with provided attributes.
	 * 
	 * @param x
	 *            Top left x coordinate
	 * @param y
	 *            Top left y coordinate
	 * @param height
	 *            Desired height of this rectangle
	 * @param width
	 *            Desired width of this rectangle
	 */
	public Rectangle(int x, int y, int height, int width) {
		if (height <= 0 || width <= 0) {
			throw new IllegalArgumentException(
					"Dimension of rectangle cannot be negative");
		}
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}

	/**
	 * Returns the x coordinate of this rectangle.
	 * 
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets a new top left x coordinate of this rectangle
	 * 
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the y coordinate of this rectangle.
	 * 
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets a new top left y coordinate of this rectangle.
	 * 
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns the height of this rectangle.
	 * 
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets a new height of this rectangle.
	 * 
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Returns the width of this rectangle
	 * 
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets a new width of this rectangle.
	 * 
	 * @param width
	 *            the width to set
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
