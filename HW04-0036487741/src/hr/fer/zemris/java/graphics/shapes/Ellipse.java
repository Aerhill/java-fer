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
		return (Math.pow(x - cx, 2) * Math.pow(radiusY, 2) + Math
				.pow(y - cy, 2) * Math.pow(radiusX, 2)) <= Math.pow(radiusX, 2)
				+ Math.pow(radiusY, 2);
	}

}
