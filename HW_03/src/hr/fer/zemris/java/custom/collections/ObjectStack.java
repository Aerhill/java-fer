package hr.fer.zemris.java.custom.collections;

import java.util.EmptyStackException;

/**
 * Implementation of stack with adaptor pattern using the ArrayIndexedCollection
 * from previous problem as an adaptee.
 * 
 * @author Ante Spajic
 *
 */
public class ObjectStack {

	private ArrayIndexedCollection adaptee;

	/**
	 * Constructs an empty stack with default size of adaptee in this case
	 * ArrayIndexedCollection of size 16.
	 */
	public ObjectStack() {
		adaptee = new ArrayIndexedCollection();
	}

	/**
	 * Constructs an empty stack with specified initial capacity
	 * 
	 * @param capacity
	 *            initial capacity of the stack
	 */
	public ObjectStack(int capacity) {
		adaptee = new ArrayIndexedCollection(capacity);
	}

	/**
	 * Checks whether there are any elements on the stack and returns
	 * <tt>true</tt> or if there are no more elements left on it returns
	 * <tt>false</tt>-
	 * 
	 * @return <tt>True</tt> or <tt>False</tt> based on if there are any
	 *         elements on stack.
	 */
	public boolean isEmpty() {
		return adaptee.isEmpty();
	}

	/**
	 * Returns the size of the stack, size of stack is number of elements stored
	 * in it.
	 * 
	 * @return Number of elements stored in this stack.
	 */
	public int size() {
		return adaptee.size();
	}

	/**
	 * Pushes given value to the top of the stack-
	 * 
	 * @param value
	 *            Value to be added on stack
	 * @throws IllegalArgumentException
	 *             if attempted to add a null value to the stack which is not
	 *             supported
	 */
	public void push(Object value) {
		if (value == null) {
			throw new IllegalArgumentException(
					"Cannot add a null element on stack");
		}
		adaptee.add(value);
	}

	/**
	 * Gets the element from top of the stack and removes it from the stack.
	 * 
	 * @return Last added element from stack.
	 * @throws EmptyStackException
	 *             When attempted to pop an element from an empty stack.
	 */
	public Object pop() {
		int size = adaptee.size();
		if (size == 0) {
			throw new EmptyStackException();
		}
		Object result = adaptee.get(size - 1);
		adaptee.remove(result);
		return result;
	}

	/**
	 * Checks what element was added last on the stack and returns it. Element
	 * stays on stack.
	 * 
	 * @return Last added element from the stack.
	 */
	public Object peek() {
		int size = adaptee.size();
		if (size == 0) {
			throw new EmptyStackException();
		}
		return adaptee.get(size - 1);
	}

	/**
	 * Removes all elements from the stack.
	 * 
	 */
	public void clear() {
		adaptee.clear();
	}

}
