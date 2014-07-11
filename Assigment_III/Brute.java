import java.util.Arrays;

public class Brute {

	public static void main(String[] args) {

		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.show(0);
		StdDraw.setPenRadius(0.01); // make the points a bit larger

		In in = new In("C:\\Users\\aldperez\\Downloads\\collinear\\input6.txt");
		int N = in.readInt();
		Point[] points = new Point[N];

		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
//			if (i > 0 && (i % 4) == 0) {
//				checkAndDraw(points);
//			}
			points[i] = new Point(x, y);
			Point p = points[i];
			System.out.println(p);
		}

		// display to screen all at once
		StdDraw.show(0);

		// reset the pen radius
		StdDraw.setPenRadius();
	}

	/**
	 * p, q, r, and s are collinear,check whether the slopes between p and
	 * q,between p and r, and between p and s are all equal.
	 * 
	 * @param points
	 */
	private static void checkAndDraw(Point[] points) {
		int i = 0;
		int j = 1;
		int k = 2;
		int l = 3;

		if ((points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]))
				&& (points[i].slopeTo(points[j]) == points[i]
						.slopeTo(points[l]))) {
			Arrays.sort(points);
			for (int s = 0; s < points.length; s++) {
				StdOut.print(points[s]);
				if (s < 3)
					StdOut.print(" -> ");
			}
			StdOut.println();
			points[0].drawTo(points[3]);
		}
	}

}