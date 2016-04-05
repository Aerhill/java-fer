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

	protected OvalShape(int cx, int cy, int radiusX, int radiusY) {
		super();
		this.cx = cx;
		this.cy = cy;
		this.radiusX = radiusX;
		this.radiusY = radiusY;
	}

	@Override
	public void draw(BWRaster r) {
		// TODO
		super.draw(r);
	}
	
	/**
	 * @return the cx
	 */
	public int getCx() {
		return cx;
	}

	/**
	 * @param cx
	 *            the cx to set
	 */
	public void setCx(int cx) {
		this.cx = cx;
	}

	/**
	 * @return the cy
	 */
	public int getCy() {
		return cy;
	}

	/**
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
		return (dx*dx * rY + dy*dy * rX) <= rY + rX;
	}
}
