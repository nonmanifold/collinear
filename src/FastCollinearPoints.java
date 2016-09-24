import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<Point[]> groups;
    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] inpoints) {
        if (inpoints == null) {
            throw new NullPointerException();
        }

        ArrayList<Point> group;
        groups = new ArrayList<Point[]>();
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
        segments = new LineSegment[groups.size()];
        int i = 0;
        for (Point[] g : groups) {
            segments[i] = new LineSegment(g[0], g[g.length - 1]);
            i++;
        }
    }

    private void registerGroup(ArrayList<Point> group) {
        group.sort(null);
        for (Point[] g : groups) {
            if (Arrays.binarySearch(g, group.get(0)) > -1 && Arrays.binarySearch(g, group.get(group.size() - 1)) > -1) {
                return;
            }
        }
        groups.add(group.toArray(new Point[group.size()]));
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }
}