package hr.fer.zemris.java.graphics;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;
import hr.fer.zemris.java.graphics.shapes.Ellipse;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.views.RasterView;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

/**
 * Program with example written in assignment.
 * 
 * @author Ante Spajic
 *
 */
public class Example {

	public static void main(String[] args) {

		Rectangle rect1 = new Rectangle(0, 0, 4, 4);
		Rectangle rect2 = new Rectangle(1, 1, 2, 2);
		Ellipse eli = new Ellipse(15,15, 12, 12);
		Circle circ = new Circle(35,35,15);
		BWRaster raster = new BWRasterMem(100, 100);

		raster.enableFlipMode();
		rect1.draw(raster);
		rect2.draw(raster);
		eli.draw(raster);
		circ.draw(raster);

		RasterView view = new SimpleRasterView();

		view.produce(raster);
		//view.produce(raster);

//		System.out.println();
//		RasterView view2 = new SimpleRasterView('X', '_');
//		view2.produce(raster);

	}
}
