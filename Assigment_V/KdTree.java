import java.util.Comparator;

/*************************************************************************
 * Dependencies: StdDraw.java Point2D.java
 * 
 * Add the points that the user clicks in the standard draw window to a kd-tree
 * and draw the resulting kd-tree.
 * 
 *************************************************************************/
public class KdTree {

	private Node root;

	private static class Node {

		private Point2D p; // the point
		private RectHV rect; // the axis-aligned rectangle corresponding to this
								// node
		private Node lb; // the left/bottom subtree
		private Node rt; // the right/top subtree
		private int N; // number of nodes in subtree

		public Node(Point2D p2, RectHV r, int N) {
			this.p = p2;
			this.rect = r;
			this.N = N;
		}
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public int size() {
		return size(root);
	}

	// return number of key-value pairs in BST rooted at x
	private int size(Node x) {
		if (x == null)
			return 0;
		else
			return x.N;
	}

	public boolean contains(Point2D p) {
		return get(root, p, 0) != null;
	}

	private Point2D get(Node x, Point2D p, int level) {
		if (x == null)
			return null;
		if (!x.p.equals(p)) {
			Comparator<Point2D> comparator = getComparatorByLevel(level);
			int cmp = comparator.compare(p, x.p);
			if (cmp < 0)
				return get(x.lb, p, level + 1);
			else
				return get(x.rt, p, level + 1);
		} else {
			return x.p;
		}
	}

	public void insert(Point2D p) {
		root = put(root, p, 0, new RectHV(0, 0, 1, 1));
	}

	private Node put(Node x, Point2D p, int level, RectHV r) {
		if (x == null)
			return new Node(p, r, 1);
		Comparator<Point2D> comparator = getComparatorByLevel(level);
		Point2D nodePoint = x.p;
		int cmp = comparator.compare(p, nodePoint);
		int nextLevel = level + 1;
		if (cmp < 0)
			x.lb = put(x.lb, p, nextLevel,
					getLeftRectByLevel(x.lb, x.rect, nodePoint, nextLevel));
		else if (!nodePoint.equals(p))
			x.rt = put(x.rt, p, nextLevel,
					getRightRectByLevel(x.rt, x.rect, nodePoint, nextLevel));
		x.N = 1 + size(x.lb) + size(x.rt);
		return x;
	}

	private RectHV getLeftRectByLevel(Node next, RectHV rect, Point2D p,
			int level) {
		if (next == null) {
			if (level % 2 == 0) {
				return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
			} else {
				return new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
			}
		} else {
			return next.rect;
		}
	}

	private RectHV getRightRectByLevel(Node next, RectHV rect, Point2D p,
			int level) {
		if (next == null) {
			if (level % 2 == 0) {
				return new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
			} else {
				return new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
			}
		} else {
			return next.rect;
		}
	}

	private Comparator<Point2D> getComparatorByLevel(int level) {
		if (level % 2 == 0) {
			return Point2D.X_ORDER;
		} else {
			return Point2D.Y_ORDER;
		}
	}

	public void draw() {
		draw(root, 0);
	}

	private void draw(Node x, int level) {
		if (x != null) {
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(.01);
			x.p.draw();
			StdDraw.setPenRadius();
			if (level % 2 == 0) {
				StdDraw.setPenColor(StdDraw.RED);
				StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
			} else {
				StdDraw.setPenColor(StdDraw.BLUE);
				StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
			}
			draw(x.lb, level + 1);
			draw(x.rt, level + 1);
		}
	}

	private Point2D nearest(Node x, Point2D p, Point2D mp, boolean vert) {
		Point2D min = mp;

		if (x == null)
			return min;
		if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(min))
			min = x.p;

		// choose the side that contains the query point first
		if (vert) {
			if (x.p.x() < p.x()) {
				min = nearest(x.rt, p, min, !vert);
				if (x.lb != null
						&& (min.distanceSquaredTo(p) > x.lb.rect
								.distanceSquaredTo(p)))
					min = nearest(x.lb, p, min, !vert);
			} else {
				min = nearest(x.lb, p, min, !vert);
				if (x.rt != null
						&& (min.distanceSquaredTo(p) > x.rt.rect
								.distanceSquaredTo(p)))
					min = nearest(x.rt, p, min, !vert);
			}
		} else {
			if (x.p.y() < p.y()) {
				min = nearest(x.rt, p, min, !vert);
				if (x.lb != null
						&& (min.distanceSquaredTo(p) > x.lb.rect
								.distanceSquaredTo(p)))
					min = nearest(x.lb, p, min, !vert);
			} else {
				min = nearest(x.lb, p, min, !vert);
				if (x.rt != null
						&& (min.distanceSquaredTo(p) > x.rt.rect
								.distanceSquaredTo(p)))
					min = nearest(x.rt, p, min, !vert);
			}
		}
		return min;
	}

	// a nearest neighbor in the set to p: null if set is empty
	public Point2D nearest(Point2D p) {
		if (isEmpty())
			return null;
		return nearest(root, p, root.p, true);
	}

	public Iterable<Point2D> range(RectHV rect) {
		return range(root, rect, new Queue<Point2D>());
	}

	private Queue<Point2D> range(Node x, RectHV rect, Queue<Point2D> range) {
		if (x != null) {
			if (rect.intersects(x.rect)) {
				if (rect.contains(x.p)) {
					range.enqueue(x.p);
				}
				range(x.lb, rect, range);
				range(x.rt, rect, range);
			}
		}
		return range;
	}

	public static void main(String[] args) {

		String filename = "C:\\Users\\aldperez\\Downloads\\kdtree\\input10K.txt";
		In in = new In(filename);

		 StdDraw.show(0);

		// initialize the two data structures with point from standard input
		KdTree kdtree = new KdTree();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			// System.out.println(p);
			kdtree.insert(p);
			StdDraw.clear();
			kdtree.draw();
			StdDraw.show(0);
		}
		// In in2 = new In(filename);
		// while (!in2.isEmpty()) {
		// double x = in2.readDouble();
		// double y = in2.readDouble();
		// Point2D p = new Point2D(x, y);
		//
		// if (!kdtree.contains(p)) {
		// System.out.println(i + "::::::::::::::::::::::::" + p);
		// break;
		// }
		// i++;
		// }
		// Stopwatch st = new Stopwatch();
		// for (int j = 0; j < 1000000; j++) {
		// kdtree.nearest(new Point2D(StdRandom.uniform(0, 1), StdRandom
		// .uniform(0, 1)));
		// }
		// System.out.println(st.elapsedTime());
	}
}