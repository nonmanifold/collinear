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
        ArrayList<Point> group = new ArrayList<>();
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

            group.clear();
            group.add(origin);

            for (int j = begin; j < points.length; j++) {
                Point p = points[j];
                double slope = origin.slopeTo(p);
                if (currentSlope == slope) {
                    group.add(p);
                    if (group.size() >= 4 && j == points.length - 1) {
                        registerGroup(group);
                    }
                } else {
                    if (group.size() >= 4) {
                        registerGroup(group);
                    }
                    group.clear();
                    group.add(origin);
                    group.add(p);
                    currentSlope = slope;
                }
            }
        }
    }

    private void registerGroup(ArrayList<Point> group) {
        group.sort(null);
        segments.add(new LineSegment(group.get(0), group.get(group.size() - 1)));
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