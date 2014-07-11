/*************************************************************************
 * Name: Aldo Perez
 * Email: aldoshin@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

	// compare points by slope
	public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
		@Override
		public int compare(Point o1, Point o2) {
			double slope1 = slopeTo(o1);
			double slope2 = slopeTo(o2);
			if (slope1 < slope2)
				return -1;
			else if (slope1 == slope2)
				return 0;
			else
				return 1;
		}
	};

	private final int x; // x coordinate
	private final int y; // y coordinate

	// create the point (x, y)
	public Point(int x, int y) {
		/* DO NOT MODIFY */
		this.x = x;
		this.y = y;
	}

	// plot this point to standard drawing
	public void draw() {
		/* DO NOT MODIFY */
		StdDraw.point(x, y);
	}

	// draw line between this point and that point to standard drawing
	public void drawTo(Point that) {
		/* DO NOT MODIFY */
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	// slope between this point and that point
	public double slopeTo(Point that) {
		/* (this.y == that.y && this.x < that.x) */
		Double dividend = Double.valueOf(that.y - this.y);
		Double divisor = Double.valueOf(that.x - this.x);

		if (this.compareTo(that) == 0)
			return Double.NEGATIVE_INFINITY;
		if (divisor == 0) {
			if (dividend == 0)
				return 0;
			else
				return Double.POSITIVE_INFINITY;
		} else {
			return dividend.floatValue() / divisor.floatValue();
		}
	}

	// is this point lexicographically smaller than that one?
	// comparing y-coordinates and breaking ties by x-coordinates
	public int compareTo(Point that) {
		if ((this.y < that.y) || (this.y == that.y && this.x < that.x))
			return -1;
		else if (this.y == that.y && this.x == that.x)
			return 0;
		else
			return 1;
	}

	// return string representation of this point
	public String toString() {
		/* DO NOT MODIFY */
		return "(" + x + ", " + y + ")";
	}

	// unit test
	public static void main(String[] args) {
		/* YOUR CODE HERE */
	}
}
