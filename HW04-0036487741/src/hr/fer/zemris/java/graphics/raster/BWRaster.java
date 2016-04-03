package hr.fer.zemris.java.graphics.raster;

public interface BWRaster {

	int getWidth();
	int getHeigth();
	void clear();
	void turnOn(int x, int y);
	void turnOff(int x, int y);
	void enableFlipMode();
	void disableFlipMode();
	boolean isTurnedOn(int x, int y);
}
