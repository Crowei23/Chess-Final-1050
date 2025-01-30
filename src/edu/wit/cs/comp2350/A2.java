package edu.wit.cs.comp2350;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @author Isabella Crowe
 * @version 1.0
 * @since 1-27-25
 * @due 1-30-25
 * 
 * 
 *      Adds floating point numbers with varying precision
 * 
 *      Wentworth Institute of Technology COMP 2350 Assignment 2
 * 
 */

public class A2 {
	
	/**
	 * Builds the heap from the given array of float(s)
	 * 
	 * @param a,    The array of float that will transformed into the heap
	 * @param size, The number of elements in the array
	 */

	private static void build_heap(float[] a, int size) {
		for (int i = size / 2 - 1; i >= 0; i--) {
			heapify(a, size, i);
		}

	}

	/**
	 * 
	 * Ensures the heap property is maintained for the subtree This method assume
	 * that the left and right subtrees
	 * 
	 * 
	 * @param a,    The array of the float element representing the heap
	 * @param size, The total number of elements in the array
	 * @param i,    The index of the root note
	 */
	private static void heapify(float[] a, int size, int i) {

		// Initializes the smallest root
		int smallest = i;
		// Calculate the indices for the left and right children
		int left = 2 * i + 1;
		int right = 2 * i + 2;

		// If left child is smaller then root, it will update smallest
		if (left < size && a[left] < a[smallest]) {
			smallest = left;
		}

		// If right child is smaller then the smallest update the smallest
		if (right < size && a[right] < a[smallest]) {
			smallest = right;
		}

		// If the smallest is not the root
		if (smallest != i) {
			// swaps the root with the smallest child
			float temp_stor = a[i];
			a[i] = a[smallest];
			a[smallest] = temp_stor;

			// Recursively heapify the affected subtree to ensure the property is maintained
			heapify(a, size, smallest);
		}
	}

	/**
	 * Extracts the minimum element from the heap, removes it, and restores the heap
	 * The minimum element is assumed to be at the root of the heap
	 * 
	 * 
	 * @param a,        The array of float elements representing the heap
	 * @param heapSize, The current szie of the heap
	 * @return The minimum element that was removed from the heap
	 */

	private static float extract_min(float[] a, int heap_size) {
		// The minimum element is the root
		float min = a[0];

		// Move the last element to the root and reduce the heap size
		a[0] = a[heap_size - 1];

		// Restore the heap property by heapifying from the root
		heapify(a, heap_size - 1, 0);

		return min;
	}

	/**
	 * 
	 * 
	 * Insets the element into the heap and restores the heap property The new
	 * element is added at the end of the heap and them moves it up the while
	 * maintaining the structure
	 * 
	 * 
	 * @param a,         The array of float elements representing the heap
	 * @param value,     The new value to be inserted into the heap
	 * @param heap_size, The current size of the heap
	 */
	private static void insert(float[] a, float value, int heap_size) {
		// Add the new element to the end of the array
		a[heap_size] = value;

		// Move the newly added element up to restore the heap property
		int i = heap_size;
		while (i > 0 && a[(i - 1) / 2] > a[i]) {
			float temp_num = a[i];
			a[i] = a[(i - 1) / 2];
			a[(i - 1) / 2] = temp_num;
			i = (i - 1) / 2;
		}
	}

	/**
	 * 
	 * Loops through the heap to extracting the two smallest element, summing them
	 * and inserting sum back into the heap.
	 * 
	 * @param a, An array of floats representing the element being processed. The
	 *           array must not be null or 0
	 * @return The remaining element in the heap is the smallest numbers after
	 *         summing the other numbers together.
	 */
	public static float heapAdd(float[] a) {

		// Handles the null and 0 lengths
		if (a == null || a.length == 0) {

			System.out.println("Array is empty or null, no sorting needed.");
			return 0;
		}
		int heap_size = a.length;
		build_heap(a, heap_size);

		// Continuously extract the two smallest elements, sum them, and insert
		// the sum back into the heap
		while (heap_size > 1) {
			// Extract the minimum elements from the heap
			float min1 = extract_min(a, heap_size);
			heap_size--; // Decrease the heap size
			float min2 = extract_min(a, heap_size);
			heap_size--; // Decrease the heap size

			// Sum the two minimum elements
			float sum = min1 + min2;

			// Insert the sum back into the heap
			insert(a, sum, heap_size);
			heap_size++; // Increase the heap size after insertion
		}

		// Returning the remaining element in the heap
		return a[0];
	}

	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	// sum an array of floats sequentially - high rounding error
	public static float seqAdd(float[] a) {
		float ret = 0;

		for (int i = 0; i < a.length; i++)
			ret += a[i];

		return ret;
	}

	// sort an array of floats and then sum sequentially - medium rounding error
	public static float sortAdd(float[] a) {
		Arrays.sort(a);
		return seqAdd(a);
	}

	// scan linearly through an array for two minimum values,
	// remove them, and put their sum back in the array. repeat.
	// minimized rounding error, inefficient operations
	public static float min2ScanAdd(float[] a) {
		int min1, min2;
		float tmp;

		if (a.length == 0)
			return 0;

		for (int i = 0, end = a.length; i < a.length - 1; i++, end--) {

			if (a[0] < a[1]) {
				min1 = 0;
				min2 = 1;
			} // initialize
			else {
				min1 = 1;
				min2 = 0;
			}

			for (int j = 2; j < end; j++) { // find two min indices
				if (a[min1] > a[j]) {
					min2 = min1;
					min1 = j;
				} else if (a[min2] > a[j]) {
					min2 = j;
				}
			}

			tmp = a[min1] + a[min2]; // add together
			if (min1 < min2) { // put into first slot of array
				a[min1] = tmp; // fill second slot from end of array
				a[min2] = a[end - 1];
			} else {
				a[min2] = tmp;
				a[min1] = a[end - 1];
			}
		}

		return a[0];
	}

	// read floats from a Scanner
	// returns an array of the floats read
	private static float[] getFloats(Scanner s) {
		ArrayList<Float> a = new ArrayList<Float>();

		while (s.hasNextFloat()) {
			float f = s.nextFloat();
			if (f >= 0)
				a.add(f);
		}
		return toFloatArray(a);
	}

	// copies an ArrayList to an array
	private static float[] toFloatArray(ArrayList<Float> a) {
		float[] ret = new float[a.size()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = a.get(i);
		return ret;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.printf("Enter the adding algorithm to use ([h]eap, [m]in2scan, se[q], [s]ort): ");
		char algo = s.next().charAt(0);

		System.out
				.printf("Enter the non-negative floats that you would like summed, followed by a non-numeric input: ");
		float[] values = getFloats(s);
		float sum = 0;

		s.close();

		if (values.length == 0) {
			System.out.println("You must enter at least one value");
			System.exit(0);
		} else if (values.length == 1) {
			System.out.println("Sum is " + values[0]);
			System.exit(0);

		}

		switch (algo) {
		case 'h':
			sum = heapAdd(values);
			break;
		case 'm':
			sum = min2ScanAdd(values);
			break;
		case 'q':
			sum = seqAdd(values);
			break;
		case 's':
			sum = sortAdd(values);
			break;
		default:
			System.out.println("Invalid adding algorithm");
			System.exit(0);
			break;
		}

		System.out.printf("Sum is %f\n", sum);

	}

}
