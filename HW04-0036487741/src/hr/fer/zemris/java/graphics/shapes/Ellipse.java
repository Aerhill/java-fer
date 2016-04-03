package hr.fer.zemris.java.graphics.shapes;

/**
 * 
 * 
 * @author Ante Spajic
 *
 */
public class Ellipse extends GeometricShape {

	private int cx;
	private int cy;
	private int radiusX;
	private int radiusY;

	public Ellipse(int cx, int cy, int radiusX, int radiusY) {
		super();
		this.cx = cx;
		this.cy = cy;
		this.radiusX = radiusX;
		this.radiusY = radiusY;
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
	 * @return the radiusX
	 */
	public int getRadiusX() {
		return radiusX;
	}

	/**
	 * @param radiusX
	 *            the radiusX to set
	 */
	public void setRadiusX(int radiusX) {
		this.radiusX = radiusX;
	}

	/**
	 * @return the radiusY
	 */
	public int getRadiusY() {
		return radiusY;
	}

	/**
	 * @param radiusY
	 *            the radiusY to set
	 */
	public void setRadiusY(int radiusY) {
		this.radiusY = radiusY;
	}

	@Override
	public boolean containsPoint(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

}
