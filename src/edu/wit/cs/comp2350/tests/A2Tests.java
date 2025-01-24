package edu.wit.cs.comp2350.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Timeout.ThreadMode;
import org.junit.jupiter.api.Test;

import edu.wit.cs.comp2350.A2;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class A2Tests {

	private void _testHeap(float[] values, float expected) {

		float result = 0; 
		result = A2.heapAdd(values);

		assertTrue(Float.isFinite(result), "Result is infinity or NaN");
		assertEquals(expected, result, Math.abs(result)/1000, "Result is not close - not all input values are summed.");
		assertEquals(expected, result, 0, "Rounding error in result is not minimized - values are not added in the correct order.");

	}

	private float[] _generateRandIntArray(int size) {
		float[] ret = new float[size];

		Random r = new Random();
		for (int i = 0; i < size; i++) {
			ret[i] = r.nextInt(10000000);
		}
		return ret;
	}	

	private float[] _generateRandFloatArray(int size) {
		float[] ret = new float[size];

		Random r = new Random();
		for (int i = 0; i < size; i++) {
			ret[i] = r.nextFloat();
		}
		return ret;
	}

	@Test
	@Timeout(value = 15000, unit = TimeUnit.MILLISECONDS, threadMode = ThreadMode.SEPARATE_THREAD)
	public void test0_Empty() {
		_testHeap(new float[] {}, 0);
	}

	@Test
	@Timeout(value = 15000, unit = TimeUnit.MILLISECONDS, threadMode = ThreadMode.SEPARATE_THREAD)
	public void test1_Small() {
		_testHeap(new float[] {1, 2}, 3);
		_testHeap(new float[] {1, 2, 3}, 6);
		_testHeap(new float[] {100, 200, 300}, 600);
		_testHeap(new float[] {(float) .1, (float) .2, (float) .3}, (float) .6);
		_testHeap(new float[] {(float) .0001, (float) .0002, (float) .0003}, (float) .0006);
		_testHeap(new float[] {1000000000, 2000000000, (float) 3000000000.0}, (float) 6000000000.0);
	}


	@Test
	@Timeout(value = 15000, unit = TimeUnit.MILLISECONDS, threadMode = ThreadMode.SEPARATE_THREAD)
	public void test2_RandInts() {
		for (int k = 0; k < 100; k++) {
			float[] randArray = _generateRandIntArray(1000);
			float[] dup = new float[1000];
			for (int i = 0; i < 1000; i++)
				dup[i] = randArray[i];

			_testHeap(randArray, A2.min2ScanAdd(dup));
		}
	}

	@Test
	@Timeout(value = 15000, unit = TimeUnit.MILLISECONDS, threadMode = ThreadMode.SEPARATE_THREAD)
	public void test3_RandFloats() {
		for (int k = 0; k < 100; k++) {
			float[] randArray = _generateRandFloatArray(1000);
			float[] dup = new float[1000];
			for (int i = 0; i < 1000; i++)
				dup[i] = randArray[i];

			_testHeap(randArray, A2.min2ScanAdd(dup));
		}
	}

	private void _testSizeFront(int size, float exp) {
		float[]f = new float[size];
		Arrays.fill(f,  (float) 1E-12);
		f[0] = (float) 1;

		_testHeap(f, exp);
	}

	private void _testSizeBack(int size, float exp) {
		float[]f = new float[size];
		Arrays.fill(f,  (float) 1E-12);
		f[size-1] = (float) 1;

		_testHeap(f, exp);
	}

	@Test
	@Timeout(value = 15000, unit = TimeUnit.MILLISECONDS, threadMode = ThreadMode.SEPARATE_THREAD)
	public void test4_Big() {
		_testSizeFront(10000000, (float) 1.00001);
		_testSizeFront(1000000, (float) 1.000001);
		_testSizeFront(100000, (float) 1.0000001);
		_testSizeFront(10000, (float) 1);
		_testSizeFront(1000, (float) 1);

		_testSizeBack(10000000, (float) 1.00001);
		_testSizeBack(1000000, (float) 1.000001);
		_testSizeBack(100000, (float) 1.0000001);
		_testSizeBack(10000, (float) 1);
		_testSizeBack(1000, (float) 1);
	}

	@Test
	public void test5_PublicMethods() {
		List<String> mNames = Arrays.asList("heapAdd", "seqAdd", "sortAdd", "min2ScanAdd",
				"main", "wait", "equals", "toString", "hashCode", "getClass", "notify", "notifyAll");

		for (Method m: A2.class.getMethods())
			assertTrue(Modifier.isPrivate(m.getModifiers()) || mNames.contains(m.getName()),
					"method named " + m.getName() + " should be private.");		
	}

}
