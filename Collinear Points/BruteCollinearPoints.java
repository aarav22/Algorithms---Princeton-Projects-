import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
         checkException(points);
        ArrayList<LineSegment> storeSegments = new ArrayList<LineSegment>();
        Arrays.sort(points);
        int n = points.length;
        for (int i = 0; i < n-3; i++)
            for (int j = i + 1; j < n-2; j++)
                for (int k = j + 1; k < n-1; k++) {
                    if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k]))
                        continue;
                    for (int l = k + 1; l < n; l++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                                points[i].slopeTo(points[j]) == points[i].slopeTo(points[l]))
                            storeSegments.add(new LineSegment(points[i], points[l]));
                    }
                }
        lineSegments = storeSegments.toArray(new LineSegment[storeSegments.size()]);    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
}

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.clone();
    }

     private void checkException(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
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
