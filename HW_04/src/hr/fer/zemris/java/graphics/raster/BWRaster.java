package hr.fer.zemris.java.graphics.raster;

/**
 * Class that represents black and white raster, where Black pixels on this
 * raster are turned on and White are turned off. We will represent black pixels
 * with specific char different from a specific char for white pixels.
 * 
 * @author Ante Spajic
 *
 */
public interface BWRaster {

	/**
	 * Returns the width of this raster.
	 * 
	 * @return
	 */
	int getWidth();

	/**
	 * Returns the height of this raster.
	 * 
	 * @return
	 */
	int getHeight();

	/**
	 * Turns off all 'pixels' in this raster.
	 */
	void clear();

	/**
	 * Turns a pixel on or if in flip mode, if the pixel is already turned on it
	 * is turned off.
	 * 
	 * @param x
	 * @param y
	 */
	void turnOn(int x, int y);

	/**
	 * 
	 */
	void turnOff(int x, int y);

	/**
	 * Enables a flip mode, when in flip mode, turnOn method acts as a switch of
	 * pixels, meaning that when you call a turnOn on already turned pixel it
	 * will turn it off.
	 */
	void enableFlipMode();

	/**
	 * Disables flip mode, turnOn and turnOff methods work same no matter how
	 * many times we call it.
	 */
	void disableFlipMode();

	/**
	 * Checks if a pixel with given coordinates (x,y) is turned on.
	 * 
	 * @param x
	 *            X coordinate of a 'pixel' we want to check.
	 * @param y
	 *            Y coordinate of a 'pixel' we want to check.
	 * @return True if a pixel is turned on and false if it is not.
	 */
	boolean isTurnedOn(int x, int y);
}
