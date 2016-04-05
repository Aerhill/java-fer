package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

public class SimpleRasterView implements RasterView {

	private char onChar;
	private char offChar;
	
	public SimpleRasterView() {
		this('*','.');
	}
	
	public SimpleRasterView(char onChar, char offChar) {
		this.onChar = onChar;
		this.offChar = offChar;
	}
	
	@Override
	public Object produce(BWRaster raster) {
		for (int x = 0, w = raster.getWidth(), h = raster.getHeigth(); x < h ; x++) {
			for (int y = 0; y < w; y++) {
				System.out.print(raster.isTurnedOn(x, y) ? onChar : offChar);
			}
			System.out.printf("\n");
		}
		return null;
	}

}
