package hr.fer.zemris.java.tecaj.hw5.collections;

import static java.lang.Math.ceil;
import static java.lang.Math.log;
import static java.lang.Math.pow;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class represents an implementation of adorable iterable SimpleHashtable
 * that stores key-value pairs.
 * 
 * @author Ante Spajic
 *
 * @param <K>
 *            Key type
 * @param <V>
 *            Value type
 */
@SuppressWarnings("unchecked")
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	private static final int DEFAULT_SIZE = 16;

	private static final double RESIZE_THRESHOLD = 0.75;

	/**
	 * Number of elements stored in this collection
	 */
	private int size;
	/**
	 * Local storage for table entry slots.
	 */
	private TableEntry<K, V>[] table;
	/**
	 * The number of structural modifications to the table.
	 */
	private int modCount;

	/**
	 * Constructs an empty SimpleHashtable with a default size of 16.
	 */
	public SimpleHashtable() {
		this(DEFAULT_SIZE);
	}

	/**
	 * Constructs an empty SimpleHashtable with a closest power of 2 greater
	 * than given size.
	 * 
	 * @param size
	 *            Desired size of a SimpleHashtable.
	 */
	public SimpleHashtable(int size) {
		if (size < 1) {
			throw new IllegalArgumentException("Table size must be greater than 0");
		} else {
			this.size = 0;
			int tablesize = (int) pow(2, ceil(log(size) / log(2)));
			this.table = new TableEntry[tablesize];
		}
	}

	/**
	 * Inner class used to implement TableEntry inside of our SimpleHashTable It
	 * has 3 objects, key which is used to differ objects with same value which
	 * is our second object and there is reference to next object in the list
	 * Class contains normal constructor and getters for all entries and a value
	 * setter
	 * 
	 * @param <K>
	 *            Key type
	 * @param <V>
	 *            Value type
	 *
	 * @author Ante Spajic
	 */
	public static class TableEntry<K, V> {
		private final K key;
		private V value;
		private TableEntry<K, V> next;

		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Get the key of TableEntry
		 * 
		 * @return Key of TableEntry
		 */
		public Object getKey() {
			return key;
		}

		/**
		 * Get the value of TableEntry
		 * 
		 * @return Value of TableEntry
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * Set the value of TableEntry
		 * 
		 * @param value
		 *            Value to be set
		 */
		public void setValue(Object value) {
			this.value = (V) value;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}
	}

	/**
	 * Place new entries to table, new entry is always placed at the end of the
	 * existing list
	 * 
	 * @param key
	 *            Key of pair to be entered
	 * @param value
	 *            Value of the pair
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Key can't be null");
		}
		if (size >= RESIZE_THRESHOLD * table.length) {
			resize();
		}
		int index = getIndex(key);
		TableEntry<K, V> slot = table[index];
		// there is an entry already at that slot
		while (slot != null) {
			if (slot.key.equals(key)) {
				slot.setValue(value);
				return;
			}
			if (slot.next == null) {
				slot.next = new TableEntry<>(key, value, null);
				size++;
				modCount++;
				return;
			}
			slot = slot.next;
		}
		// slot was empty
		table[index] = new TableEntry<>(key, value, null);
		modCount++;
		size++;
	}

	/**
	 * If this table's is filled with more elements than 75% of this capacity
	 * then this hashtable's capacity is doubled to prevent overflowing
	 * elements.
	 * 
	 */
	private void resize() {
		TableEntry<K, V>[] oldTable = table;
		table = new TableEntry[2 * oldTable.length];
		size = 0;
		modCount++;
		for (int i = 0; i < oldTable.length; i++) {
			TableEntry<K, V> tata = oldTable[i];
			while (tata != null) {
				put(tata.key, tata.value);
				tata = tata.next;
			}
		}
	}

	/**
	 * Clears all elements from this SimpleHashtable.
	 */
	public void clear() {
		table = new TableEntry[table.length];
		size = 0;
	}

	/**
	 * Returns the value to which the specified key is mapped, or {@code null}
	 * if this map contains no mapping for the key.
	 * 
	 * @param key
	 *            key to get the value of
	 * @return the value of given key
	 */
	public V get(Object key) {
		if (key != null) {
			TableEntry<K, V> slot = table[getIndex(key)];
			while (slot != null) {
				if (slot.getKey().equals(key)) {
					return (V) slot.getValue();
				}
				slot = slot.next;
			}
		}
		return null;
	}

	/**
	 * Returns the number of key-value mappings in this table.
	 * 
	 * @return number of table entries
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns {@code true} if this map contains a mapping for the specified
	 * key.
	 * 
	 * @param key
	 *            key whose presence in this map is to be tested
	 * @return boolean
	 */
	public boolean containsKey(Object key) {
		if (key != null) {
			TableEntry<K, V> slot = table[getIndex(key)];
			while (slot != null) {
				if (slot.key.equals(key)) {
					return true;
				}
				slot = slot.next;
			}
		}
		return false;
	}

	/**
	 * Returns <tt>true</tt> if this map maps one or more keys to the specified
	 * value.
	 * 
	 * @param value
	 *            value whose presence in this map is to be tested
	 * @return <tt>true</tt> if this map maps one or more keys to the specified
	 *         value
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> slot = table[i];
			while (slot != null) {
				if (slot.value == value || (slot.value != null && slot.value.equals(value))) {
					return true;
				}
				slot = slot.next;
			}
		}
		return false;
	}

	/**
	 * This method goes through table slot lists and looks for a given key and
	 * when found relocates the next pointer of previous item to its next item
	 * and that way item is removed from the list
	 * 
	 * @param key
	 *            key to be removed
	 */
	public void remove(Object key) {
		if (key == null || !containsKey(key)) {
			return;
		}
		int index = getIndex(key);
		TableEntry<K, V> help = table[index];
		if (help.getKey().equals(key)) {
			table[index] = help.next;
		} else {
			// find the key inside the slot
			while (help.next != null && !help.next.getKey().equals(key)) {
				help = help.next;
			}
			help.next = help.next.next;
		}
		modCount++;
		size--;
	}

	/**
	 * Calculates the table index slot of a key.
	 * 
	 * @param key
	 *            Key whose index is calculated.
	 * @return Generated table slot.
	 */
	private int getIndex(Object key) {
		int keyHash = key.hashCode();
		return Math.abs(keyHash % table.length);
	}

	/**
	 * Check if table has any elements in it.
	 * 
	 * @return True if the table has any elements inside, false otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		TableEntry<K, V> help;
		for (int i = 0; i < table.length; i++) {
			help = table[i];
			while (help != null) {
				sb.append(help.toString()).append(", ");
				help = help.next;
			}
		}
		return (sb.length() >= 2) ? "[" + sb.substring(0, sb.length() - 2) + "]" : "[]";
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		private int tableIndex;
		private TableEntry<K, V> current;
		private TableEntry<K, V> next;
		private int privateModCount = modCount;

		IteratorImpl() {
			tableIndex = 0;
			current = next = null;
			if (size > 0) {
				nextEntry();
			}
		}

		/**
		 * Increments tableIndex to go through this collection elements.
		 * 
		 */
		private void nextEntry() {
			while (tableIndex < table.length && next == null) {
				next = table[tableIndex++];
			}
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public TableEntry<K, V> next() {
			if (privateModCount != modCount) {
				throw new ConcurrentModificationException("Alo");
			}
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements left!");
			}
			current = next;
			if ((next = current.next) == null) {
				nextEntry();
			}
			return current;
		}

		@Override
		public void remove() {
			if (privateModCount != modCount) {
				throw new ConcurrentModificationException("Alo");
			}
			if (current == null) {
				throw new IllegalStateException();
			}
			SimpleHashtable.this.remove(current.key);
			privateModCount = modCount;
			current = null;
		}
	}

	/**
	 * Returns the length of a {@link TableEntry} array used for storing slots
	 * inside a table. Used mostly for testing purposes.
	 * 
	 * @return The table length
	 */
	public int getTableLength() {
		return table.length;
	}
}
