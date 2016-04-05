package hr.fer.zemris.java.graphics;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;
import hr.fer.zemris.java.graphics.shapes.Ellipse;
import hr.fer.zemris.java.graphics.shapes.GeometricShape;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.shapes.Square;
import hr.fer.zemris.java.graphics.views.RasterView;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

import java.util.Scanner;

/**
 * Demonstration program for this homework. Takes in two arguments from command
 * line which represent dimensions of a raster. And then asks for a number of
 * lines we want to enter. Valid commands are
 * <ul>
 * <li>FLIP(switches raster flip mode)</li>
 * <li>RECTANGLE x y height width</li>
 * <li>ELLIPSE cx cy radiusX radiusY</li>
 * <li>SQUARE x y size</li>
 * <li>CIRCLE cx cy radius</li>
 * </ul>
 * 
 * @author Ante Spajic
 *
 */
public class Demo {

	/**
	 * Entry point to a program.
	 * 
	 * @param args
	 *            2 Command line arguments which represent width and height of a
	 *            raster.
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err
					.println("Expected 2 arguments, width and height of raster.");
			System.exit(1);
		}
		try (Scanner sc = new Scanner(System.in);) {
			int width = Integer.parseInt(args[0]);
			int height = Integer.parseInt(args[1]);

			BWRaster raster = new BWRasterMem(width, height);

			int numberOfCommands = Integer.parseInt(sc.nextLine());
			GeometricShape[] shapes = new GeometricShape[numberOfCommands];

			for (int i = 0; i < numberOfCommands; i++) {
				String[] entry = sc.nextLine().split("\\s+");
				String name = entry[0];
				shapes[i] = createShape(name.toLowerCase(), entry);
			}

			boolean flipped = false;
			for (int i = 0; i < shapes.length; i++) {
				if (shapes[i] != null) {
					shapes[i].draw(raster);
				} else {
					if (flipped) {
						flipped = false;
						raster.disableFlipMode();
					} else {
						flipped = true;
						raster.enableFlipMode();
					}
				}
			}

			RasterView srv = new SimpleRasterView();
			srv.produce(raster);

		} catch (NumberFormatException e) {
			System.err
					.println("Dear user, You have entered something else where I expected an integer, places where I don't expect an integer are names of shapes and command flip");
			System.exit(1);
		}
	}

	/**
	 * Helper method that creates a new shape from line input.
	 * 
	 * @param name
	 *            Name of the shape we want to create.
	 * @param entry
	 *            Line feed from System.in
	 * @return Newly created {@link GeometricShape} or null if a command flip
	 *         has been provided
	 */
	private static GeometricShape createShape(String name, String[] entry) {
		switch (name) {
		case "rectangle":
			return new Rectangle(Integer.parseInt(entry[1]),
					Integer.parseInt(entry[2]), Integer.parseInt(entry[3]),
					Integer.parseInt(entry[4]));
		case "ellipse":
			return new Ellipse(Integer.parseInt(entry[1]),
					Integer.parseInt(entry[2]), Integer.parseInt(entry[3]),
					Integer.parseInt(entry[4]));
		case "square":
			return new Square(Integer.parseInt(entry[1]),
					Integer.parseInt(entry[2]), Integer.parseInt(entry[3]));
		case "circle":
			return new Circle(Integer.parseInt(entry[1]),
					Integer.parseInt(entry[2]), Integer.parseInt(entry[3]));
		case "flip":
			return null;
		default:
			System.err.println("Wow wow wow, dear user.");
			System.exit(1);
		}
		return null;
	}
}
