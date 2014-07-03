public class Subset {
	public static void main(String[] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<>();
		int k = Integer.valueOf(args[0]);
		if (!StdIn.isEmpty()) {
			String[] input = StdIn.readAllStrings();
			StdRandom.shuffle(input);
			for (int i = 0; i < k; i++) {
				rq.enqueue(input[i]);
			}
			for (int i = 0; i < k; i++) {
				System.out.println(rq.dequeue());
			}
		}
	}
}
