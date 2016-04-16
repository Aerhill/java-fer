package hr.fer.zemris.java.tecaj.hw5.collections.tests;

import static org.junit.Assert.assertEquals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable.TableEntry;

public class SimpleHashtableTest {

	private static String[] imena = {"Ante", "Zvonko", "Kristijan", "Josip", "Hrvoje", "Marko", "Frano", "Vrano", "Petar", "Rezić"};
	private static String[] brojevi = {"1" , "2" , "3" , "4", "5", "6", "7", "8", "9", "0"};
	private Random rand = new Random(9);
	
	@Test(expected = IllegalArgumentException.class)
	public void nonPositiveInitialCapacityTest() {
		new SimpleHashtable<String, Integer>(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertNullKeyEntryTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();
		// null key not allowed!
		hashtable.put(null, 13);
	}

	@Test
	public void putNewAndExistingEntriesTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

		hashtable.put("key1", 1);
		hashtable.put("key2", 2);
		hashtable.put("key3", 3);

		// size should now be 3
		assertEquals(3, hashtable.size());

		hashtable.put("key1", 42);

		// size should remain 3 but value assigned to "key1" should be 42
		assertEquals(3, hashtable.size());
		assertEquals(42, hashtable.get("key1").intValue());
	}

	@Test
	public void clearEntriesTest() {
		SimpleHashtable<String, String> hashtable = new SimpleHashtable<>();
		fillTableRandomly(hashtable);
		hashtable.clear();
		// size shuold be zero after clearing
		assertEquals(0, hashtable.size());
	}

	@Test
	public void containsKeyMethodTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

		hashtable.put("key1", 1);
		hashtable.put("key2", 2);
		hashtable.put("key3", 3);

		// should always be false since hashtable does not allow null keys
		assertEquals(false, hashtable.containsKey(null));

		// should be false since key is not the right type
		assertEquals(false, hashtable.containsKey(9));

		// should be false
		assertEquals(false, hashtable.containsKey("key4"));

		// should be true
		assertEquals(true, hashtable.containsKey("key1"));
	}

	@Test
	public void containsValueMethodTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

		hashtable.put("key1", 1);
		hashtable.put("key2", 2);
		hashtable.put("key3", 3);
		hashtable.put("null", null);

		// should return true - null values are allowed
		assertEquals(true, hashtable.containsValue(null));

		// should return false
		assertEquals(false, hashtable.containsValue(4));

		// should return true
		assertEquals(true, hashtable.containsValue(1));

	}

	@Test
	public void getMethodTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

		hashtable.put("key1", 1);
		hashtable.put("key2", 2);
		hashtable.put("key3", 3);
		hashtable.put("null", null);

		// get should return 2
		assertEquals(2, hashtable.get("key2").intValue());

		// get should return null because "key4" does not exist
		assertEquals(null, hashtable.get("key4"));

		// get should return null because it is assigned to "null" key
		assertEquals(null, hashtable.get("null"));
	}

	@Test
	public void removeMethodTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

		
		// many values in 2 bucket table will also make 
		//  the table dinamically grow so that part will
		//  implicitly be tested too
		 
		for(int i = 1; i < 13 ; i++){
			hashtable.put("key" + i, (i%3)==0 ? 3 : i%3);
		}
		
		assertEquals(12, hashtable.size());

		// these two calls change nothing
		hashtable.remove("abc");
		hashtable.remove(null);

		// size remains the same
		assertEquals(12, hashtable.size());

		// check if there is "key1" in table after its removal
		assertEquals(true, hashtable.containsKey("key8"));
		hashtable.remove("key8");
		assertEquals(false, hashtable.containsKey("key8"));

		// size should now be 2
		assertEquals(11, hashtable.size());
	}

	@Test
	public void emptyMethodTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

		hashtable.put("key1", 1);
		hashtable.put("key2", 2);

		hashtable.remove("key1");
		hashtable.remove("key2");

		assertEquals(true, hashtable.isEmpty());
	}

	@Test
	public void toStringMethodTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();
		assertEquals("[]", hashtable.toString());
		hashtable.put("key1", 1);
		assertEquals("[key1=1]", hashtable.toString());
	}

	@Test
	public void iteratorRemoveAllTest() {
		SimpleHashtable<String, String> hashtable = new SimpleHashtable<>();
		fillTableRandomly(hashtable);
		Iterator<TableEntry<String, String>> it = hashtable.iterator();
		while (it.hasNext()) {
			it.next();
			it.remove();
		}
		assertEquals(true, hashtable.isEmpty());
	}

	@Test(expected = IllegalStateException.class)
	public void iteratorRemoveWithoutCallingNextFirst() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();
		hashtable.put("key1", 1);
		hashtable.put("key2", 2);
		Iterator<TableEntry<String, Integer>> it = hashtable.iterator();
		// should throw exception
		it.remove();
	}

	@Test(expected = IllegalStateException.class)
	public void iteratorRemoveTwiceInARowTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();
		hashtable.put("key1", 1);
		hashtable.put("key2", 2);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = hashtable.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("key1")) {
				iter.remove();
				//should throw exception now!
				iter.remove();
			}
		}
	}
	
	@Test
	public void iteratorHasNextEmptyTable(){
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = hashtable.iterator();
		assertEquals(false, iter.hasNext());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void iteratorManyNextCallsTest(){
		SimpleHashtable<String, String> hashtable = new SimpleHashtable<>();
		fillTableRandomly(hashtable);
		Iterator<SimpleHashtable.TableEntry<String, String>> iter = hashtable.iterator();
		
		//should throw exception
		while(true){
			iter.next();
		}
	}
	
	@Test(expected = ConcurrentModificationException.class)
	public void iteratorOutsideStructuralModification(){
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();
		hashtable.put("key1", 1);
		hashtable.put("key2", 2);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = hashtable.iterator();
		
		while(iter.hasNext()){
			TableEntry<String, Integer> entry = iter.next();
			if(entry.getKey().equals("key1")){
				//outside modification should cause next iterator call to throw exception
				hashtable.remove("key1");
			}
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void constructorInvalidCapacity() {
		@SuppressWarnings("unused")
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void putInvalidKeyTest() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		table.put(null, 5);
	}

	@Test
	public void constructorTableSizeTest() {
		SimpleHashtable<String, Integer> table1 = new SimpleHashtable<>(1);
		assertEquals("Expected getTableLength() 1.", 1, table1.getTableLength());

		SimpleHashtable<String, Integer> table2 = new SimpleHashtable<>(3);
		assertEquals("Expected getTableLength() 4.", 4, table2.getTableLength());

		SimpleHashtable<String, Integer> table3 = new SimpleHashtable<>(32);
		assertEquals("Expected getTableLength() 32.", 32, table3.getTableLength());

		assertEquals("Expected table1 to be empty.", true, table1.isEmpty());
		assertEquals("Expected table2 to be empty.", true, table2.isEmpty());
		assertEquals("Expected table3 to be empty.", true, table3.isEmpty());
	}

	@Test
	public void putFullTest() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		assertEquals("Expected getTableLength() 2.", 2, table.getTableLength());

		table.put("BOSS", 15);
		assertEquals("Expected getTableLength() 2.", 2, table.getTableLength());

		table.put("ANTIMOND", 19);
		assertEquals("Expected getTableLength() 2.", 2, table.getTableLength());

		table.put("ANTIMOND", 20);
		assertEquals("Expected getTableLength() 4.", 4, table.getTableLength());
		assertEquals("Expected 2", 2, table.size());

		table.put("Cvita", 16);
		assertEquals("Expected getTableLength() 4.", 4, table.getTableLength());

		table.put("banANko", 3);
		assertEquals("Expected getTableLength() 8.", 8, table.getTableLength());

		table.put("BOSS", 16);
		assertEquals("Expected getTableLength() 8.", 8, table.getTableLength());

		table.put("banANko", 4);
		assertEquals("Expected getTableLength() 8.", 8, table.getTableLength());
		assertEquals("Expected 4", 4, table.size());
	}

	@Test
	public void getFullTest() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(1);

		assertEquals("Expected getTableLength() 1.", 1, table.getTableLength());
		assertEquals("Expected null reference.", null, table.get(null));

		table.put("Ankica", 15);
		assertEquals("Expected getTableLength() 1.", 1, table.getTableLength());
		assertEquals("Expected value 15.", Integer.valueOf(15), table.get("Ankica"));

		table.put("Brankica", 19);
		assertEquals("Expected getTableLength() 2.", 2, table.getTableLength());
		assertEquals("Expected value 19.", Integer.valueOf(19), table.get("Brankica"));

		table.put("Brankica", 20);
		assertEquals("Expected getTableLength() 4.", 4, table.getTableLength());
		assertEquals("Expected size 2.", 2, table.size());
		assertEquals("Expected value 20.", Integer.valueOf(20), table.get("Brankica"));

		table.put("Brankica", 21);
		assertEquals("Expected getTableLength() 4.", 4, table.getTableLength());
		assertEquals("Expected size 2.", 2, table.size());
		assertEquals("Expected value 15.", Integer.valueOf(15), table.get("Ankica"));

	}

	@Test
	public void containsFullTest() {
		SimpleHashtable<Integer, Integer> table = new SimpleHashtable<>(7);

		table.put(1, 15);
		table.put(2, 19);
		assertEquals("Expected '2' not to be in the table.", false, table.containsKey("2"));
		assertEquals("Expected value 15 in the table.", true, table.containsValue(15));

		table.put(234, 20);
		assertEquals("Expected 234 in the table.", true, table.containsKey(234));
		assertEquals("Expected 1 in the table.", true, table.containsKey(1));

		table.put(708921310, 12);
		table.put(999999, 30);
		table.put(91910901, 14);
		table.put(91910901, 15);

		assertEquals("Expected that value 20 exists.", true, table.containsValue(20));
		assertEquals("Expected 'Florami' to be in the table.", true, table.containsKey(91910901));
		assertEquals("Expected 'Jelenko' not to be in the table.", false, table.containsKey("Jelenko"));
		assertEquals("Did not expect 14 to be inside.", false, table.containsValue(14));
	}

	@Test
	public void removeWithNullKeyTest() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(1);
		table.remove(null);
	}

	@Test
	public void removeFullTest() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(5);

		table.remove("žđsdažšasd");
		table.remove("žđšpoiuztre");

		fillTableWithImena(table);
		
		table.remove("Marko");
		table.remove("Ante");
		assertEquals("Expected table size to be 8.", 8, table.size());

		table.remove("Hrvoje");
		table.remove("Frano");
		assertEquals("Expected table size to be 6.", 6, table.size());

		for (int i = 0; i < imena.length; i++) {
			table.remove(imena[i]);
		}
		assertEquals("Expected table size to be 0.", 0, table.size());
		assertEquals("Expected table to be empty.", true, table.isEmpty());
	}

	@Test
	public void clearTest() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();

		fillTableWithImena(table);
		
		assertEquals("Expected table size to be 10.", 10, table.size());
		assertEquals("Expected getTableLength() to be 16.", 16, table.getTableLength());

		table.clear();

		assertEquals("Expected table size to be 0.", 0, table.size());
		assertEquals("Expected table to be empty.", true, table.isEmpty());
		assertEquals("Expected table capacity to be 16.", 16, table.getTableLength());

	}

	@Test
	public void iteratorSequence() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();

		table.put("Vrano", 30);
		table.put("Jaken", 14);
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();
		assertEquals("Expected 'Vrano=30' to appear.", "Vrano=30", iterator.next().toString());

		Iterator<TableEntry<String, Integer>> iterator2 = table.iterator();
		iterator2.next();
		iterator2.remove();
		assertEquals("Expected 'Jaken=14' to appear.", "Jaken=14", iterator2.next().toString());
		assertEquals("Excpected table to look like '[Jaken=14]'.", "[Jaken=14]", table.toString());
	}

	@Test
	public void iteratorSequence2() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		Iterator<TableEntry<String, Integer>> iterator = examMarks.iterator();

		// This code should pass without a problem.
		while (iterator.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iterator.next();
			if (pair.getKey().equals("Ivana")) {
				iterator.remove();
			}
		}
	}

	// Test from assignment documentation for Iterator class.
	@Test
	public void iteratorSequencePrintChecker() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		assertEquals("Expected table size to be 4.", 4, examMarks.size());
		assertEquals("Expected getTableLength() to be 8.", 8, examMarks.getTableLength());

		StringBuilder sb = new StringBuilder();
		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			sb.append(pair.getKey() + " => " + pair.getValue() + "\n");
		}
		String result = "Ante => 2\nIvana => 5\nJasna => 2\nKristina => 5\n";
		assertEquals("Result of for iteration is different.", result, sb.toString());

		StringBuilder sb2 = new StringBuilder();
		for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
			for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
				sb2.append("(" + pair1.getKey() + " => " + pair1.getValue() + ")" + " - " + "(" + pair2.getKey()
						+ " => " + pair2.getValue() + ")" + "\n"

				);
			}
		}
		String result2 = "(Ante => 2) - (Ante => 2)\n" + "(Ante => 2) - (Ivana => 5)\n" + "(Ante => 2) - (Jasna => 2)\n"
				+ "(Ante => 2) - (Kristina => 5)\n" + "(Ivana => 5) - (Ante => 2)\n" + "(Ivana => 5) - (Ivana => 5)\n"
				+ "(Ivana => 5) - (Jasna => 2)\n" + "(Ivana => 5) - (Kristina => 5)\n" + "(Jasna => 2) - (Ante => 2)\n"
				+ "(Jasna => 2) - (Ivana => 5)\n" + "(Jasna => 2) - (Jasna => 2)\n" + "(Jasna => 2) - (Kristina => 5)\n"
				+ "(Kristina => 5) - (Ante => 2)\n" + "(Kristina => 5) - (Ivana => 5)\n"
				+ "(Kristina => 5) - (Jasna => 2)\n" + "(Kristina => 5) - (Kristina => 5)\n";
		assertEquals("Result2 of for iteration is different.", result2, sb2.toString());

	}

	// Test from assignment documentation for Iterator class.
	@Test(expected = IllegalStateException.class)
	public void iteratorSequenceIllegalSecondRemoveAction() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iterator = examMarks.iterator();
		while (iterator.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iterator.next();
			if (pair.getKey().equals("Ivana")) {
				iterator.remove();
				iterator.remove();
			}
		}
	}

	// Test from assignment documentation for Iterator class.
	@Test(expected = ConcurrentModificationException.class)
	public void iteratorSequenceIllegalRemoveActionOutsideOfIterator() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iterator = examMarks.iterator();
		while (iterator.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iterator.next();
			if (pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
			}
		}
	}

	// Trying to iterate over collection which has no more elements should
	// result in exception.
	@Test(expected = NoSuchElementException.class)
	public void iteratorForcingNoSuchElementException1() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();
		iterator.next();
	}

	// Trying to iterate over collection which has no more elements should
	// result in exception.
	@Test(expected = NoSuchElementException.class)
	public void iteratorForcingNoSuchElementException2() {
		// Must throw!
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(15);
		table.put("maca", 15);
		table.put("krava", 19);
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();
		iterator.next();
		iterator.next();
		iterator.next();
	}

	@Test
	public void iteratorEmptyCollection() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>(2);
		Iterator<TableEntry<String, String>> iterator = table.iterator();
		
		for (int i = 0; i < 10; i++) {
			assertEquals("Expected collection to be empty.", false, iterator.hasNext());	
		}
	}

	// Trying to remove element when iterator is not pointing at any, should
	// result in IllegalStateException.
	@Test(expected = IllegalStateException.class)
	public void iteratorForcingIllegalStateException() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>(2);
		Iterator<TableEntry<String, String>> iterator = table.iterator();
		iterator.remove();
	}

	// Each method performed with the instance of object Iterator, when
	// underlying collection was modified, should result in a
	// ConcurrentModificationException.
	@Test(expected = ConcurrentModificationException.class)
	public void iteratorForcingConcurentModificationException1() {
		// Must throw!
		SimpleHashtable<String, String> table = new SimpleHashtable<>(1);
		table.put("Miško", "brod");
		table.put("Mladen", "automobil");
		table.put("Vedran", "zrakoplov");

		Iterator<TableEntry<String, String>> iterator = table.iterator();
		table.remove("Mladen");
		iterator.next();
	}

	// Checking method remove.
	@Test(expected = ConcurrentModificationException.class)
	public void iteratorForcingConcurentModificationException2() {
		// Must throw!
		SimpleHashtable<String, String> table = new SimpleHashtable<>(4);
		table.put("Miško", "brod");
		table.put("Mladen", "automobil");
		table.put("Vedran", "zrakoplov");

		Iterator<TableEntry<String, String>> iterator = table.iterator();
		table.remove("Miško");
		iterator.remove();
	}
	
	private void fillTableRandomly(SimpleHashtable<String, String> hashtable) {
		for (int i = 0; i < brojevi.length; i++) {
			hashtable.put(imena[rand.nextInt(10)], brojevi[rand.nextInt(10)]);
		}		
	}
	
	private void fillTableWithImena(SimpleHashtable<String, Integer> table) {
		for (int i = 0; i < imena.length; i++) {
			table.put(imena[i], rand.nextInt(50));
		}		
	}
		
}