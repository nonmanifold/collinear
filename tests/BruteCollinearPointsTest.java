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
        assertArrayEquals(new LineSegment[]{new LineSegment(new Point(0, 0), new Point(3, 3))}, collinearPoints1.segments());

        Point[] points2 = new Point[]{
                new Point(10, 0),
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(1, 300),
                new Point(20, 3),
        };
        BruteCollinearPoints collinearPoints2 = new BruteCollinearPoints(points2);
        assertEquals("one segment", 1, collinearPoints2.numberOfSegments());
        assertArrayEquals(new LineSegment[]{new LineSegment(new Point(0, 0), new Point(3, 3))}, collinearPoints2.segments());
    }
}