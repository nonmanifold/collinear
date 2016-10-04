import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> groups;
    private int lastGroupSize = -1;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] inpoints) {
        if (inpoints == null) {
            throw new NullPointerException();
        }

        ArrayList<Point> group;
        groups = new ArrayList<>();

        Point[] points = inpoints.clone();
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

            group = new ArrayList<>();
            group.add(origin);
            group.add(points[begin]);

            for (int j = begin + 1; j < points.length; j++) {
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
                    group = new ArrayList<>();
                    group.add(origin);
                    group.add(p);
                    currentSlope = slope;
                }
            }
        }
    }

    private void registerGroup(ArrayList<Point> group) {
        if (lastGroupSize == group.size()) {
            lastGroupSize = group.size() - 1;
            return;
        }
        lastGroupSize = group.size() - 1;

        group.sort(null);
        groups.add(new LineSegment(group.get(0), group.get(group.size() - 1)));
    }

    // the number of line segments
    public int numberOfSegments() {
        return groups.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return groups.toArray(new LineSegment[groups.size()]);
    }
}