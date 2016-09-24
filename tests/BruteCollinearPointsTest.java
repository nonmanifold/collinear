import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class BruteCollinearPointsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throwNPEOnNullPoints() throws Exception {
        //Throw a java.lang.NullPointerException if the argument to the constructor is null
        thrown.expect(NullPointerException.class);
        new BruteCollinearPoints(null);
    }

    @Test
    public void throwNPEOnInsertFirstNull() throws Exception {
        //Throw a java.lang.NullPointerException if any point in the array is null.
        thrown.expect(NullPointerException.class);
        new BruteCollinearPoints(new Point[]{new Point(0, 0), null});
    }

    @Test
    public void throwIllegalArgumentOnRepeatedInput() throws Exception {
        //Throw a java.lang.IllegalArgumentException if the argument to the constructor contains a repeated point.
        thrown.expect(IllegalArgumentException.class);
        new BruteCollinearPoints(new Point[]{new Point(0, 0), new Point(1, 0), new Point(0, 0)});
    }

    @Test
    public void testForCollinearDetection() {
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(new Point[]{
                new Point(0, 0),
                new Point(1, 1)}
        );
        assertEquals("no segments", 0, collinearPoints.numberOfSegments());
        assertArrayEquals(new LineSegment[]{}, collinearPoints.segments());

        Point[] points1 = new Point[]{
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
        };
        BruteCollinearPoints collinearPoints1 = new BruteCollinearPoints(points1);
        assertEquals("one segment", 1, collinearPoints1.numberOfSegments());
        LineSegment[] segments1 = collinearPoints1.segments();
        assertArrayEquals(new LineSegment[]{new LineSegment(new Point(0, 0), new Point(3, 3))}, segments1);

        Point[] points2 = new Point[]{
                new Point(10, 15),
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(1, 300),
                new Point(20, 3),
        };
        BruteCollinearPoints collinearPoints2 = new BruteCollinearPoints(points2);
        LineSegment[] segments2 = collinearPoints2.segments();
        assertArrayEquals(new LineSegment[]{new LineSegment(new Point(0, 0), new Point(3, 3))}, segments2);
        assertEquals("one segment", 1, collinearPoints2.numberOfSegments());
    }

    @Test
    public void testForCollinearDetection2() {

        Point[] points2 = new Point[]{
                new Point(10, 15),
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(1, 300),
                new Point(20, 3),
        };
        BruteCollinearPoints collinearPoints2 = new BruteCollinearPoints(points2);
        LineSegment[] segments2 = collinearPoints2.segments();
        assertArrayEquals(new LineSegment[]{new LineSegment(new Point(0, 0), new Point(3, 3))}, segments2);

    }

    @Test
    public void verticals() {

        Point[] points2 = new Point[]{
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3),
                new Point(0, 30),
                new Point(0, 15),
        };
        BruteCollinearPoints collinearPoints2 = new BruteCollinearPoints(points2);
        LineSegment[] segments2 = collinearPoints2.segments();
        assertArrayEquals(new LineSegment[]{new LineSegment(new Point(0, 0), new Point(0, 30))}, segments2);
    }
}