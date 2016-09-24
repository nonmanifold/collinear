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
        segments = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            Point origin = points[i];
            if (origin == null) {
                throw new IllegalArgumentException();
            }
            if (i + 1 > points.length - 1) {
                continue;
            }
            Arrays.sort(points, i + 1, points.length, origin.slopeOrder());
            double lastSlope = origin.slopeTo(points[i + 1]);
            if (lastSlope == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException();
            }
            int groupNum = 1;

            for (int j = i + 1; j < points.length; j++) {
                Point p = points[j];
                double slope = origin.slopeTo(p);
                if (lastSlope == slope) {
                    groupNum++;
                } else {
                    if (groupNum >= 3) {
                        // register group
                        segments.add(new LineSegment(origin, points[j - 1]));
                    }
                    groupNum = 1;
                    lastSlope = slope;
                }
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