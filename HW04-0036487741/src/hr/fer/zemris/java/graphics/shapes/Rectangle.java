package hr.fer.zemris.java.graphics.shapes;

/**
 * This class is an implementation of an {@link AbstractRectangle}.
 * 
 * @author Ante Spajic
 *
 */
public class Rectangle extends AbstractRectangle {

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
		super(x, y, height, width);
	}

}
