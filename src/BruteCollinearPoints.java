import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        segments = new ArrayList<>();
        Point[] group = new Point[4];
        for (int p1 = 0; p1 < points.length; p1++) {
            Point pp1 = points[p1];
            for (int p2 = p1 + 1; p2 < points.length; p2++) {
                Point pp2 = points[p2];
                double slope12 = pp1.slopeTo(pp2);
                if (slope12 == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
                for (int p3 = p2 + 1; p3 < points.length; p3++) {
                    Point pp3 = points[p3];
                    double slope13 = pp1.slopeTo(pp3);
                    if (slope13 == Double.NEGATIVE_INFINITY) {
                        throw new IllegalArgumentException();
                    }
                    for (int p4 = p3 + 1; p4 < points.length; p4++) {
                        Point pp4 = points[p4];
                        double slope14 = pp1.slopeTo(pp4);
                        if (slope14 == Double.NEGATIVE_INFINITY) {
                            throw new IllegalArgumentException();
                        }
                        if (slope12 == slope13 && slope12 == slope14) {
                            group[0] = pp1;
                            group[1] = pp2;
                            group[2] = pp3;
                            group[3] = pp4;
                            Arrays.sort(group);
                            segments.add(new LineSegment(group[0], group[3]));
                        }
                    }
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