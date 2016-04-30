package hr.fer.zemris.java.tecaj.hw6.demo2;

/**
 * {@link LikeMedian} demonstration program, copied from assignment.
 * 
 * @author Ante Spajic
 *
 */
public class MedianDemo2 {

	/**
	 * Entry point to a program.
	 * 
	 * @param args
	 *            Unused command line arguments.
	 */
	public static void main(String[] args) {
		LikeMedian<String> likeMedian = new LikeMedian<String>();
		likeMedian.add("Joe");
		likeMedian.add("Jane");
		likeMedian.add("Adam");
		likeMedian.add("Zed");
		String result = likeMedian.get().get();
		System.out.println(result); // Writes: Jane
	}
}
