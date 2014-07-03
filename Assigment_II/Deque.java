import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private int N; // number of elements on deck
	private Node<Item> first; // beginning of queue
	private Node<Item> last; // end of queue

	// helper linked list class
	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
		private Node<Item> prev;
	}

	// construct an empty deque
	public Deque() {
	}

	// is the deque empty?
	public boolean isEmpty() {
		return first == null;
	}

	// return the number of items on the deque
	public int size() {
		return N;
	}

	// insert the item at the front
	public void addFirst(Item item) {
		validateItem(item);
		if (isEmpty()) {
			first = new Node<Item>();
			first.item = item;
			last = first;
		} else {
			Node<Item> oldfirst = first;
			first = new Node<Item>();
			first.item = item;
			first.prev = oldfirst;
			oldfirst.next = first;
		}
		N++;
	}

	// insert the item at the end
	public void addLast(Item item) {
		validateItem(item);

		if (isEmpty()) {
			last = new Node<Item>();
			last.item = item;
			first = last;
		} else {
			Node<Item> oldlast = last;
			last = new Node<Item>();
			last.item = item;
			oldlast.prev = last;
			last.next = oldlast;
		}
		N++;
	}

	// delete and return the item at the front
	public Item removeFirst() {
		validateDeque();
		Item item = first.item; // save item to return
		first = first.prev; // delete first node
		N--;
		if (isEmpty())
			last = null;
		else
			first.next = null;
		return item;
	}

	// delete and return the item at the end
	public Item removeLast() {
		validateDeque();
		Item item = last.item;
		last = last.next;
		N--;
		if (N == 0)
			first = null; // to avoid loitering
		else
			last.prev = null;

		return item;
	}

	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return new ListIterator();
	}

	private void validateItem(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
	}

	private void validateDeque() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
	}

	private class ListIterator implements Iterator<Item> {

		private Node<Item> current;

		public ListIterator() {
			current = first;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			Item item = current.item;
			current = current.prev;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	// unit testing
	public static void main(String[] args) {
		Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addLast(10);
        deque.addFirst(6);
        deque.addFirst(7);
        deque.addLast(1);
        deque.addFirst(2);
        deque.addFirst(3);
        Iterator<Integer> i1 = deque.iterator();
        Iterator<Integer> i2 = deque.iterator();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        while (i1.hasNext() && i2.hasNext()) {
            StdOut.printf("%d    %d\n", i1.next(), i2.next());
        }
	}

}
