import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Fast {

	public static void main(String[] args) {

		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.show(0);
		StdDraw.setPenRadius(0.01); // make the points a bit larger

		In in = new In("C:\\Users\\aldperez\\Downloads\\collinear\\input10000.txt");
		int N = in.readInt();
		Point[] points = new Point[N];

		for (int i = 0; i < N; i++) {
			Point p = new Point(in.readInt(), in.readInt());
			points[i] = p;
			p.draw();
		}

		if (N > 3) {
			checkAndDraw(points);
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
	private static void checkAndDraw(Point[] arr) {
		Arrays.sort(arr);

		for (int i = 0; i < arr.length; i++) {
			Arrays.sort(arr, arr[i].SLOPE_ORDER);

			int start = 1;
			int end = 2;
			List<Point> col = new ArrayList<>();
			col.add(arr[0]);
			col.add(arr[start]);
			double slopeToP = arr[0].slopeTo(arr[start]);

			while (end < arr.length) {
				if (slopeToP == arr[0].slopeTo(arr[end])) {
					col.add(arr[end]);
					end++;
				} else {
					if (col.size() > 3) {
						draw(col);
					}
					start = end;
					end += 1;

					col = new ArrayList<>();
					col.add(arr[0]);
					col.add(arr[start]);

					slopeToP = arr[0].slopeTo(arr[start]);
				}
			}
			if (col.size() > 3) {
				draw(col);
			}
			Arrays.sort(arr);
		}
	}

	private static void draw(List<Point> col) {
		Point least = col.get(0);
		Collections.sort(col);
		if (least == col.get(0)) {
			for (int s = 0; s < col.size(); s++) {
				StdOut.print(col.get(s));
				if (s < col.size() - 1)
					StdOut.print(" -> ");
			}
			StdOut.println();
			least.drawTo(col.get(col.size() - 1));
		}
	}
}