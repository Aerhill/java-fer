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
		if (radiusX < 1 || radiusY < 1) {
			throw new IllegalArgumentException(
					"Dear user, you probably didn't want to enter negative radius");
		}
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
		int rasterWidth = r.getWidth();
		int rasterHeight = r.getHeight();
		// Shape is not in the display area.
		if (cx < -radiusX || cy < -radiusY || cx > rasterWidth + radiusX
				|| cy > rasterHeight + radiusY) {
			return;
		}

		int fromY = Math.max(0, cy - radiusY);
		int toY = Math.min(cy + radiusY, rasterHeight);
		int fromX = Math.max(0, cx - radiusX);
		int toX = Math.min(cx + radiusX, rasterWidth);
		
		for (int x = fromX; x <= toX; x++) {
			for (int y = fromY; y <= toY; y++) {
				if (containsPoint(x, y)) {
					r.turnOn(x, y);
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
		return (double) (dx * dx) / rX + (double) (dy * dy) / rY <= 1;
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
	 * Returns the OvalShape radius on X axis
	 * 
	 * @return the radiusX
	 */
	public int getRadiusX() {
		return radiusX;
	}

	/**
	 * Sets a new radius of the OvalShape on X axis.
	 * 
	 * @param radiusX
	 *            the radiusX to set
	 */
	public void setRadiusX(int radiusX) {
		this.radiusX = radiusX;
	}

	/**
	 * Returns the OvalShape radius on Y axis
	 * 
	 * @return the radiusY
	 */
	public int getRadiusY() {
		return radiusY;
	}

	/**
	 * Sets a new radius of the OvalShape on Y axis.
	 * 
	 * @param radiusY
	 *            the radiusY to set
	 */
	public void setRadiusY(int radiusY) {
		this.radiusY = radiusY;
	}

}
