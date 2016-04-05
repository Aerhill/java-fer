package hr.fer.zemris.java.graphics.shapes;

/**
 * Square is an implementation of {@link AbstractRectangle} that has equal width
 * and height. It also has 1 extra method compared to AbstractRectangle,
 * setSize(), this method is here because user gives the size argument in
 * constructor and he expects that argument should have according setters and
 * getters. That method changes both width and height of AbstractRectangle.
 * 
 * @author Ante Spajic
 *
 */
public class Square extends AbstractRectangle {

	/**
	 * Public constructor of square which takes in 3 arguments, x and y
	 * coordinates of square's top left corner and square edge size.
	 * 
	 * @param x
	 *            Top left X coordinate.
	 * @param y
	 *            Top left Y coordinate.
	 * @param size
	 *            Size of the edge of square.
	 */
	public Square(int x, int y, int size) {
		super(x, y, size, size);
	}

	/**
	 * Sets a new edge size of this square.
	 * 
	 * @param width
	 *            the width to set
	 */
	public void setSize(int size) {
		this.height = size;
		this.width = size;
	}

	/**
	 * Returns the edge size of this square.
	 * 
	 * @return Edge size.
	 */
	public int getSize() {
		return this.width;
	}

	@Override
	public void setHeight(int height) {
		setSize(height);
	}

	@Override
	public void setWidth(int width) {
		setSize(width);
	}

}
