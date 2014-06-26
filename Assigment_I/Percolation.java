public class Percolation {

	private static final int OPEN_STATUS = 1;
	private int virtualTop = 0;
	private int virtualBottom;
	private int N;

	private int[][] grid;
	private WeightedQuickUnionUF unionFind;

	public Percolation(int N) {
		if (N <= 0) {
			throw new IllegalArgumentException();
		}
		this.N = N;
		this.virtualBottom = (N * N) + 2;
		this.grid = new int[this.N][this.N];
		this.unionFind = new WeightedQuickUnionUF(this.virtualBottom);
	}

	// open site (row i, column j) if it is not already
	public void open(int i, int j) {
		validateParms(i, j);
		this.grid[i - 1][j - 1] = OPEN_STATUS;
		int indexToOpen = xyTo1D(i, j);
		if (i > 1) {
			if (isOpen(i - 1, j)) {
				unionFind.union(xyTo1D(i - 1, j), indexToOpen);
			}
		} else {
			unionFind.union(virtualTop, indexToOpen);
		}
		if (i < this.N) {
			if (isOpen(i + 1, j)) {
				unionFind.union(xyTo1D(i + 1, j), indexToOpen);
			}
		} else {
			unionFind.union(virtualBottom - 1, indexToOpen);
		}
		if (j > 1 && isOpen(i, j - 1)) {
			unionFind.union(xyTo1D(i, j - 1), indexToOpen);
		}
		if (j < this.N && isOpen(i, j + 1)) {
			unionFind.union(xyTo1D(i, j + 1), indexToOpen);
		}
	}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		validateParms(i, j);
		return this.grid[i - 1][j - 1] == OPEN_STATUS;
	}

	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		validateParms(i, j);
		return isOpen(i, j) && unionFind.connected(virtualTop, xyTo1D(i, j));
	}

	// does the system percolate?
	public boolean percolates() {
		return unionFind.connected(virtualTop, virtualBottom - 1);
	}

	private int xyTo1D(int i, int j) {
		return ((j - 1) + (this.N * (i - 1)));
	}

	private void validateParms(int i, int j) {
		validateParm(i);
		validateParm(j);
	}

	private void validateParm(int i) {
		if (i < 1 && i > N) {
			throw new java.lang.IndexOutOfBoundsException();
		}
	}
}