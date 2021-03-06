
import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PriorityQueue2011<Key extends Comparable<Key>> extends AbstractQueue<Key> {
	private int PQ_SIZE = 10; // maximum number of elements on PQ
	private int size; // number of elements on PQ
	private int[] pq; // binary heap using 1-based indexing
	private int[] qp; // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
	private Key[] keys; // keys[i] = priority of i

	/**
	 * Initializes an empty indexed priority queue with indices between {@code 0}
	 * and {@code maxN - 1}.
	 * 
	 * @param maxN the keys on this priority queue are index from {@code 0}
	 *             {@code maxN - 1}
	 * @throws IllegalArgumentException if {@code maxN < 0}
	 */
	public PriorityQueue2011() {
		size = 0;
		keys = (Key[]) new Comparable[PQ_SIZE + 1]; // make this of length maxN??
		pq = new int[PQ_SIZE + 1];
		qp = new int[PQ_SIZE + 1]; // make this of length maxN??
		for (int i = 0; i <= PQ_SIZE; i++)
			qp[i] = -1;
	}

	/**
	 * Returns the number of keys on this priority queue.
	 *
	 * @return the number of keys on this priority queue
	 */
	public int size() {
		return size;
	}

	/**
	 * Inserts the specified element into this priority queue.
	 *
	 * @param key the key to be added to PQ
	 */
	@Override
	public boolean offer(Key key) {
		//TODO- investigate if we really need unused qp[] cells to be -1
		size++;
		if (size >= PQ_SIZE) {
			PQ_SIZE = PQ_SIZE * 2;
			
			keys = Arrays.copyOf(keys, PQ_SIZE);
			pq = Arrays.copyOf(pq, PQ_SIZE);
			qp = Arrays.copyOf(qp, PQ_SIZE);
		}
		
		
		int i = size - 1;
		qp[i] = size;
		pq[size] = i;
		keys[i] = key;
		swim(size);
		return true;
	}

	/**
	 * Inserts the specified element into this priority queue.
	 *
	 * @param key the key to be added to PQ
	 */
	@Override
	public boolean add(Key key) {
		return offer(key);
	}

	/**
	 * Returns an index associated with a minimum key.
	 *
	 * @return an index associated with a minimum key
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	public int minIndex() {
		if (size == 0)
			throw new NoSuchElementException("Priority queue underflow");
		return pq[1];
	}

	/**
	 * Returns a minimum key.
	 *
	 * @return a minimum key
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	public Key minKey() {
		if (size == 0)
			throw new NoSuchElementException("Priority queue underflow");
		return keys[pq[1]];
	}

	@Override
	public Key peek() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Removes a minimum key and returns its associated index.
	 * 
	 * @return the minimum key to be removed
	 */
	@Override
	public Key poll() {
		if (size == 0)
			return null;
		int min = pq[1];
		exch(1, size--);
		sink(1);
		assert min == pq[size + 1];
		qp[min] = -1; // delete
		return keys[min];
	}
	
	/**
	 * Removes a single instance of the specified element from this queue, if it is present.
	 * 
	 * @return true if removal was successful
	 * @return false if key does not exist
	 */
	public boolean remove(Key key) {
		//TODO
		return false;
	}


	/**
	 * Returns the key associated with index {@code i}.
	 *
	 * @param i the index of the key to return
	 * @return the key associated with index {@code i}
	 * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
	 * @throws NoSuchElementException   no key is associated with index {@code i}
	 */
	public Key keyOf(int i) {
		validateIndex(i);
		if (!contains(i))
			throw new NoSuchElementException("index is not in the priority queue");
		else
			return keys[i];
	}

	/**
	 * Change the key associated with index {@code i} to the specified value.
	 *
	 * @param i   the index of the key to change
	 * @param key change the key associated with index {@code i} to this key
	 * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
	 * @throws NoSuchElementException   no key is associated with index {@code i}
	 */
	public void changeKey(int i, Key key) {
		validateIndex(i);
		if (!contains(i))
			throw new NoSuchElementException("index is not in the priority queue");
		keys[i] = key;
		swim(qp[i]);
		sink(qp[i]);
	}

	/**
	 * Decrease the key associated with index {@code i} to the specified value.
	 *
	 * @param i   the index of the key to decrease
	 * @param key decrease the key associated with index {@code i} to this key
	 * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
	 * @throws IllegalArgumentException if {@code key >= keyOf(i)}
	 * @throws NoSuchElementException   no key is associated with index {@code i}
	 */
	public void decreaseKey(int i, Key key) {
		validateIndex(i);
		if (!contains(i))
			throw new NoSuchElementException("index is not in the priority queue");
		if (keys[i].compareTo(key) == 0)
			throw new IllegalArgumentException(
					"Calling decreaseKey() with a key equal to the key in the priority queue");
		if (keys[i].compareTo(key) < 0)
			throw new IllegalArgumentException(
					"Calling decreaseKey() with a key strictly greater than the key in the priority queue");
		keys[i] = key;
		swim(qp[i]);
	}

	/**
	 * Increase the key associated with index {@code i} to the specified value.
	 *
	 * @param i   the index of the key to increase
	 * @param key increase the key associated with index {@code i} to this key
	 * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
	 * @throws IllegalArgumentException if {@code key <= keyOf(i)}
	 * @throws NoSuchElementException   no key is associated with index {@code i}
	 */
	public void increaseKey(int i, Key key) {
		validateIndex(i);
		if (!contains(i))
			throw new NoSuchElementException("index is not in the priority queue");
		if (keys[i].compareTo(key) == 0)
			throw new IllegalArgumentException(
					"Calling increaseKey() with a key equal to the key in the priority queue");
		if (keys[i].compareTo(key) > 0)
			throw new IllegalArgumentException(
					"Calling increaseKey() with a key strictly less than the key in the priority queue");
		keys[i] = key;
		sink(qp[i]);
	}

	/**
	 * Remove the key associated with index {@code i}.
	 *
	 * @param i the index of the key to remove
	 * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
	 * @throws NoSuchElementException   no key is associated with index {@code i}
	 */
	public void delete(int i) {
		validateIndex(i);
		if (!contains(i))
			throw new NoSuchElementException("index is not in the priority queue");
		int index = qp[i];
		exch(index, size--);
		swim(index);
		sink(index);
		keys[i] = null;
		qp[i] = -1;
	}

	@Override
	public Key remove() {
		// TODO Auto-generated method stub
		return null;
	}

	// throw an IllegalArgumentException if i is an invalid index
	private void validateIndex(int i) {
		if (i < 0)
			throw new IllegalArgumentException("index is negative: " + i);
		if (i >= PQ_SIZE)
			throw new IllegalArgumentException("index >= capacity: " + i);
	}

	/***************************************************************************
	 * General helper functions.
	 ***************************************************************************/
	private boolean greater(int i, int j) {
		return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
	}

	private void exch(int i, int j) {
		int swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
		qp[pq[i]] = i;
		qp[pq[j]] = j;
	}
	

	/***************************************************************************
	 * Heap helper functions.
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

	/**
	 * Unit tests the {@code IndexMinPQ} data type.
	 *
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {
		// insert a bunch of strings
		String[] strings = { "it", "was", "the", "best", "of", "times", "it", "was", "the", "worst"};

		PriorityQueue2011<String> PQ = new PriorityQueue2011<String>();
		for (int i = 0; i < strings.length; i++) {
			PQ.offer(strings[i]);
		}

		// delete and print each key
		while (!PQ.isEmpty()) {
			int i = PQ.minIndex();
			System.out.println(i + " " +PQ.poll());
		}
		System.out.println();

		// re-insert the same strings
		for (int i = 0; i < strings.length; i++) {
			PQ.offer(strings[i]);
		}

		// print each key using the iterator
		for (int i = 0; i < strings.length; i++) {
			System.out.println(i + " " + strings[i]);
		}
		while (!PQ.isEmpty()) {
			PQ.poll();
		}

	}

	@Override
	public Iterator<Key> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}