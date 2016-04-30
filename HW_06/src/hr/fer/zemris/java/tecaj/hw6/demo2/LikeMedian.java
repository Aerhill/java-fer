package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class <tt>LikeMedian</tt> presents collection of elements with defined
 * natural ordering, on which action to calculate and retrieve median element
 * can be performed.
 * 
 * @author Ante Spajic
 *
 * @param <T>
 *            type of elements
 */
public class LikeMedian<T> implements Iterable<T> {

	/**
	 * Collection of elements parameterized by T.
	 */
	private List<T> elements;

	/**
	 * Public constructor creates empty <tt>ArrayList</tt> parameterized by the
	 * type of elements.
	 */
	public LikeMedian() {
		elements = new ArrayList<>();
	}

	/**
	 * Method adds element to the collection of elements.
	 * 
	 * @param element
	 *            element to be added
	 */
	public void add(T element) {
		if (element == null) {
			throw new IllegalArgumentException("Can't be null");
		}
		elements.add(element);
	}

	/**
	 * Method retrieves median element of the collection defined by result of
	 * sorting elements in their natural order. If number of elements in the
	 * collection is even, smaller of two middle elements will be retrieved.
	 * 
	 * @return median element of the collection
	 */
	public Optional<T> get() {
		if (elements.isEmpty()) {
			return Optional.empty();
		}
		List<T> sorted = elements.stream().sorted().collect(Collectors.toList());
		int size = sorted.size();
		int index = size / 2;
		if (size == 1 || size == 2) {
			return Optional.of(sorted.get(0));
		} else if (index % 2 == 1) {
			return Optional.of(sorted.get(index));
		} else {
			return Optional.of(sorted.get(index - 1));
		}
	}

	@Override
	public java.util.Iterator<T> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Private nested class provides implementation of iterator over elements in
	 * class <tt>LikeMeidan</tt>.
	 * 
	 * @author Ante Spajic
	 *
	 */
	private class IteratorImpl implements Iterator<T> {
		/**
		 * Next element in the collection to be retrieved by this iterator.
		 */
		private T nextMember;
		/**
		 * Current element in the collection to which iterator points.
		 */
		private T currentMember;
		/**
		 * Current position of the next element in the collection.
		 */
		private int memberCount;

		/**
		 * Constructor creates iterator over current collection.
		 */
		IteratorImpl() {
			memberCount = 0;
			if (!elements.isEmpty()) {
				nextMember = elements.get(memberCount);
			}
		}

		@Override
		public boolean hasNext() {
			return memberCount < elements.size();
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements in the collection to iterate over.");
			}
			currentMember = nextMember;
			memberCount++;
			if (memberCount < elements.size()) {
				nextMember = elements.get(memberCount);
			}
			return currentMember;
		}

	}

}
