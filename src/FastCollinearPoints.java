import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        Point[] tempPoints = new Point[points.length - 1];
        segments = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            if (p == null) {
                throw new IllegalArgumentException();
            }

            for (int j = 0, k = 0; j < points.length; j++) {
                if (j != i) {
                    tempPoints[k] = points[j];
                    k++;
                }
            }
            processPointAt(p, tempPoints);
        }
    }

    private void processPointAt(Point origin, Point[] points) {
        Arrays.sort(points, origin.slopeOrder());
        double lastSlope = Double.NEGATIVE_INFINITY;
        int groupNum = 0;
        for (int j = 0; j < points.length; j++) {
            Point p = points[j];
            double slope = origin.slopeTo(p);
            if (slope == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException();
            }
            if (lastSlope == slope) {
                groupNum++;
            } else {
                if (groupNum >= 3) {
                    // register group
                    segments.add(new LineSegment(origin, points[j - 1]));
                }
                groupNum = 0;
                lastSlope = slope;
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
}