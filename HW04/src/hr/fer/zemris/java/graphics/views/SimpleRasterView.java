package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Implementation of {@link RasterView} that is primarily used to produce
 * rasters to stdout with specific characters to represent turned on pixel and
 * turned off pixel on raster.
 * 
 * @author Ante Spajic
 *
 */
public class SimpleRasterView implements RasterView {

	private char onChar;
	private char offChar;

	/**
	 * Default constructor that sets character to represent turned on pixel to
	 * '*' and to turned of pixel to '.'.
	 */
	public SimpleRasterView() {
		this('*', '.');
	}

	/**
	 * Constructor that takes two argument characters to represent turned on
	 * pixels and turned off pixels.
	 * 
	 * @param onChar
	 *            Character to represent turned on 'pixel'
	 * @param offChar
	 *            Character to represent turned off 'pixel'
	 */
	public SimpleRasterView(char onChar, char offChar) {
		this.onChar = onChar;
		this.offChar = offChar;
	}

	@Override
	public Object produce(BWRaster raster) {
		for (int x = 0, w = raster.getWidth(), h = raster.getHeight(); x < h; x++) {
			for (int y = 0; y < w; y++) {
				System.out.print(raster.isTurnedOn(x, y) ? onChar : offChar);
			}
			System.out.printf("\n");
		}
		return null;
	}

}
