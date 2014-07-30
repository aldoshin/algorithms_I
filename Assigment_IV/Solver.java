/*************************************************************************
 * This program solves the 8 puzzle game using A* algorithm
 * 
 * @author aldperez
 * 
 *************************************************************************/
public class Solver {

	private SearchNode solutionNode;

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		MinPQ<SearchNode> pq = new MinPQ<>();
		pq.insert(new SearchNode(initial, null));

		MinPQ<SearchNode> pqTwin = new MinPQ<>();
		pqTwin.insert(new SearchNode(initial.twin(), null));

		SearchNode min = pq.delMin();
		SearchNode minTwin = pqTwin.delMin();

		while (!min.getBoard().isGoal() && !minTwin.getBoard().isGoal()) {
			updatePQ(pq, min);
			updatePQ(pqTwin, minTwin);

			min = pq.delMin();
			minTwin = pqTwin.delMin();
		}
		if (min.getBoard().isGoal()) {
			solutionNode = min;
		}
	}

	// is the initial board solvable?
	public boolean isSolvable() {
		return this.solutionNode != null;
	}

	private void updatePQ(MinPQ<SearchNode> pq, SearchNode min) {
		for (Board board : min.getBoard().neighbors()) {
			if (min.getPrevious() == null
					|| !board.equals(min.getPrevious().getBoard())) {
				pq.insert(new SearchNode(board, min));
			}
		}
	}

	public int moves() {
		return (this.solutionNode != null) ? this.solutionNode.getMoves() : -1;
	}

	// sequence of boards in a shortest solution; null if no solution
	public Iterable<Board> solution() {
		if (this.solutionNode != null) {
			Stack<Board> iter = new Stack<>();
			iter.push(this.solutionNode.getBoard());
			SearchNode sn = this.solutionNode.getPrevious();
			while (sn != null) {
				iter.push(sn.getBoard());
				sn = sn.getPrevious();
			}
			return iter;
		}
		return null;
	}

	// solve a slider puzzle (given below)
	public static void main(String[] args) {
		// create initial board from file
		In in = new In("C:\\Users\\aldperez\\Downloads\\8puzzle\\puzzle44.txt");
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}

	private class SearchNode implements Comparable<SearchNode> {
		private Board board;
		private int moves;
		private SearchNode previous;
		private int priority;

		public SearchNode(Board board, SearchNode previous) {
			this.board = board;
			this.moves = previous != null ? previous.getMoves() + 1 : 0;
			this.previous = previous;
			this.priority = this.board.manhattan() + this.moves;
		}

		@Override
		public int compareTo(SearchNode o) {
			if (!this.getBoard().equals(o.getBoard())) {
				int thatPriority = o.getPriority();
				if (priority > thatPriority)
					return 1;
				if (priority == thatPriority) {
					if (this.getBoard().manhattan() > o.getBoard().manhattan()) {
						return 1;
					} else if (this.getBoard().manhattan() == o.getBoard()
							.manhattan()) {
						return 0;
					} else if (this.getBoard().manhattan() < o.getBoard()
							.manhattan()) {
						return -1;
					}
					return 0;
				}
				if (priority < thatPriority)
					return -1;
			}
			return 0;
		}

		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append("priority = " + this.priority + "\n");
			s.append("moves = " + this.moves + "\n");
			s.append(board.toString());
			return s.toString();
		}

		public Board getBoard() {
			return board;
		}

		public SearchNode getPrevious() {
			return previous;
		}

		public int getMoves() {
			return moves;
		}

		public int getPriority() {
			return priority;
		}
	}
}