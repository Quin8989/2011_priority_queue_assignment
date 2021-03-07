
import java.util.AbstractQueue;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PriorityQueue2011<Key> extends AbstractQueue<Key> {
	private Key[] pq; // store items at indices 1 to n
	private int size; // number of items on priority queue
	private Comparator<Key> comparator; // optional comparator

	/**
	 * Initializes an empty priority queue with the given initial capacity.
	 */
	@SuppressWarnings("unchecked")
	public PriorityQueue2011() {
		pq = (Key[]) new Object[15];
		size = 0;
	}

	/*
	 * resize the underlying array to have the given capacity
	 * 
	 * @param capacity is the size of the new array
	 */
	private void resize(int capacity) {
		assert capacity > size;
		@SuppressWarnings("unchecked")
		Key[] temp = (Key[]) new Object[capacity];
		for (int i = 1; i <= size; i++) {
			temp[i] = pq[i];
		}
		pq = temp;
	}

	/**
	 * Adds a new key to this priority queue.
	 *
	 * @param x the key to add to this priority queue
	 */
	@Override
	public boolean offer(Key x) {
		return add(x);
	}

	/**
	 * Adds a new key to this priority queue.
	 *
	 * @param x the key to add to this priority queue
	 * @return returns whether x has been successfully added
	 */
	@Override
	public boolean add(Key x) {
		// double size of array if necessary
		if (size == pq.length - 1)
			resize(pq.length * 2);

		pq[++size] = x;
		swim(size);
		return true;

	}

	/**
	 * Removes and returns a smallest key on this priority queue.
	 *
	 * @return a smallest key on this priority queue
	 * @throws NoSuchElementException if this priority queue is empty
	 * 
	 * uses code from https://algs4.cs.princeton.edu/24pq/MaxPQ.java.html
	 */
	@Override
	public Key poll() {
		if (isEmpty())
			return null;
		Key min = pq[1];
		exch(1, size--);
		sink(1);
		pq[size + 1] = null;
		if ((size > 0) && (size == (pq.length - 1) / 4))
			resize(pq.length / 2);
		return min;
	}

	/**
	 * Removes and returns a smallest key on this priority queue.
	 *
	 * @return a smallest key on this priority queue
	 * @throws NoSuchElementException if this priority queue is empty
	 */

	@Override
	public Key remove() {
		return poll();
	}

	/**
	 * Returns a smallest key on this priority queue.
	 *
	 * @return a smallest key on this priority queue
	 * @return null if this priority queue is empty
	 */
	@Override
	public Key peek() {
		if (isEmpty())
			return null;
		return pq[1];
	}

	/**
	 * Returns a smallest priority key on this priority queue.
	 *
	 * @return a smallest priority key on this priority queue
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	@Override
	public Key element() {
		if (isEmpty())
			throw new NoSuchElementException();
		return pq[1];
	}

	/**
	 * Returns the number of keys on this priority queue.
	 *
	 * @return the number of keys on this priority queue
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Describes contents of PQ array in string form
	 *
	 * @return String representation of PQ array
	 */
	@Override
	public String toString() {
		if (size == 0)
			return "[]";
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < size + 1; i++) {
			if (i == (size)) {
				sb.append(pq[i]);
				return sb.append(']').toString();
			} else {
				sb.append(pq[i]);
				sb.append(',').append(' ');
			}
		}
		return null;

	}

	/**
	 * outputs the binary heap as a string representation of a tree
	 *
	 * @return String representation of tree
	 */
	public String toTree() {
		int h = (int) (Math.log(size - 1) / Math.log(2));
		StringBuilder tree_string = new StringBuilder();
		Key[] arry = (Key[]) this.pq;
		int spacing = 0;
		int index = 1;
		for (int i = 0; i <= h; i++) {
			int leftSpacing = 0;
			for (int p = 0; p <= h - i; p++) {
				leftSpacing += (int) Math.ceil(Math.pow(2, p));
			}
			int j = 1;
			while (j <= Math.pow(2, i)) {
				if (index > this.size)
					break;

				if (j > 1 && i >= 0) {
					if (i == 0) {
						tree_string.append(String.format(" %" + leftSpacing + "s", arry[index].toString()));
					} else {
						tree_string.append(String.format(" %" + spacing + "s", arry[index].toString()));
					}
				} else {
					if (i == h) {
						tree_string.append(String.format("%s", arry[index].toString()));
					} else {
						tree_string.append(String.format(" %" + leftSpacing + "s", arry[index].toString()));
					}
				}

				j++;
				index++;
			}
			tree_string.append('\n');
			spacing = leftSpacing;

		}
		return tree_string.toString();
	}

	/*************************************************************************
	 * Helper functions: modified for this implementation(Original author of code is Robert Sedgewick and Kevin
	 * Wayne, source: https://algs4.cs.princeton.edu/24pq/MaxPQ.java.html
	 ************************************************************************/
	
	private void swim(Integer k) {
		while (k > 1 && greater(k / 2, k)) {
			exch(k, k / 2);
			k = k / 2;
		}
	}

	private void sink(Integer k) {
		while (2 * k <= size) {
			Integer j = 2 * k;
			if (j < size && greater(j, j + 1))
				j++;
			if (!greater(k, j))
				break;
			exch(k, j);
			k = j;
		}
	}

	@SuppressWarnings("unchecked")
	private boolean greater(Integer i, Integer j) {

		if (comparator == null) {
			return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
		} else {
			return comparator.compare(pq[i], pq[j]) > 0;
		}
	}

	private void exch(Integer i, Integer j) {
		Key swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<Key> iterator() {
		throw new UnsupportedOperationException();
	}
}
