package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Implementation of RasterView that produces given raster into a String.
 * 
 * @author Ante Spajic
 *
 */
public class StringRasterView implements RasterView {

	/**
	 * Represents a turned on pixel on a raster.
	 */
	private char onChar;
	/**
	 * Represents a turned off pixel on a raster.
	 */
	private char offChar;

	/**
	 * Default constructor that uses default onChar '*' and default offChar '.'.
	 * 
	 */
	public StringRasterView() {
		this('*', '.');
	}

	/**
	 * Public constructor with which you can specify what character you want to
	 * represent a turned on pixel and a character to represent turned off
	 * pixel.
	 * 
	 * @param onChar
	 *            Character to represent turned on pixel.
	 * @param offChar
	 *            Character to represent turned off pixel.
	 */
	public StringRasterView(char onChar, char offChar) {
		this.onChar = onChar;
		this.offChar = offChar;
	}

	/**
	 * Produces the raster into a string.
	 */
	@Override
	public Object produce(BWRaster raster) {
		StringBuilder sb = new StringBuilder();
		for (int x = 0, w = raster.getWidth(), h = raster.getHeight(); x < h; x++) {
			for (int y = 0; y < w; y++) {
				sb.append(raster.isTurnedOn(x, y) ? onChar : offChar);
			}
		}
		return sb.toString();
	}

}
