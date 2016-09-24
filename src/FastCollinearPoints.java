import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<ArrayList<Point>> groups;
    private ArrayList<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] inpoints) {
        if (inpoints == null) {
            throw new NullPointerException();
        }
        segments = new ArrayList<>();
        ArrayList<Point> group;
        groups = new ArrayList<ArrayList<Point>>();
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
        for (ArrayList<Point> g : groups) {
            segments.add(new LineSegment(g.get(0), g.get(g.size() - 1)));
        }
    }

    private void registerGroup(ArrayList<Point> group) {
        group.sort(null);
        for (ArrayList<Point> g : groups) {
            if (g.contains(group.get(0)) && g.contains(group.get(group.size() - 1))) {
                return;
            }
        }
        groups.add(group);
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