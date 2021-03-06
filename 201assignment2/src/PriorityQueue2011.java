
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
	 *
	 * @param initCapacity the initial capacity of this priority queue
	 */
	public PriorityQueue2011() {
		pq = (Key[]) new Object[15];
		size = 0;
	}

	// resize the underlying array to have the given capacity
	private void resize(int capacity) {
		assert capacity > size;
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
	 * @return
	 */
	@Override
	public boolean add(Key x) {
		// double size of array if necessary
		if (size == pq.length - 1)
			resize(2 * pq.length);

		// add x, and percolate it up to maintain heap invariant
		pq[++size] = x;
		swim(size);
		assert isMinHeap();
		return true;
	}

	/**
	 * Removes and returns a smallest key on this priority queue.
	 *
	 * @return a smallest key on this priority queue
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	@Override
	public Key poll() {
		if (isEmpty())
			return null;
		Key min = pq[1];
		exch(1, size--);
		sink(1);
		pq[size + 1] = null; // to avoid loitering and help with garbage collection
		if ((size > 0) && (size == (pq.length - 1) / 4))
			resize(pq.length / 2);
		assert isMinHeap();
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

	/***************************************************************************
	 * Helper functions to restore the heap invariant.
	 ***************************************************************************/

	private void swim(int k) {
		while (k > 1 && greater(k / 2, k)) {
			exch(k, k / 2);
			k = k / 2;
		}
	}

	private void sink(int k) {
		while (2 * k <= size) {
			int j = 2 * k;
			if (j < size && greater(j, j + 1))
				j++;
			if (!greater(k, j))
				break;
			exch(k, j);
			k = j;
		}
	}

	/***************************************************************************
	 * Helper functions for compares and swaps.
	 ***************************************************************************/
	private boolean greater(int i, int j) {
		if (comparator == null) {
			return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
		} else {
			return comparator.compare(pq[i], pq[j]) > 0;
		}
	}

	private void exch(int i, int j) {
		Key swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}

	// is pq[1..n] a min heap?
	private boolean isMinHeap() {
		for (int i = 1; i <= size; i++) {
			if (pq[i] == null)
				return false;
		}
		for (int i = size + 1; i < pq.length; i++) {
			if (pq[i] != null)
				return false;
		}
		if (pq[0] != null)
			return false;
		return isMinHeapOrdered(1);
	}

	// is subtree of pq[1..n] rooted at k a min heap?
	private boolean isMinHeapOrdered(int k) {
		if (k > size)
			return true;
		int left = 2 * k;
		int right = 2 * k + 1;
		if (left <= size && greater(k, left))
			return false;
		if (right <= size && greater(k, right))
			return false;
		return isMinHeapOrdered(left) && isMinHeapOrdered(right);
	}

	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Unit tests
	 *
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {
		String[] items = { "123", "1234567", "1234", "1", "12345", "123456",  "12", "12345678"};
		PriorityQueue2011<String> pq = new PriorityQueue2011<String>();
		for(String i : items) {
			System.out.println(i);
			pq.offer(i);
		}
		System.out.println(" ");
		for(int k = 0 ; k < items.length; k++) {
			System.out.println(pq.poll());
		}
	}

	@Override
	public Iterator<Key> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}