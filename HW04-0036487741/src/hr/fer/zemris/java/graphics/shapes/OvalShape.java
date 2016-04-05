package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * OvalShape is an abstract shape that can have 1 or 2 axes of symmetry. If it
 * has 1 axis of symmetry it is a circle and if it has 2 axes it is an ellipse.
 * 
 * @author Ante Spajic
 *
 */
public abstract class OvalShape extends GeometricShape {

	protected int cx;
	protected int cy;
	protected int radiusX;
	protected int radiusY;

	/**
	 * This protected constructor is used to create a parent class to the
	 * classes that extend this shape.
	 * 
	 * @param cx
	 *            Center X coordinate
	 * @param cy
	 *            Center Y coordinate
	 * @param radiusX
	 *            X axis radius of this shape.
	 * @param radiusY
	 *            Y axis radius of this shape.
	 */
	protected OvalShape(int cx, int cy, int radiusX, int radiusY) {
		super();
		this.cx = cx;
		this.cy = cy;
		this.radiusX = radiusX;
		this.radiusY = radiusY;
	}

	/**
	 * Draws an OvalShape to provided raster.
	 */
	@Override
	public void draw(BWRaster r) {
		int width = r.getWidth();
		int height = r.getHeight();

		if (cx + radiusX < 0 || cy + radiusY < 0 || cy - radiusY > height
				|| cx - radiusX > width) {
			return;
		}
		int xStart;
		if (cx - radiusX > 0) {
			xStart = cx - radiusX;
		} else {
			xStart = 0;
		}
		int yStart;
		if (cy - radiusY > 0) {
			yStart = cy - radiusY;
		} else {
			yStart = 0;
		}

		for (int i = xStart; i < xStart + 2 * radiusX && i < width; i++) {
			for (int j = yStart; j < yStart + 2 * radiusY && j < height; j++) {
				if (this.containsPoint(i, j)) {
					r.turnOn(i, j);
				}
			}
		}
	}

	/**
	 * Ellipse specific implementation of checking whether a point is contained
	 * by the ellipse. Check is done by the formula specified in accepted answer
	 * on <a
	 * href="http://math.stackexchange.com/questions/76457/check-if-a-point-is-
	 * within-an-ellipse#76463"> This site </a>
	 * 
	 */
	@Override
	public boolean containsPoint(int x, int y) {
		int dx = x - cx;
		int dy = y - cy;
		int rY = radiusY * radiusY;
		int rX = radiusX * radiusX;
		return (dx * dx * rY + dy * dy * rX) <= rY + rX;
	}

	/**
	 * Returns the center's X coordinate of this OvalShape
	 * 
	 * @return the cx
	 */
	public int getCx() {
		return cx;
	}

	/**
	 * Set a new center X coordinate of this OvalShape
	 * 
	 * @param cx
	 *            the cx to set
	 */
	public void setCx(int cx) {
		this.cx = cx;
	}

	/**
	 * Returns the center's Y coordinate of this OvalShape
	 * 
	 * @return the cy
	 */
	public int getCy() {
		return cy;
	}

	/**
	 * Set a new center Y coordinate of this OvalShape
	 * 
	 * @param cy
	 *            the cy to set
	 */
	public void setCy(int cy) {
		this.cy = cy;
	}

	/**
	 * Returns the ellipse radius on X axis
	 * 
	 * @return the radiusX
	 */
	public int getRadiusX() {
		return radiusX;
	}

	/**
	 * Sets a new radius of the ellipse on X axis.
	 * 
	 * @param radiusX
	 *            the radiusX to set
	 */
	public void setRadiusX(int radiusX) {
		this.radiusX = radiusX;
	}

	/**
	 * Returns the ellipse radius on Y axis
	 * 
	 * @return the radiusY
	 */
	public int getRadiusY() {
		return radiusY;
	}

	/**
	 * Sets a new radius of the ellipse on Y axis.
	 * 
	 * @param radiusY
	 *            the radiusY to set
	 */
	public void setRadiusY(int radiusY) {
		this.radiusY = radiusY;
	}

}
