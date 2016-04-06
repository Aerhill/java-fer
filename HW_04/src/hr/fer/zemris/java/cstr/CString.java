package hr.fer.zemris.java.cstr;

/**
 * Class that represents my implementation of {@link java.util.String} but with
 * some properties different like complexity of substring method in this class
 * is O(1) instead of O(n), also this class doesn't create its own copy of
 * character array.
 * 
 * @author Ante Spajic
 *
 */
public class CString {

	private final char[] data;
	private final int length;
	private final int offset;

	/**
	 * Constructor that takes data array and constructs a string out of it.
	 * 
	 * @param data
	 *            Character array to construct the string from.
	 */
	public CString(char[] data) {
		this(data, 0, data.length);
	}

	/**
	 * Constructor that takes another CString and creates the copy of it.
	 * 
	 * @param original
	 *            Original CString that we make a copy of.
	 */
	public CString(CString original) {
		if (original.data.length > original.length) {
			char[] newArray = new char[original.length];
			for (int i = 0; i < original.length; i++) {
				newArray[i] = original.data[original.offset + i];
			}
			this.data = newArray;
			this.length = original.length;
			this.offset = 0;
		} else {
			this.data = original.data;
			this.offset = original.offset;
			this.length = original.length;
		}
	}

	/**
	 * Allocates a new {@code CString} that contains characters from a subarray
	 * of the character array argument. The {@code offset} argument is the index
	 * of the first character of the subarray and the {@code count} argument
	 * specifies the length of the subarray. The contents of the subarray are
	 * copied; subsequent modification of the character array does not affect
	 * the newly created string.
	 * 
	 * @param data
	 *            Character array to construct this CString.
	 * @param offset
	 *            Index of the first character in the subarray.
	 * @param length
	 *            Length of this CString.
	 */
	public CString(char[] data, int offset, int length) {
		if (offset < 0) {
			throw new StringIndexOutOfBoundsException(offset);
		}
		if (length < 0) {
			throw new StringIndexOutOfBoundsException(length);
		}
		if (offset > data.length - length) {
			throw new StringIndexOutOfBoundsException(offset + length);
		}
		this.data = new char[offset + length];
		this.offset = 0;
		this.length = length;
		System.arraycopy(data, offset, this.data, 0, length);

	}

	// private constructor used to achieve O(1) complexity in substring with
	// shared array
	private CString(int offset, int length, char[] data) {
		this.offset = offset;
		this.length = length;
		this.data = data;
	}

	/**
	 * Returns new CString object which has the same character data as given
	 * Java's String object
	 * 
	 * @param string
	 * @return
	 */
	public static CString fromString(String string) {
		return new CString(string.toCharArray(), 0, string.length());
	}

	/**
	 * Returns this CStrings length
	 * 
	 * @return
	 */
	public int length() {
		return length;
	}

	/**
	 * Returns the character at given index from CString.
	 * 
	 * @param index
	 *            Index of character in CString we want to be returned.
	 * @return Character from CString at given index.
	 */
	public char charAt(int index) {
		if (index < 0 || index >= data.length) {
			throw new StringIndexOutOfBoundsException(index);
		}
		return data[index + offset];
	}

	/**
	 * Returns this CStrings character array representation.
	 * 
	 * @return Array with characters from this CString.
	 */
	public char[] toCharArray() {
		char[] newData = new char[length - offset];
		for (int i = offset; i < length; i++) {
			newData[i] = data[i];
		}
		return newData;
	}

	@Override
	public String toString() {
		return new String(data, offset, length);
	}

	/**
	 * Returns the index within this string of the first occurrence of given
	 * character.
	 * 
	 * @param c
	 *            Character that we want to get index of.
	 * @return The index of the first occurrence of the character in the
	 *         character sequence represented by this CString, or {@code -1} if
	 *         the character does not occur.
	 */
	public int indexOf(char c) {
		for (int i = 0; i < data.length; i++) {
			if (data[i + offset] == c) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns true if this CString starts with sequence of characters same as
	 * in given CString 's', otherwise returns false if provided CString's
	 * length is greater or doesn't have same properties as this CString.
	 * 
	 * @param s
	 *            CString sequence of characters we check if this CString begins
	 *            with.
	 * @return True if this CString begins with all the characters from CString
	 *         parameter s.
	 */
	public boolean startsWith(CString s) {
		return containsFrom(s, 0);
	}

	/**
	 * Returns true if this CString ends with same sequence of characters as in
	 * provided CString, otherwise returns false if lengths don't match or some
	 * of properties differ.
	 * 
	 * @param s
	 *            CString sequence of characters we check if this CString ends
	 *            with.
	 * @return True if this CString ends with all the characters from CString
	 *         parameter s.
	 */
	public boolean endsWith(CString s) {
		return containsFrom(s, length - s.length);
	}

	/**
	 * Private method that checks if this string contains CString from given
	 * index.
	 * 
	 * @param s
	 *            String to check if contained inside this CString
	 * @param index
	 *            Index to check from.
	 * @return TRUE OR FALSE BASED ON RESULT.
	 */
	private boolean containsFrom(CString s, int index) {
		int start = offset + index;
		int sOffset = s.offset;
		int sLen = s.length;
		if ((index < 0) || (index > length - sLen)) {
			return false;
		}
		while (--sLen >= 0) {
			if (data[start++] != s.data[sOffset++]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns true if and only if this string contains the specified sequence
	 * of char values.
	 * 
	 * @param s
	 *            The sequence to search for
	 * @return True if this string contains {@code s}, false otherwise.
	 */
	public boolean contains(CString s) {
		if (s == null) {
			throw new NullPointerException();
		}
		// it contains an empty string only if its empty
		if (s.length == 0) {
			return (length == 0) ? true : false;
		}
		char first = s.data[0];
		int targetLen = s.data.length;
		int max = offset + (length - targetLen);

		for (int i = offset; i < data.length; i++) {
			// Find first character
			if (data[i] != first) {
				while (++i <= max && data[i] != first)
					;
			}
			// After finding first character look for rest of the word
			if (i <= max) {
				int j = i + 1;
				int end = j + targetLen - 1;
				for (int k = s.offset + 1; j < end && data[j] == s.data[k]; j++, k++)
					;

				if (j == end) {
					// Found whole string.
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Returns a string that is a substring of this string. The substring begins
	 * at the specified {@code startIndex} and extends to the character at index
	 * {@code endIndex - 1}. The length of the substring is
	 * {@code endIndex-beginIndex}.
	 * 
	 * @param startIndex
	 *            The starting index, inclusive.
	 * @param endIndex
	 *            The ending index, exclusive.
	 * @return The specified substring.
	 */
	public CString substring(int startIndex, int endIndex) {
		if (startIndex < 0) {
			throw new StringIndexOutOfBoundsException(startIndex);
		}
		if (endIndex > data.length) {
			throw new StringIndexOutOfBoundsException(endIndex);
		}
		int newLen = endIndex - startIndex;
		if (newLen < 0) {
			throw new StringIndexOutOfBoundsException(newLen);
		}
		return ((startIndex == 0 && endIndex == data.length) ? this
				: new CString(offset + startIndex, newLen, data));
	}

	/**
	 * Returns new CString which represents starting part of original string and
	 * is of length n.
	 * 
	 * @param n
	 *            Number of characters to be in new CString from the start of
	 *            this CString
	 * @return New CString representing specified left substring.
	 */
	public CString left(int n) {
		if (n < 0 || n > length) {
			throw new StringIndexOutOfBoundsException(n);
		}
		return new CString(data, offset, n);
	}

	/**
	 * Returns new CString which represents ending part of original string and
	 * is of length n
	 * 
	 * @param n
	 *            Number of last characters from this string to be contained in
	 *            new.
	 * @return New CString representing specified right substring.
	 */
	public CString right(int n) {
		if (n < 0 || n > length) {
			throw new StringIndexOutOfBoundsException(n);
		}
		return new CString(data, offset + length - n, n);
	}

	/**
	 * Returns a new CString that is a result of adding another CString 's' to
	 * this CString.
	 *
	 * @param s
	 *            CString to be added to this one.
	 * @return New CString that is a result of adding CString s to this CString.
	 */
	public CString add(CString s) {
		int newLen = s.length + length;
		char[] newArr = new char[newLen];
		System.arraycopy(data, offset, newArr, 0, length);
		System.arraycopy(s.data, s.offset, newArr, length, s.length);
		return new CString(newArr, 0, newLen);
	}

	/**
	 * Replaces all occurrences of oldChar with newChar. If none of oldChar is
	 * contained in this string method returns a copy of this string.
	 * 
	 * @param oldChar
	 *            Character to be replaced.
	 * @param newChar
	 *            Character to replace the old character with.
	 * @return
	 */
	public CString replaceAll(char oldChar, char newChar) {
		char[] newData = new char[length - offset];
		for (int i = offset; i < length; i++) {
			if (data[i] == oldChar) {
				newData[i] = newChar;
				continue;
			}
			newData[i] = data[i];
		}
		return new CString(newData, offset, length);
	}

	/**
	 * Replaces all occurrences of oldString in this CString with newStr.
	 * 
	 * @param oldStr
	 *            Sequence of characters to be replaced.
	 * @param newStr
	 *            New sequence to replace the old with.
	 * @return new CString with all old CStrings replaced with new CStrings
	 */
	public CString replaceAll(CString oldStr, CString newStr) {
		if (!contains(oldStr)) {
			return this;
		}
		int newLen = (length - oldStr.length) + newStr.length + 1;
		char[] newData = new char[newLen];
		if (oldStr.toString().equals("")) {
			for (int i = 0; i < length; i++) {
				newData[i * length] = data[i];
				for (int j = 0, k = i * length + 1; j < newStr.length; j++, k++) {
					newData[k] = newStr.data[j];
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = offset; i < offset + length; i++) {
			if (data[i] == oldStr.data[oldStr.offset]) {
				// Checking whole sequence
				int j, z;
				for (j = oldStr.offset, z = i; j < oldStr.offset
						+ oldStr.length; j++, z++) {
					if (data[z] != oldStr.data[j]) {
						break;
					}
				}
				// Sequence is correct, append new piece.
				if (j == oldStr.offset + oldStr.length) {
					sb.append(newStr.toString());
					// skip the old word
					i += oldStr.length;
				}
			}
			sb.append(data[i]);
		}
		newData = sb.toString().toCharArray();
		return new CString(newData);
	}
}
