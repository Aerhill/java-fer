package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

/**
 * {@link LikeMedian} demonstration program, copied from assignment.
 * 
 * @author Ante Spajic
 *
 */
public class MedianDemo1 {

	/**
	 * Entry point to a program.
	 * 
	 * @param args
	 *            Unused command line arguments.
	 */
	public static void main(String[] args) {

		LikeMedian<Integer> likeMedian = new LikeMedian<Integer>();
		likeMedian.add(new Integer(10));
		likeMedian.add(new Integer(5));
		likeMedian.add(new Integer(3));
		Optional<Integer> result = likeMedian.get();
		System.out.println(result.get());
		for (Integer elem : likeMedian) {
			System.out.println(elem);
		}
	}
}
