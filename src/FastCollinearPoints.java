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
            int begin = i + 1;
            if (begin > points.length - 1) {
                continue;
            }
            Arrays.sort(points, begin, points.length, origin.slopeOrder());
            double currentSlope = origin.slopeTo(points[begin]);
            if (currentSlope == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException();
            }
            int seenEqualSlopes = 0;

            for (int j = begin; j < points.length; j++) {
                Point p = points[j];
                double slope = origin.slopeTo(p);
                if (currentSlope == slope) {
                    seenEqualSlopes++;
                    if (seenEqualSlopes >= 3 && j == points.length - 1) {
                        // register group
                        segments.add(new LineSegment(origin, points[j]));
                    }
                } else {
                    if (seenEqualSlopes >= 3) {
                        // register group
                        segments.add(new LineSegment(origin, points[j - 1]));
                    }
                    seenEqualSlopes = 1;
                    currentSlope = slope;
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