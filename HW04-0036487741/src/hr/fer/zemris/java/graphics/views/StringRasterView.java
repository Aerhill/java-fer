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
		StringBuilder sb = new StringBuilder();
		for (int x = 0, w = raster.getWidth(), h = raster.getHeigth(); x < h ; x++) {
			for (int y = 0; y < w; y++) {
				sb.append(raster.isTurnedOn(x, y) ? onChar : offChar);
			}
		}
		return sb.toString();
	}

}
