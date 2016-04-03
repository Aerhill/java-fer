package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

public class StringRasterView implements RasterView {

	private char onChar;
	private char offChar;
	
	public StringRasterView() {
		this('*','.');
	}
	
	public StringRasterView(char onChar, char offChar) {
		this.onChar = onChar;
		this.offChar = offChar;
	}
	
	@Override
	public Object produce(BWRaster raster) {
		// TODO Auto-generated method stub
		return null;
	}

}
