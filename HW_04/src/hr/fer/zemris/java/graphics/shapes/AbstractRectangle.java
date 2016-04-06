package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Intermediate class that represents any kind of Rectangle, in our case we have
 * 2 classes, Rectangle and Square, that inherit from this one.
 * 
 * @author Ante Spajic
 *
 */
public abstract class AbstractRectangle extends GeometricShape {

	protected int x;
	protected int y;
	protected int height;
	protected int width;

	protected AbstractRectangle(int x, int y, int height, int width) {
		if (height <= 0 || width <= 0) {
			throw new IllegalArgumentException(
					"Dimension of rectangle cannot be negative");
		}
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}

	@Override
	public void draw(BWRaster r) {
		int rasterWidth = r.getWidth();
		int rasterHeight = r.getHeight();
		if (x + width <= 0 || x >= rasterWidth || y + height <= 0
				|| y >= rasterHeight) {
			return;
		}
		
		int fromX = Math.max(0, x);
		int toX = Math.min(x + width, rasterWidth);
		int fromY = Math.max(0, y);
		int toY = Math.min(y + height, rasterHeight);
		for (int y = fromY; y < toY; y++) {
			for (int x = fromX; x < toX; x++) {
				r.turnOn(x, y);
			}
		}
	};

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
	 * Returns the width of this rectangle
	 * 
	 * @return the width
	 */
	public int getWidth() {
		return width;
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
		if (x < this.x || x >= this.x + width)
			return false;
		if (y < this.y || y >= this.y + height)
			return false;
		return true;
	}

}
