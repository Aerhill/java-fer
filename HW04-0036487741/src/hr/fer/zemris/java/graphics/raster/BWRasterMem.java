package hr.fer.zemris.java.graphics.raster;

/**
 * Implementation of {@link BWRaster}
 * 
 * @author Ante Spajic
 *
 */
public class BWRasterMem implements BWRaster {

	private char[][] raster;
	private int width;
	private int height;

	public BWRasterMem(int width, int height) {
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException(
					"Invalid dimension. Width and height must be at least 1");
		}
		this.width = width;
		this.height = height;
		this.raster = new char[width][height];
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeigth() {
		return height;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void turnOn(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void turnOff(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enableFlipMode() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disableFlipMode() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isTurnedOn(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

}
