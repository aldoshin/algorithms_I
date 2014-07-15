import java.util.Arrays;

/*************************************************************************
 * Name: Aldo Perez Email: aldoshin@gmail.com
 * 
 * Compilation: javac Brute.java Execution: Dependencies: StdDraw.java
 * 
 * Description: An immutable data type for points in the plane.
 * 
 *************************************************************************/

public class Brute {

	public static void main(String[] args) {

		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.show(0);
		StdDraw.setPenRadius(0.01); // make the points a bit larger

		String filename = args[0];
        In in = new In(filename);
		int N = in.readInt();
		Point[] points = new Point[N];

		for (int i = 0; i < N; i++) {
			Point p = new Point(in.readInt(), in.readInt());
			points[i] = p;
			p.draw();
		}

		checkAndDraw(points);
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
		for (int i = 0; i < arr.length - 3; i += 1) {
			for (int j = i + 1; j < arr.length - 2; j += 1) {
				for (int k = j + 1; k < arr.length - 1; k += 1) {
					for (int l = k + 1; l < arr.length; l += 1) {
						// check if these 4 point have the same slope
						if ((arr[i].slopeTo(arr[j]) == arr[i].slopeTo(arr[k]))
								&& (arr[i].slopeTo(arr[j]) == arr[i]
										.slopeTo(arr[l]))) {
							Point[] temp = new Point[] { arr[i], arr[j],
									arr[k], arr[l] };
							Arrays.sort(temp);
							for (int s = 0; s < 4; s++) {
								StdOut.print(temp[s]);
								if (s < 3)
									StdOut.print(" -> ");
							}
							StdOut.println();
							temp[0].drawTo(temp[3]);
						}
					}
				}
			}

		}
	}

}