public class PercolationStats {

	private static final double Z95 = 1.96;
	private double[] results;

	// perform T independent computational experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) {
			throw new IllegalArgumentException();
		}
		this.results = new double[T];

		for (int k = 0; k < T; k++) {
			int i, j, opened = 0;
			Percolation perc = new Percolation(N);
			while (!perc.percolates()) {
				do {
					i = StdRandom.uniform(1, N + 1);
					j = StdRandom.uniform(1, N + 1);
				} while (perc.isOpen(i, j));
				perc.open(i, j);
				opened++;
			}
			double threshold = ((double) opened) / (N * N);
			results[k] = threshold;
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(results);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		if (results.length == 1) {
			return Double.NaN;
		}
		return StdStats.stddev(results);
	}

	// returns lower bound of the 95% confidence interval
	public double confidenceLo() {
		return ci(-1);
	}

	// returns upper bound of the 95% confidence interval
	public double confidenceHi() {
		return ci(1);
	}

	private double ci(int side) {
		return mean() + side * (Z95 * stddev()) / Math.sqrt(results.length);
	}

	// test client, described below
	public static void main(String[] args) {
		PercolationStats p = new PercolationStats(200, 100);
		System.out.println("mean                    = " + p.mean());
		System.out.println("stddev                  = " + p.stddev());
		System.out.println("95% confidence interval = " + p.confidenceLo()
				+ ", " + p.confidenceHi());
	}
}