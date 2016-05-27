package hr.fer.zemris.java.gui.charts;

/**
 * The Class XYValue that represents coordinates inside a barchart.
 */
public class XYValue implements Comparable<XYValue> {

	/** The x coordinate. */
	private int x;

	/** The y coordinate. */
	private int y;

	/**
	 * Instantiates a new XY value.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	@Override
	public int compareTo(XYValue o) {
		if (this.x == o.x)
			return 0;
		else if (this.x > o.x)
			return 1;
		else
			return -1;
	}
}
