package hr.fer.zemris.java.raytracer.model;

/**
 * This is a model implementation of a sphere that can exist in our 3D scene.
 * 
 * @author Ante Spajic
 *
 */
public class Sphere extends GraphicalObject {

	/** center of this sphere */
	private Point3D center;
	/** radius of this sphere */
	private double radius;
	/** Diffuse red coefficient */
	private double kdr;
	/** Diffuse green coefficient */
	private double kdg;
	/** Diffuse blue coefficient */
	private double kdb;
	/** Reflexive red coefficient */
	private double krr;
	/** Reflexive green coefficient */
	private double krg;
	/** Reflexive blue coefficient */
	private double krb;
	/** Reflexive intensity coefficient */
	private double krn;

	/**
	 * Creates a new sphere with specified center point, radius and diffuse and reflexive coefficients.
	 * 
	 * @param center center of this sphere
	 * @param radius radius of this sphere
	 * @param kdr Diffuse red coefficient
	 * @param kdg Diffuse green coefficient
	 * @param kdb Diffuse blue coefficient
	 * @param krr Reflexive red coefficient
	 * @param krg Reflexive green coefficient
	 * @param krb Reflexive blue coefficient
	 * @param krn Reflexive intensity coefficient
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {

		double a = ray.direction.scalarProduct(ray.direction); // if direction is normalized it should equal 1.0
		double b = ray.direction.scalarMultiply(2).scalarProduct(ray.start.sub(center));
		double c = (ray.start.sub(center)).scalarProduct(ray.start.sub(center)) - radius * radius;

		double discriminant = b * b - 4 * a * c;
		// solutions are complex numbers and ray has no intersection with sphere
		if (discriminant < 0) {
			return null;
		}

		double lambd1 = (-b + Math.sqrt(discriminant)) / (2 * a);
		double lambd2 = (-b - Math.sqrt(discriminant)) / (2 * a);
		if (lambd1 <= 0 && lambd2 <= 0) {
			return null;
		}

		// we need only a closer intersection so we pick smaller out of 2
		// solutions
		double distance = lambd1 < lambd2 ? lambd1 : lambd2;
		Point3D intersection = ray.start.add(ray.direction.scalarMultiply(distance));
		// we can be inside of a sphere so a closest intersection will be inner,
		// on all other occasions it will be outer
		boolean outer = intersection.sub(ray.start).norm() > radius;

		return new RayIntersection(intersection, distance, outer) {

			@Override
			public Point3D getNormal() {
				return getPoint().sub(center).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

}
