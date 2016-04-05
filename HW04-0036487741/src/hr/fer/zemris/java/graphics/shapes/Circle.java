package hr.fer.zemris.java.graphics.shapes;

/**
 * Circle is an implementation of OvalShape with 1 axis of symmetry. This class
 * also has an extra method to change the radius of circle which user expects
 * because he gave only 1 radius argument in constructor and should be given the
 * option to set it and get it with appropriate getter methods.
 * 
 * @author Ante Spajic
 *
 */
public class Circle extends OvalShape {

	/**
	 * Public constructor for Circle that takes in 3 arguments, x and y
	 * coordinates of center and a radius of this circle.
	 * 
	 * @param cx
	 * @param cy
	 * @param radius
	 */
	public Circle(int cx, int cy, int radius) {
		super(cx, cy, radius, radius);
	}

	/**
	 * Sets the new radius of this circle.
	 * 
	 * @param radius
	 *            The new radius of the circle.
	 */
	public void setRadius(int radius) {
		this.radiusX = radius;
		this.radiusY = radius;
	}

	/**
	 * Returns the radius of this circle.
	 * 
	 * @return The radius of circle.
	 */
	public int getRadius() {
		return radiusX;
	}

	@Override
	public void setRadiusX(int radiusX) {
		setRadius(radiusX);
	}

	@Override
	public void setRadiusY(int radiusY) {
		setRadius(radiusY);
	}
}
