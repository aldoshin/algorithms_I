import java.util.ArrayList;
import java.util.Arrays;
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
	private int[] tiles;
	private int zeroRow;
	private int zeroColumn;
	private int manhattanVal;
	private int hammingVal;

	// construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		this.N = blocks.length;
		this.tiles = copy2DBoardTo1D(blocks);
		findZero();
	}

	public int dimension() {
		return N;
	}

	public int hamming() {
		if (this.hammingVal == 0) {
			for (int i = 0; i < tiles.length; i++) {
				if ((tiles[i] != 0) && tiles[i] != (i + 1)) {
					this.hammingVal++;
				}
			}
		}
		return this.hammingVal;
	}

	public int manhattan() {
		if (this.manhattanVal == 0) {
			for (int i = 0; i < tiles.length; i++) {
				int current = tiles[i];
				if ((current != 0)) {
					int rightX = (i / N);
					int rightY = (i % N);
					manhattanVal += Math.abs(rightX - ((current - 1) / N))
							+ Math.abs(rightY - ((current - 1) % N));
				}
			}
		}
		return this.manhattanVal;
	}

	// is this board the goal board?
	public boolean isGoal() {
		return hamming() == 0;
	}

	// a board obtained by exchanging two adjacent blocks in the same row
	public Board twin() {
		int[][] copy = copy1DCharTo2DArray(this.tiles);
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
		Board that = (Board) x;
		return Arrays.equals(this.tiles, that.tiles);
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
			int[][] copy = copy1DCharTo2DArray(this.tiles);
			int switched = copy[row][this.zeroColumn];
			copy[row][this.zeroColumn] = copy[this.zeroRow][this.zeroColumn];
			copy[this.zeroRow][this.zeroColumn] = switched;
			n.enqueue(new Board(copy));
		}
		for (Integer col : changableColumns) {
			int[][] copy = copy1DCharTo2DArray(this.tiles);
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
		for (int i = 0; i < tiles.length; i++) {
			s.append(String.format("%2d ", tiles[i]));
			if ((i + 1) % N == 0)
				s.append("\n");
		}
		return s.toString();
	}

	private int[] copy2DBoardTo1D(int[][] board) {
		int[] copy = new int[N * N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				copy[xyTo1D(i, j)] = board[i][j];
			}
		}
		return copy;
	}

	private int[][] copy1DCharTo2DArray(int[] src) {
		int length = src.length;
		int[][] tgt = new int[N][N];
		for (int i = 0; i < length; i++) {
			tgt[i / N][i % N] = src[i];
		}
		return tgt;
	}

	private int xyTo1D(int i, int j) {
		return j + (this.N * i);
	}

	private void findZero() {
		for (int i = 0; i < tiles.length; i++) {
			if (tiles[i] == 0) {
				this.zeroRow = (i / N);
				this.zeroColumn = (i % N);
			}
		}
	}
}