package hr.fer.zemris.java.graphics.raster;

/**
 * 
 * @author Ante Spajic
 *
 */
public interface BWRaster {
	
	/**
	 * 
	 * @return
	 */
	int getWidth();
	/**
	 * 
	 * @return
	 */
	int getHeigth();
	/**
	 * 
	 */
	void clear();
	/**
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
	 * 
	 */
	void enableFlipMode();
	/**
	 * 
	 */
	void disableFlipMode();
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	boolean isTurnedOn(int x, int y);
}
