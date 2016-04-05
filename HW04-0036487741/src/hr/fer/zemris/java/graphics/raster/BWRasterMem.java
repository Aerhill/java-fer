package hr.fer.zemris.java.graphics.raster;

/**
 * Implementation of {@link BWRaster}
 * 
 * @author Ante Spajic
 *
 */
public class BWRasterMem implements BWRaster {

	private boolean flipMode = false;
	private boolean[][] raster;
	private int width;
	private int height;

	/**
	 * Public constructor for Black and white raster that creates a new raster
	 * with provided dimensions.
	 * 
	 * @param width
	 *            Width of raster
	 * @param height
	 *            Height of raster
	 */
	public BWRasterMem(int width, int height) {
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException(
					"Invalid dimension. Width and height must be at least 1");
		}
		this.width = width;
		this.height = height;
		this.raster = new boolean[height][width];
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void clear() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				raster[i][j] = false;
			}
		}
	}

	@Override
	public void turnOn(int x, int y) {
		if(flipMode && raster[x][y]) {
			raster[x][y] = false;
		} else {
			raster[x][y] = true;
		}
	}

	@Override
	public void turnOff(int x, int y) {
		raster[x][y] = false;
	}

	@Override
	public void enableFlipMode() {
		flipMode = true;
	}

	@Override
	public void disableFlipMode() {
		flipMode = false;
	}

	@Override
	public boolean isTurnedOn(int x, int y) {
		return raster[x][y];
	}

}
