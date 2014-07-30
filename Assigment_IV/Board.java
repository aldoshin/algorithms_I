import java.util.ArrayList;
import java.util.List;

/*************************************************************************
 * This program creates a representation of a Board for the 8 puzzle game.
 * Contains the needed methods for Solver.java in order to solve the game using
 * the A* algorithm
 * 
 * @author aldperez
 * 
 *************************************************************************/
public class Board {
	private int N;
	private int[][] tiles;
	private int zeroRow;
	private int zeroColumn;
	private int manhattanVal;
	private int hammingVal;

	// construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		this.N = blocks.length;
		this.tiles = copyBoard(blocks);
		findZero();
	}

	public int dimension() {
		return N;
	}

	public int hamming() {
		if (this.hammingVal == 0) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					int elemtn = xyTo1D(i, j);
					if ((tiles[i][j] != 0) && tiles[i][j] != (elemtn + 1)) {
						this.hammingVal++;
					}
				}
			}
		}
		return this.hammingVal;
	}

	public int manhattan() {
		if (this.manhattanVal == 0) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					int elemtn = xyTo1D(i, j);
					if ((tiles[i][j] != 0) && tiles[i][j] != (elemtn + 1)) {
						int rightVal = tiles[i][j] - 1;
						int rightX = (rightVal / N);
						int rightY = (rightVal % N);
						manhattanVal += Math.abs(rightX - i)
								+ Math.abs(rightY - j);
					}
				}
			}
		}
		return this.manhattanVal;
	}

	// is this board the goal board?
	public boolean isGoal() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				int elemtn = xyTo1D(i, j);
				if ((tiles[i][j] != 0) && tiles[i][j] != (elemtn + 1)) {
					return false;
				}
			}
		}
		return true;
	}

	// a board obtained by exchanging two adjacent blocks in the same row
	public Board twin() {
		int[][] copy = copyBoard(this.tiles);
		int changableRow = 0;
		if (this.zeroRow == 0) {
			changableRow = this.zeroRow + 1;
		}
		int switchd = copy[changableRow][0];
		copy[changableRow][0] = copy[changableRow][1];
		copy[changableRow][1] = switchd;
		return new Board(copy);
	}

	// does this board equal y?
	public boolean equals(Object x) {
		if (x == this)
			return true;
		if (x == null)
			return false;
		if (x.getClass() != this.getClass())
			return false;
		return (this.toString().equals(x.toString()));
	}

	public Iterable<Board> neighbors() {
		Queue<Board> n = new Queue<>();
		List<Integer> changableRows = new ArrayList<>();
		List<Integer> changableColumns = new ArrayList<>();

		if (this.zeroRow != 0) {
			changableRows.add(this.zeroRow - 1);
		}
		if (this.zeroRow != (N - 1)) {
			changableRows.add(this.zeroRow + 1);
		}
		if (this.zeroColumn != 0) {
			changableColumns.add(zeroColumn - 1);
		}
		if (this.zeroColumn != (N - 1)) {
			changableColumns.add(zeroColumn + 1);
		}
		for (Integer row : changableRows) {
			int[][] copy = copyBoard(this.tiles);
			int switched = copy[row][this.zeroColumn];
			copy[row][this.zeroColumn] = copy[this.zeroRow][this.zeroColumn];
			copy[this.zeroRow][this.zeroColumn] = switched;
			n.enqueue(new Board(copy));
		}
		for (Integer col : changableColumns) {
			int[][] copy = copyBoard(this.tiles);
			int switched = copy[this.zeroRow][col];
			copy[this.zeroRow][col] = copy[this.zeroRow][this.zeroColumn];
			copy[this.zeroRow][this.zeroColumn] = switched;
			n.enqueue(new Board(copy));
		}
		return n;
	}

	// string representation of the board (in the output format specified
	// below)
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", tiles[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	private int[][] copyBoard(int[][] board) {
		int[][] copy = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				copy[i][j] = board[i][j];
			}
		}
		return copy;
	}

	private int xyTo1D(int i, int j) {
		return j + (this.N * i);
	}

	private void findZero() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (tiles[i][j] == 0) {
					this.zeroRow = i;
					this.zeroColumn = j;
				}
			}
		}
	}
}