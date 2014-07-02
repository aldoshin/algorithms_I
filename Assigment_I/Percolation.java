public class Percolation {

	private static final int OPEN_STATUS = 1;
	private int virtualTop = 0;
	private int N;

	private int[][] grid;
	private boolean[] treeHasBottom;
	private WeightedQuickUnionUF unionFind;

	public Percolation(int N) {
		if (N <= 0) {
			throw new IllegalArgumentException();
		}
		this.N = N;
		this.treeHasBottom = new boolean[(N * N) + 1];
		this.grid = new int[this.N][this.N];
		this.unionFind = new WeightedQuickUnionUF((N * N) + 1);
	}

	// open site (row i, column j) if it is not already
	public void open(int i, int j) {
		validateParms(i, j);
		this.grid[i - 1][j - 1] = OPEN_STATUS;
		int indexToOpen = xyTo1D(i, j);
		if (i == 1) {
			unionFind.union(virtualTop, indexToOpen);
		}
		if (i == N) {
			this.treeHasBottom[indexToOpen] = true;
		}
		if (i < this.N) {
			if (isOpen(i + 1, j)) {
				unionFind.union(xyTo1D(i + 1, j), indexToOpen);
				percolatesUnion(indexToOpen, xyTo1D(i + 1, j));
			}
		}
		if (i > 1) {
			if (isOpen(i - 1, j)) {
				unionFind.union(xyTo1D(i - 1, j), indexToOpen);
				percolatesUnion(indexToOpen, xyTo1D(i - 1, j));
			}
		}
		if (i == 1 && i == N) {
			percolatesUnion(virtualTop, indexToOpen);
		}
		if (j > 1 && isOpen(i, j - 1)) {
			unionFind.union(xyTo1D(i, j - 1), indexToOpen);
		}
		if (j < this.N && isOpen(i, j + 1)) {
			unionFind.union(xyTo1D(i, j + 1), indexToOpen);
		}
	}

	private void percolatesUnion(int indexToOpen, int indexUnion) {
		int treIndexToOpen = unionFind.find(indexToOpen);
		int treeIndexUnion = unionFind.find(indexUnion);
		if (this.treeHasBottom[indexToOpen]
				|| this.treeHasBottom[treIndexToOpen]) {
			this.treeHasBottom[treeIndexUnion] = true;
			this.treeHasBottom[indexUnion] = true;
		}
		if (this.treeHasBottom[indexUnion]
				|| this.treeHasBottom[treeIndexUnion]) {
			this.treeHasBottom[treIndexToOpen] = true;
			this.treeHasBottom[indexToOpen] = true;
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
		return this.treeHasBottom[unionFind.find(virtualTop)];
	}

	private int xyTo1D(int i, int j) {
		return ((j - 1) + (this.N * (i - 1))) + 1;
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