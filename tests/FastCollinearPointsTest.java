import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class FastCollinearPointsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throwNPEOnNullPoints() throws Exception {
        //Throw a java.lang.NullPointerException if the argument to the constructor is null
        thrown.expect(NullPointerException.class);
        new FastCollinearPoints(null);
    }

    @Test
    public void throwNPEOnInsertFirstNull() throws Exception {
        //Throw a java.lang.NullPointerException if any point in the array is null.
        thrown.expect(NullPointerException.class);
        new FastCollinearPoints(new Point[]{new Point(0, 0), null});
    }

    @Test
    public void throwIllegalArgumentOnRepeatedInput() throws Exception {
        //Throw a java.lang.IllegalArgumentException if the argument to the constructor contains a repeated point.
        thrown.expect(IllegalArgumentException.class);
        new FastCollinearPoints(new Point[]{new Point(0, 0), new Point(1, 0), new Point(0, 0)});
    }

    @Test
    public void testForCollinearDetection() {
        FastCollinearPoints collinearPoints = new FastCollinearPoints(new Point[]{
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
        FastCollinearPoints collinearPoints1 = new FastCollinearPoints(points1);
        LineSegment[] segments1 = collinearPoints1.segments();
        assertArrayEquals(new LineSegment[]{new LineSegment(new Point(0, 0), new Point(3, 3))}, segments1);
        assertEquals("one segment", 1, collinearPoints1.numberOfSegments());

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
        FastCollinearPoints collinearPoints2 = new FastCollinearPoints(points2);
        LineSegment[] segments2 = collinearPoints2.segments();
        assertArrayEquals(new LineSegment[]{new LineSegment(new Point(0, 0), new Point(3, 3))}, segments2);
        assertEquals("one segment", 1, collinearPoints2.numberOfSegments());
    }

    @Test
    public void input6() {
        Point[] points2 = new Point[]{
                new Point(19000, 10000),
                new Point(18000, 10000),
                new Point(32000, 10000),
                new Point(21000, 10000),
                new Point(1234, 5678),
                new Point(14000, 10000),
        };
        FastCollinearPoints collinearPoints2 = new FastCollinearPoints(points2);
        LineSegment[] segments2 = collinearPoints2.segments();
        assertArrayEquals(new LineSegment[]{new LineSegment(new Point(14000, 10000), new Point(32000, 10000))}, segments2);
    }

    @Test
    public void input8() {
        Point[] points2 = new Point[]{
                new Point(10000, 0),
                new Point(0, 10000),
                new Point(3000, 7000),
                new Point(7000, 3000),
                new Point(20000, 21000),
                new Point(3000, 4000),
                new Point(14000, 15000),
                new Point(6000, 7000),
        };
        FastCollinearPoints collinearPoints2 = new FastCollinearPoints(points2);
        LineSegment[] segments2 = collinearPoints2.segments();

        assertArrayEquals(new LineSegment[]{
                new LineSegment(new Point(0, 10000), new Point(10000, 0)),
                new LineSegment(new Point(3000, 4000), new Point(20000, 21000)),
        }, segments2);
    }

    @Test
    public void vertical() {
        Point[] points2 = new Point[]{
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3),
                new Point(0, 30),
                new Point(0, 15),

        };
        FastCollinearPoints collinearPoints2 = new FastCollinearPoints(points2);
        LineSegment[] segments2 = collinearPoints2.segments();
        assertArrayEquals(new LineSegment[]{new LineSegment(new Point(0, 0), new Point(0, 30))}, segments2);
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

                new Point(2, 10),
                new Point(2, 20),
                new Point(2, 30),
                new Point(2, 40),
                new Point(200, 40),
        };
        FastCollinearPoints collinearPoints2 = new FastCollinearPoints(points2);
        LineSegment[] segments2 = collinearPoints2.segments();
        assertArrayEquals(new LineSegment[]{new LineSegment(new Point(0, 0), new Point(0, 30)), new LineSegment(new Point(2, 10), new Point(2, 40))}, segments2);
    }


}