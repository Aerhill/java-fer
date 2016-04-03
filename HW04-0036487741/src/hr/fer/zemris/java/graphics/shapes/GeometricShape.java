package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

public abstract class GeometricShape {

	public void draw(BWRaster r) {

	}
	
	public abstract boolean containsPoint(int x, int y);
}
