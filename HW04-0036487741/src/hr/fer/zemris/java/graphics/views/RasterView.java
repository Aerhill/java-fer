package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * 
 * @author Ante Spajic
 *
 */
public interface RasterView {

	/**
	 * Produces the given raster to output or into an object.
	 * 
	 * @param raster
	 *            Raster to be produced
	 * @return Object containing produced raster.
	 */
	Object produce(BWRaster raster);
}
