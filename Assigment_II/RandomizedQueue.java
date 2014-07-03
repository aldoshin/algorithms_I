import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] a; // array of items
	private int N;

	// construct an empty randomized queue
	public RandomizedQueue() {
		a = (Item[]) new Object[2];
	}

	// is the queue empty?
	public boolean isEmpty() {
		return N == 0;
	}

	// return the number of items on the queue
	public int size() {
		return N;
	}

	// add the item
	public void enqueue(Item item) {
		validateItem(item);
		if (N == a.length)
			resize(2 * a.length); // double size of array if necessary
		a[N++] = item; // add item
	}

	// delete and return a random item
	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException("Stack underflow");
		int randomIndex = StdRandom.uniform(N);
		Item item = a[randomIndex];
		a[randomIndex] = a[N - 1];
		a[N - 1] = null;// to avoid loitering
		N--;
		// shrink size of array if necessary
		if (N > 0 && N == a.length / 4)
			resize(a.length / 2);
		return item;
	}

	// return (but do not delete) a random item
	public Item sample() {
		if (isEmpty())
			throw new NoSuchElementException("Stack underflow");
		return a[StdRandom.uniform(0, N)];
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new ArrayIterator();
	}

	// an iterator, doesn't implement remove() since it's optional
	private class ArrayIterator implements Iterator<Item> {
		private Item[] it;
		private int s;

		public ArrayIterator() {
			s = N;
			it = (Item[]) new Object[s];
			for (int i = 0; i < s; i++) {
				it[i] = a[i];
			}
			StdRandom.shuffle(it);
		}

		public boolean hasNext() {
			return s > 0;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return it[--s];
		}
	}

	private void validateItem(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
	}

	// resize the underlying array holding the elements
	private void resize(int capacity) {
		assert capacity >= N;
		Item[] temp = (Item[]) new Object[capacity];
		for (int i = 0; i < N; i++) {
			temp[i] = a[i];
		}
		a = temp;
	}

	public static void main(String[] args) {
		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		for (int i = 0; i < 10; i++) {
			rq.enqueue(i);
		}
		Iterator<Integer> i1 = rq.iterator();
		Iterator<Integer> i2 = rq.iterator();
		while (i1.hasNext() && i2.hasNext()) {
			StdOut.printf("%d    %d\n", i1.next(), i2.next());
		}
		for (Integer i : rq) {
			System.out.println(rq.dequeue());
		}
	}

}
