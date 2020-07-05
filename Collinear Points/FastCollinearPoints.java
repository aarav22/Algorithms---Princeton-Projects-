 
 
import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        checkException(points);
        ArrayList<LineSegment> storeSegments = new ArrayList<LineSegment>();
        Point[] copyPoints = Arrays.copyOf(points, points.length);
        for (Point point : points) {
            Arrays.sort(copyPoints, point.slopeOrder());
            double tmpSlope = point.slopeTo(copyPoints[0]);
            int count = 1;
            int i;
            for (i = 1; i < copyPoints.length; i++) {
                if (point.slopeTo(copyPoints[i]) == tmpSlope) {
                    count++;
                    continue;
                } else {
                    if (count >= 3) {
                        Arrays.sort(copyPoints, i - count, i);
                        if (point.compareTo(copyPoints[i - count]) < 0)
                            storeSegments.add(new LineSegment(point, copyPoints[i - 1]));
                        }
                    tmpSlope = point.slopeTo(copyPoints[i]);
                    count = 1;
                }
            }
            if (count >= 3) {
                Arrays.sort(copyPoints, i - count, i);
                if (point.compareTo(copyPoints[i - count]) < 0)
                    storeSegments.add(new LineSegment(point, copyPoints[i - 1]));
                                   }
        }
        lineSegments = storeSegments.toArray(new LineSegment[storeSegments.size()]);
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    private void checkException(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null)
                throw new java.lang.NullPointerException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null)
                    throw new java.lang.NullPointerException();
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
        }
    }
}
