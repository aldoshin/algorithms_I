import java.util.TreeSet;

/*************************************************************************
 * Dependencies: StdDraw.java Point2D.java
 * 
 * Add the points that the user clicks in the standard draw window to a kd-tree
 * and draw the resulting kd-tree.
 * 
 *************************************************************************/
public class PointSET {

	private TreeSet<Point2D> bst;

	// construct an empty set of points
	public PointSET() {
		this.bst = new TreeSet<>();
	}

	// is the set empty?
	public boolean isEmpty() {
		return this.bst.isEmpty();
	}

	// number of points in the set
	public int size() {
		return this.bst.size();
	}

	// add the point p to the set (if it is not already in the set)
	public void insert(Point2D p) {
		this.bst.add(p);
	}

	// does the set contain the point p?
	public boolean contains(Point2D p) {
		return this.bst.contains(p);
	}

	// draw all of the points to standard draw
	public void draw() {
		for (Point2D bstPoint : this.bst.descendingSet()) {
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(.01);
			bstPoint.draw();
		}
	}

	// all points in the set that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		Queue<Point2D> range = new Queue<>();
		for (Point2D bstPoint : this.bst.descendingSet()) {
			if (rect.contains(bstPoint)) {
				range.enqueue(bstPoint);
			}
		}
		return range;
	}

	// a nearest neighbor in the set to p; null if set is empty
	public Point2D nearest(Point2D p) {
		if (isEmpty()) {
			return null;
		} else {
			Point2D result = null;
			Double distance = null;
			for (Point2D bstPoint : this.bst.descendingSet()) {
				double distanceToP = bstPoint.distanceTo(p);
				if (distance == null || distanceToP < distance) {
					result = bstPoint;
					distance = distanceToP;
				}
			}
			return result;
		}
	}
}