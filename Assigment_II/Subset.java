public class Subset {
	public static void main(String[] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<>();
		int k = Integer.valueOf(args[0]);
		for (int i = 0; i < k; i++) {
			if (!StdIn.isEmpty()) {
				rq.enqueue(StdIn.readString());
			}
		}
		for (String s : rq)
			StdOut.print(rq.dequeue());
	}
}
