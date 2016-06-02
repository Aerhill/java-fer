package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * <tt>ObjectMultistack</tt> is a map which allows user to store multiple values
 * for the same key in a 'stack-like' abstraction. Value stored most recently
 * under certain key will be the value retrieved when search under certain key
 * is being conducted.
 * 
 * @author Ante Spajic
 *
 */
public class ObjectMultistack {

	/**
	 * Map of keys as instances of type <tt>String</tt>, and values as instances
	 * of type <tt>MultistackEntry</tt> connected in single-linked list if there
	 * is more than one value associated with the same key.
	 */
	private Map<String, MultistackEntry> map;

	/**
	 * Public constructor allocates internal map.
	 */
	public ObjectMultistack() {
		map = new HashMap<>();
	}

	/**
	 * Pushes an item onto top of stack associated with the passed key (name) in
	 * the map.
	 * 
	 * @param name
	 *            key of map entry
	 * @param valueWrapper
	 *            the item to be pushed onto this stack
	 * @throws IllegalArgumentException
	 *             if key passed is <tt>null</tt> value
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if (name == null) {
			throw new IllegalArgumentException("Key can't be null");
		}
		MultistackEntry entry = map.get(name);
		MultistackEntry newEntry = new MultistackEntry(valueWrapper, entry);
		map.put(name, newEntry);
	}

	/**
	 * Removes the object at the top of stack associated with the passed key
	 * (name) and returns that object as the value of this function.
	 * 
	 * @param name
	 *            key of map entry
	 * @return object retrieved from the top of stack
	 * @throws IllegalArgumentException
	 *             if key passed is <tt>null</tt> value
	 * @throws NoSuchElementException
	 *             if entry under given key does not exist in the map
	 */
	public ValueWrapper pop(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Key can't be null");
		}
		if (!map.containsKey(name)) {
			throw new NoSuchElementException("No values associated with that key exist");
		}
		MultistackEntry entry = map.get(name);
		if (entry.next == null) {
			map.remove(name);
		} else {
			map.put(name, entry.next);
		}
		return entry.value;
	}

	/**
	 * Looks at the object at the top of stack associated with the passed key
	 * (name) without removing it from the stack.
	 * 
	 * @param name
	 *            key of map entry
	 * @return the object at the top of this stack
	 * @throws IllegalArgumentException
	 *             if key passed is <tt>null</tt> value
	 * @throws NoSuchElementException
	 *             if entry under given key does not exist in the map
	 */
	public ValueWrapper peek(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Key can't be null");
		}
		if (!map.containsKey(name)) {
			throw new NoSuchElementException("No values associated with that key exist");
		}
		return map.get(name).value;
	}

	/**
	 * Checks if there are entry's stored in the map under given key.
	 * 
	 * @param name
	 *            key
	 * @return <tt>true</tt> if key has stored entry's, <tt>false</tt> otherwise
	 */
	public boolean isEmpty(String name) {
		return map.get(name) == null;
	}

	/**
	 * <tt>MultistackEntry</tt> is inner static class which acts as a node of
	 * single-linked list, list of values, associated with key in internal map
	 * of <tt>ObjectMultistack</tt>.
	 * 
	 * @author Ante Spajic
	 *
	 */
	public static class MultistackEntry {

		/**
		 * Value of type <tt>ValueWrapper</tt> stored in node.
		 */
		private ValueWrapper value;

		/**
		 * Reference to the next node.
		 */
		private MultistackEntry next;

		/**
		 * Public constructor which receives object of type
		 * <tt>ValueWrapper</tt> to be stored inside the node.
		 * 
		 * @param value
		 *            Value to be stored
		 * @param next
		 *            Reference to the next entry in a list.
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}

		/**
		 * Getter for value of type <tt>ValueWrapper</tt> stored inside the
		 * node.
		 * 
		 * @return value stored
		 */
		public ValueWrapper getValue() {
			return value;
		}

		/**
		 * Getter for the next entry in the list. That means second element from
		 * top.
		 * 
		 * @return Next Entry
		 */
		public MultistackEntry getNext() {
			return next;
		}

	}
}
