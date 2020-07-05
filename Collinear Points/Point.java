import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y)
            return Double.NEGATIVE_INFINITY;
        else if (this.x == that.x)
            return Double.POSITIVE_INFINITY;
        else if (this.y == that.y)
            return +0;
        else
            return (double) (that.y - this.y) / (that.x - this.x);
    }

    public int compareTo(Point that) {
        if (this.y < that.y)
            return -1;
        else if (this.y == that.y && this.x < that.x)
            return -1;
        else if (this.y == that.y && this.x == that.x)
            return  0;
        return 1;
    }

    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point po1, Point po2) {
                double slope1 = slopeTo(po1);
                double slope2 = slopeTo(po2);
                if (slope1 == slope2) return 0;
                if (slope1 < slope2) return -1;
                return 1;
            }
        };
    }


    public String toString() {
        return "(" + x + ", " + y + ")";
    }

        public static void main(String[] args) {
        Point p1 = new Point(1, -100);
        Point p2 = new Point(2000, 2000);
        p1.draw();
        p2.draw();
        p1.drawTo(p2);
        System.out.println(p1.compareTo(p2));
    }
}
