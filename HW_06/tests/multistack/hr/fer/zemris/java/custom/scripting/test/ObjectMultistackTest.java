package hr.fer.zemris.java.custom.scripting.test;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class ObjectMultistackTest {

	@Test
	public void overallTestStack() {
		ObjectMultistack stack = new ObjectMultistack();

		stack.push("Ante", new ValueWrapper(5));
		assertEquals(Integer.valueOf(5), stack.peek("Ante").getValue());
		assertEquals(Integer.valueOf(5), stack.pop("Ante").getValue());

		assertEquals(true, stack.isEmpty("Štefica"));

		stack.push("Ante", new ValueWrapper(5));
		stack.push("Ante", new ValueWrapper(10));
		stack.push("Ante", new ValueWrapper(15));

		assertEquals(15, stack.peek("Ante").getValue());
		stack.peek("Ante").decrement(4);
		assertEquals(11, stack.pop("Ante").getValue());

		assertEquals(10, stack.peek("Ante").getValue());

	}

	@Test(expected = NoSuchElementException.class)
	public void testNullPopper() {
		ObjectMultistack stack = new ObjectMultistack();
		stack.pop("Something");
	}

	@Test(expected = NoSuchElementException.class)
	public void testNullPeeker() {
		ObjectMultistack stack = new ObjectMultistack();
		stack.peek("Something");
	}

	@Test
	public void sizeTestStack() {
		ObjectMultistack stack = new ObjectMultistack();
		stack.push("Ante", new ValueWrapper(5));

		assertEquals(false, stack.isEmpty("Ante"));
		stack.peek("Ante");
		assertEquals(false, stack.isEmpty("Ante"));
		stack.pop("Ante");
		assertEquals(true, stack.isEmpty("Ante"));

	}

	@Test(expected = RuntimeException.class)
	public void testInvalidArithmeticValues() {
		ValueWrapper wrap = new ValueWrapper(5);
		wrap.increment('Z');
	}

	// Zero passed as Integer.
	@Test(expected = ArithmeticException.class)
	public void testDivisionByZero1() {
		ValueWrapper wrap = new ValueWrapper(0);
		wrap.divide(0);
	}

	// Zero passed as Double.
	@Test(expected = ArithmeticException.class)
	public void testDivisionByZero2() {
		ValueWrapper wrap = new ValueWrapper(0);
		wrap.divide(0.0);
	}

	// Zero passed as String.
	@Test(expected = ArithmeticException.class)
	public void testDivisionByZero3() {
		ValueWrapper wrap = new ValueWrapper(0);
		wrap.divide("0");
	}

	// Zero passed as null pointer.
	@Test(expected = ArithmeticException.class)
	public void testDivisionByZero4() {
		ValueWrapper wrap = new ValueWrapper(0);
		wrap.divide(null);
	}

	@Test
	public void testNull() {
		ValueWrapper wrap = new ValueWrapper(null);
		wrap.increment(1);
		assertEquals(Integer.valueOf(1), (Integer) wrap.getValue());
		wrap.increment(2);
		assertEquals(Integer.valueOf(3), (Integer) wrap.getValue());

	}

	@Test
	public void testNullZero() {
		ValueWrapper wrap = new ValueWrapper(1);
		wrap.increment(null);
		assertEquals(Integer.valueOf(1), (Integer) wrap.getValue());
		wrap.increment(2);
		assertEquals(Integer.valueOf(3), (Integer) wrap.getValue());
	}

	@Test
	public void testIncrementAndDecrement() {

		// Integer - addition with positive and negative integer -> should
		// result in Integer
		ValueWrapper wrap1 = new ValueWrapper(5);
		wrap1.increment(7);
		assertEquals(Integer.valueOf(12), (Integer) wrap1.getValue());
		wrap1.increment(-18);
		assertEquals(Integer.valueOf(-6), (Integer) wrap1.getValue());

		// Integer - subtraction with positive and negative integer -> should
		// result in Integer
		ValueWrapper wrap2 = new ValueWrapper(99);
		wrap2.decrement(89);
		assertEquals(Integer.valueOf(10), (Integer) wrap2.getValue());
		wrap2.decrement(-20);
		assertEquals(Integer.valueOf(30), (Integer) wrap2.getValue());

		// Integer - addition with positive and negative double -> should result
		// in Double
		ValueWrapper wrap3 = new ValueWrapper(50);
		wrap3.increment(5.5);
		assertEquals(Double.valueOf(55.5), (Double) wrap3.getValue());
		ValueWrapper wrap4 = new ValueWrapper(50);
		wrap4.increment(-55.5);
		assertEquals(Double.valueOf(-5.5), (Double) wrap4.getValue());

		// Integer - subtraction with positive and negative double -> should
		// result in Double
		ValueWrapper wrap5 = new ValueWrapper(0);
		wrap5.decrement(5.6);
		assertEquals(Double.valueOf(-5.6), (Double) wrap5.getValue());
		ValueWrapper wrap6 = new ValueWrapper(80);
		wrap6.decrement(-82.3);
		assertEquals(Double.valueOf(162.3), (Double) wrap6.getValue());

		// ---------------------------------------------------------------------------

		// Double - addition with positive and negative integer -> should
		// result in Double
		ValueWrapper wrap11 = new ValueWrapper(5.0);
		wrap11.increment(7);
		assertEquals(Double.valueOf(12.0), (Double) wrap11.getValue());
		wrap11.increment(-18);
		assertEquals(Double.valueOf(-6), (Double) wrap11.getValue());

		// Double - subtraction with positive and negative integer -> should
		// result in Double
		ValueWrapper wrap12 = new ValueWrapper(99.5);
		wrap12.decrement(80);
		assertEquals(Double.valueOf(19.5), (Double) wrap12.getValue());
		wrap12.decrement(-20);
		assertEquals(Double.valueOf(39.5), (Double) wrap12.getValue());

		// Double - addition with positive and negative double -> should result
		// in Double
		ValueWrapper wrap13 = new ValueWrapper(52.5);
		wrap13.increment(5.5);
		assertEquals(Double.valueOf(58.0), (Double) wrap13.getValue());
		wrap13.increment(-7.5);
		assertEquals(Double.valueOf(50.5), (Double) wrap13.getValue());

		// Double - subtraction with positive and negative double -> should
		// result in Double
		ValueWrapper wrap14 = new ValueWrapper(0.0);
		wrap14.decrement(14.4);
		assertEquals(Double.valueOf(-14.4), (Double) wrap14.getValue());
		wrap14.decrement(-4);
		assertEquals(Double.valueOf(-10.4), (Double) wrap14.getValue());
	}

	@Test
	public void testIncrementAndDecrementScientific() {

		ValueWrapper wrap1 = new ValueWrapper("0.5");
		wrap1.increment("155");
		assertEquals(Double.valueOf(155.5), (Double) wrap1.getValue());

		ValueWrapper wrap2 = new ValueWrapper("0.5");
		wrap2.increment("1E2");
		assertEquals(Double.valueOf(100.5), (Double) wrap2.getValue());

		ValueWrapper wrap3 = new ValueWrapper("-141");
		wrap3.increment("1e-1");
		assertEquals(Double.valueOf(-140.9), (Double) wrap3.getValue());
	}

	@Test
	public void testMultiplication() {

		ValueWrapper wrap1 = new ValueWrapper("0.5");
		wrap1.multiply("5");
		assertEquals(Double.valueOf(2.5), (Double) wrap1.getValue());

		ValueWrapper wrap2 = new ValueWrapper("12.80");
		wrap2.multiply("-2");
		assertEquals(Double.valueOf(-25.6), (Double) wrap2.getValue());

		ValueWrapper wrap3 = new ValueWrapper(-1);
		wrap3.multiply(3);
		assertEquals(Integer.valueOf(-3), (Integer) wrap3.getValue());

		ValueWrapper wrap4 = new ValueWrapper(2);
		wrap4.multiply(5);
		assertEquals(Integer.valueOf(10), (Integer) wrap4.getValue());

		ValueWrapper wrap5 = new ValueWrapper(13);
		wrap5.multiply(2.5);
		assertEquals(Double.valueOf(32.5), (Double) wrap5.getValue());

		ValueWrapper wrap6 = new ValueWrapper(null);
		wrap6.multiply(25.0);
		assertEquals(Double.valueOf(0), (Double) wrap6.getValue());

		ValueWrapper wrap7 = new ValueWrapper("0.5");
		wrap7.multiply(null);
		assertEquals(Double.valueOf(0), (Double) wrap7.getValue());

		ValueWrapper wrap8 = new ValueWrapper(null);
		wrap8.multiply("5");
		assertEquals(Integer.valueOf(0), (Integer) wrap8.getValue());

		ValueWrapper wrap9 = new ValueWrapper("15");
		wrap9.multiply(null);
		assertEquals(Integer.valueOf(0), (Integer) wrap9.getValue());

		ValueWrapper wrap10 = new ValueWrapper("-55.5");
		wrap10.multiply("-2");
		assertEquals(Double.valueOf(111), (Double) wrap10.getValue());
	}

	@Test
	public void testDivision() {

		ValueWrapper wrap1 = new ValueWrapper("25");
		wrap1.divide("5");
		assertEquals(Integer.valueOf(5), (Integer) wrap1.getValue());

		ValueWrapper wrap2 = new ValueWrapper("12.80");
		wrap2.divide("2");
		assertEquals(Double.valueOf(6.4), (Double) wrap2.getValue());

		ValueWrapper wrap3 = new ValueWrapper(-1);
		wrap3.divide(0.5);
		assertEquals(Double.valueOf(-2), (Double) wrap3.getValue());

		ValueWrapper wrap4 = new ValueWrapper(null);
		wrap4.divide(5);
		assertEquals(Integer.valueOf(0), (Integer) wrap4.getValue());

		ValueWrapper wrap5 = new ValueWrapper(null);
		wrap5.divide(2.5);
		assertEquals(Double.valueOf(0), (Double) wrap5.getValue());

		ValueWrapper wrap6 = new ValueWrapper("25e2");
		wrap6.divide(500);
		assertEquals(Double.valueOf(5), (Double) wrap6.getValue());

		ValueWrapper wrap7 = new ValueWrapper("0.5");
		wrap7.divide(2);
		assertEquals(Double.valueOf(0.25), (Double) wrap7.getValue());

		ValueWrapper wrap8 = new ValueWrapper(55);
		wrap8.divide("-5.5E1");
		assertEquals(Double.valueOf(-1), (Double) wrap8.getValue());

		ValueWrapper wrap9 = new ValueWrapper(100);
		wrap9.divide("2e2");
		assertEquals(Double.valueOf(0.5), (Double) wrap9.getValue());

		ValueWrapper wrap10 = new ValueWrapper("-1e3");
		wrap10.divide("2e2");
		assertEquals(Double.valueOf(-5), (Double) wrap10.getValue());
	}

	@Test
	public void testNumCompare() {
		ValueWrapper wrap = new ValueWrapper(5);
		assertTrue(wrap.numCompare(4) >= 1);
		assertTrue(wrap.numCompare(5) == 0);
		assertTrue(wrap.numCompare(6) <= -1);
		assertTrue(wrap.numCompare(6.4) <= -1);
		assertTrue(wrap.numCompare(5.0) == 0);
		assertTrue(wrap.numCompare(null) >= 1);
		assertTrue(wrap.numCompare("5e1") <= -1);
	}
}
