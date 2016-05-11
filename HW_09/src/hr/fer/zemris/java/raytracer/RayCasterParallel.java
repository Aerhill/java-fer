package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This class represents simple multithreaded implementation of ray-tracer for
 * rendering of 3D scenes;
 * 
 * @author Ante Spajic
 *
 */
public class RayCasterParallel {

	/**
	 * Allowed discrepancy for double comparison
	 */
	private static final double DELTA = 1e-9;

	/**
	 * Entry point to the program, creates scene with objects and shows them in
	 * GUI.
	 * 
	 * @param args
	 *            unused command line arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Class that creates a ray-tracer producer that is used for tracing rays in
	 * our 3D scene, it is used by a RayTracerViewer to show a scene in gui.
	 * 
	 * @return imlpementation of {@link IRayTracerProducer} producer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D ogVector = view.sub(eye).normalize();
				Point3D viewUpVector = viewUp.normalize();

				Point3D yAxis = viewUpVector.sub(ogVector.scalarMultiply(ogVector.scalarProduct(viewUpVector)))
						.normalize();
				Point3D xAxis = ogVector.vectorProduct(yAxis).normalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();

				ForkJoinPool pool = new ForkJoinPool();

				pool.invoke(new ComputationJob(horizontal, vertical, height, width, xAxis, yAxis, eye, screenCorner,
						scene, red, green, blue, 0, height - 1));

				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * Traces fired ray vector in scene and finds its closest intersection and
	 * determines produced color.
	 * 
	 * @param scene
	 *            scene with objects
	 * @param ray
	 *            fired ray
	 * @param rgb
	 *            array containing red green and blue color intensities
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		short[] rgbNew = { 15, 15, 15 };

		RayIntersection intersection = findClosestIntersection(scene, ray);
		if (intersection != null) {
			determineColorFor(intersection, ray, scene, rgbNew);
		} else {
			rgbNew[0] = 0;
			rgbNew[1] = 0;
			rgbNew[2] = 0;
		}

		for (int i = 0; i < 3; i++) {
			rgb[i] = rgbNew[i];
		}
	}

	/**
	 * Finds closest intersection of each object-ray intersection in scene and
	 * between objects in scene and traced ray.
	 * 
	 * @param scene
	 *            scene containing all objects
	 * @param ray
	 *            traced ray
	 * @return closest ray-object intersection in scene
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection result = null;
		for (GraphicalObject go : scene.getObjects()) {
			RayIntersection i = go.findClosestRayIntersection(ray);
			if (i == null) {
				continue;
			}
			if (result == null || result.getDistance() > i.getDistance()) {
				result = i;
			}
		}
		return result;
	}

	/**
	 * Method that determines a color for each intersection of ray and object on
	 * screen.
	 * 
	 * @param intersection
	 *            intersection of ray and object
	 * @param ray
	 *            traced ray
	 * @param scene
	 *            scene where the objects are placed
	 * @param rgb
	 *            array with red green and blue color intensity
	 */
	private static void determineColorFor(RayIntersection intersection, Ray ray, Scene scene, short[] rgb) {

		for (LightSource ls : scene.getLights()) {
			Ray r = Ray.fromPoints(ls.getPoint(), intersection.getPoint());
			RayIntersection i = findClosestIntersection(scene, r);
			if (i != null) {
				double d1 = ls.getPoint().sub(intersection.getPoint()).norm();
				double d2 = ls.getPoint().sub(i.getPoint()).norm();
				if (d2 + DELTA >= d1) {
					addReflective(ls, ray, rgb, i);
					addDiffuse(ls, rgb, i);
				}
			}
		}
	}

	/**
	 * Adds the diffuse component to displayed object
	 * 
	 * @param ls
	 *            light source in scene
	 * @param rgb
	 *            array with red green and blue components representing
	 *            intensity of each color
	 * @param i
	 *            an intersection of ray with object
	 */
	private static void addDiffuse(LightSource ls, short[] rgb, RayIntersection i) {

		Point3D normal = i.getNormal();
		Point3D light = ls.getPoint().sub(i.getPoint()).normalize();

		double cos = normal.scalarProduct(light) > 0 ? normal.scalarProduct(light) : 0;

		rgb[0] += (short) (ls.getR() * i.getKdr() * cos);
		rgb[1] += (short) (ls.getG() * i.getKdg() * cos);
		rgb[2] += (short) (ls.getB() * i.getKdb() * cos);
	}

	/**
	 * Adds the reflective component to displayed object
	 * 
	 * @param ls
	 *            light source in scene
	 * @param ray
	 *            traced ray
	 * @param rgb
	 *            array with red green and blue components representing
	 *            intensity of each color
	 * @param i
	 *            an intersection of ray with object
	 */
	private static void addReflective(LightSource ls, Ray ray, short[] rgb, RayIntersection i) {

		Point3D normal = i.getNormal();
		Point3D light = ls.getPoint().sub(i.getPoint()).normalize();

		Point3D r = normal.normalize().scalarMultiply(2 * light.scalarProduct(normal) / normal.norm()).sub(light)
				.normalize();
		Point3D v = ray.start.sub(i.getPoint()).normalize();

		double vec = r.scalarProduct(v);
		double cos = Math.pow(vec, i.getKrn()) > 0 ? Math.pow(vec, i.getKrn()) : 0;

		if (vec >= 0) {
			rgb[0] += (short) (ls.getR() * i.getKrr() * cos);
			rgb[1] += (short) (ls.getG() * i.getKrg() * cos);
			rgb[2] += (short) (ls.getB() * i.getKrb() * cos);
		}
	}

	/**
	 * This class is used to create a recursive computation job that splits
	 * coloring of pixels on multiple threads until a small enough part for each
	 * thread has been reached. Class checks if number of lines of screen is
	 * small enough for one thread and if its not separates the work on 2
	 * threads recursively until a job has been splitted on small enough parts
	 * for each thread.
	 * 
	 * @author Ante Spajic
	 *
	 */
	public static class ComputationJob extends RecursiveAction {

		/**
		 * auto generated UID
		 */
		private static final long serialVersionUID = -3791256024364918873L;

		/**
		 * Threshold for singlethreaded work
		 */
		private static final int THRESHOLD = 5;

		/**
		 * Width and height of window
		 */
		private int height;
		private int width;

		/**
		 * horizontal width of observed space and vertical height of observed
		 * space
		 */
		private double horizontal;
		private double vertical;

		/**
		 * Vectors of this screen and the position of the observer
		 */
		private Point3D xAxis;
		private Point3D yAxis;
		private Point3D screenCorner;
		private Point3D eye;

		/**
		 * Colors for generation
		 */
		private short[] red;
		private short[] green;
		private short[] blue;

		/**
		 * Scene where this is all happening
		 */
		private Scene scene;

		/**
		 * Limits for every thread, where to start computing and where to end
		 */
		private int yMin;
		private int yMax;

		/**
		 * Creates a new recursive computation job.
		 * 
		 * @param horizontal
		 *            horizontal width of observed space
		 * @param vertical
		 *            vertical height of observed space
		 * @param width
		 *            number of pixels per screen row
		 * @param height
		 *            number of pixel per screen column
		 * @param xAxis
		 *            x-axis of screen
		 * @param yAxis
		 *            y-axis of screen
		 * @param eye
		 *            position of human observer
		 * @param screenCorner
		 *            screen corner
		 * @param scene
		 *            scene with objects
		 * @param red
		 *            array representing intensity of red color
		 * @param green
		 *            array representing intensity of red color
		 * @param blue
		 *            array representing intensity of red color
		 * @param yMin
		 *            starting line screen part to write
		 * @param yMax
		 *            ending line of screen part to write
		 * 
		 * 
		 * 
		 */
		public ComputationJob(double horizontal, double vertical, int height, int width, Point3D xAxis, Point3D yAxis,
				Point3D eye, Point3D screenCorner, Scene scene, short[] red, short[] green, short[] blue, int yMin,
				int yMax) {
			this.height = height;
			this.width = width;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.screenCorner = screenCorner;
			this.eye = eye;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.scene = scene;
			this.yMin = yMin;
			this.yMax = yMax;
		}

		@Override
		protected void compute() {
			if (yMax - yMin < THRESHOLD) {
				computeDirect();
				return;
			}
			invokeAll(
					new ComputationJob(horizontal, vertical, height, width, xAxis, yAxis, eye, screenCorner, scene, red,
							green, blue, yMin, (yMax + yMin) / 2),
					new ComputationJob(horizontal, vertical, height, width, xAxis, yAxis, eye, screenCorner, scene, red,
							green, blue, (yMax + yMin) / 2 + 1, yMax));

		}

		/**
		 * This method is used when work has been separated enough so 1 thread
		 * can work independently.
		 */
		private void computeDirect() {

			short[] rgb = new short[3];
			int offset = yMin * width;
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					double xComp = horizontal * x / (width - 1.0);
					double yComp = vertical * y / (height - 1.0);
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(xComp))
							.sub(yAxis.scalarMultiply(yComp));
					Ray ray = Ray.fromPoints(eye, screenPoint);
					tracer(scene, ray, rgb);
					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
					offset++;
				}
			}
		}
	}
}
