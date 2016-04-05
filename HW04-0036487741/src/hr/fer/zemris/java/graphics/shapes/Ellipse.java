package hr.fer.zemris.java.graphics.shapes;

/**
 * Ellipse is an oval shape with two axes of symmetry. It is also pure
 * implementation of oval shape since it shares all methods with it.
 * 
 * @author Ante Spajic
 *
 */
public class Ellipse extends OvalShape {

	/**
	 * Public constructor for ellipse that takes center point and two radiuses,
	 * radius on X axis and radius on Y axis. Point is counted from top left
	 * corner of the screen.
	 * 
	 * @param cx
	 * @param cy
	 * @param radiusX
	 * @param radiusY
	 */
	public Ellipse(int cx, int cy, int radiusX, int radiusY) {
		super(cx, cy, radiusX, radiusY);
	}

}
