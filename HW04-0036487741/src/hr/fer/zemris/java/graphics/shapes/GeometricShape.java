package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Abstract class that is parent class to all of the geometric shapes we are
 * going to implement in this homework. Initial shapes that we were supposed to
 * build for this homework were RECTANGLE, SQUARE, ELLIPSE AND CIRCLE that all
 * extend this class.
 * 
 * @author Ante Spajic
 *
 */
public abstract class GeometricShape {

	/**
	 * Default method for drawing the geometric shape to the BWRaster, goes
	 * through whole raster and checks every pixel if it should be turned on.
	 * Quite inefficient and should be overridden with better implementation if
	 * possible.
	 * 
	 * @param r
	 *            Raster to draw the picture on.
	 */
	public void draw(BWRaster r) {
		for (int x = 0, h = r.getHeigth(), w = r.getWidth(); x < h; x++) {
			for (int y = 0; y < w; y++) {
				if (containsPoint(x, y)) {
					r.turnOn(x, y);
				}
			}
		}
	}

	/**
	 * Checks if this geometric shape contains a given point. This method's
	 * implementation differs from geometric shape to geometric shape and it
	 * will be further described in individual implementation.
	 * 
	 * @param x
	 *            X coordinate of a point
	 * @param y
	 *            Y coordinate of a point
	 * @return True if the given point is inside of a geometric shape and false
	 *         otherwise.
	 */
	public abstract boolean containsPoint(int x, int y);
}
