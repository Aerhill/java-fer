package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.Arrays;

import static java.lang.Math.*;

/**
 * Class used to create SimpleHashtable
 * 
 * @author Ante
 *
 * @param <K>
 *            Key type
 * @param <V>
 *            Value type
 */

public class SimpleHashTable<K, V> {

	private static final int DEFAULT_SIZE = 16;

	private int size;
	private TableEntry<K, V>[] table;

	public SimpleHashTable() {
		this(DEFAULT_SIZE);
	}

	@SuppressWarnings("unchecked")
	public SimpleHashTable(int size) {
		if (size <= 1) {
			throw new IllegalArgumentException("Table size must be bigger than 1");
		} else {
			this.size = (int) pow(2, ceil(log(size) / log(2)));
			this.table = new TableEntry[this.size];
		}
	}

	/**
	 * Inner class used to implement TableEntry inside of our SimpleHashTable It
	 * has 3 objects, key which is used to differ objects with same value which
	 * is our second object and there is reference to next object in the list
	 * Class contains normal constructor and getters for all entries and a value
	 * setter
	 * 
	 * @author Ante
	 *
	 */
	public static class TableEntry<K, V> {
		private K key;
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
		public K getKey() {
			return key;
		}

		/**
		 * Get the value of TableEntry
		 * 
		 * @return Value of TableEntry
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Set the value of TableEntry
		 * 
		 * @param value
		 *            Value to be set
		 */
		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "TableEntry [key=" + key + ", value=" + value + "]";
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
		int index = Math.abs(key.hashCode()) % this.size;
		TableEntry<K, V> slot = table[index];
		if (slot == null) {
			TableEntry<K, V> entry = new TableEntry<>(key, value, null);
			table[index] = entry;
		} else {
			while (slot != null) {
				if (slot.next == null) {
					TableEntry<K, V> entry = new TableEntry<>(key, value, null);
					slot.next = entry;
					return;
				}
				if (slot.getKey().equals(key)) {
					slot.setValue(value);
					return;
				}
				slot = slot.next;
			}
		}
	}

	/**
	 * Returns an element with given key
	 * 
	 * @param key
	 *            key to get the value of
	 * @return the value of given key
	 */

	public V get(Object key) {
		if (key == null) {
			throw new IllegalArgumentException("Key can't be null");
		}
		int index = Math.abs(key.hashCode()) % this.size;
		TableEntry<K, V> slot = table[index];
		while (slot != null) {
			if (slot.getKey().equals(key)) {
				return slot.getValue();
			}
			slot = slot.next;
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
	 * Iterates over the table until it finds given key
	 * 
	 * @param key
	 *            key to be checked for
	 * @return boolean
	 */
	public boolean containsKey(K key) {
		// TODO
		return get(key) != null;
	}

	/**
	 * Iterates over the table until it finds given value
	 * 
	 * @param value
	 *            value to be checked
	 * @return boolean
	 */

	public boolean containsValue(V value) {
		for (int i = 0; i < this.size; i++) {
			TableEntry<K, V> slot = table[i];
			while (slot != null) {
				if (slot.getValue().equals(value)) {
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
	public void remove(K key) {
		int keyHash = key.hashCode();
		int index = keyHash % size;
		TableEntry<K, V> slot = table[index];
		TableEntry<K, V> previous = null;
		for (; slot != null; previous = slot, slot = slot.next) {
			if ((slot.getKey().equals(key)) && (slot.getKey().hashCode() == keyHash)) {
				if (previous != null) {
					previous.next = slot.next;
				} else {
					table[index] = slot.next;
				}
			}
		}
	}

	/**
	 * Check if table has any elements in it
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public String toString() {
		return "SimpleHashTable [size=" + size + ", table=" + Arrays.toString(table) + "]";
	}

}
