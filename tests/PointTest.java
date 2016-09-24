import org.junit.Test;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class PointTest {

    @Test
    public void compareTo() throws Exception {
        /*
        The compareTo() method should compare points by their y-coordinates,
        breaking ties by their x-coordinates.
        Formally, the invoking point (x0, y0) is less than the argument point (x1, y1)
        if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
         */
        Point p0 = new Point(10, 0);
        Point p1 = new Point(10, 1);
        assertEquals("p0 is less then p1 when y0 < y1", p0.compareTo(p1), -1);
        assertEquals("p1 is greather than p0 when y0 < y1", p1.compareTo(p0), 1);

        p0 = new Point(10, 50);
        p1 = new Point(100, 50);
        assertEquals("p0 is less than p1 when y0 = y1 and x0 < x1", p0.compareTo(p1), -1);
        assertEquals("p1 is greather than p0 when y0 = y1 and x0 < x1", p1.compareTo(p0), 1);

        assertEquals("p0.compareTo(p0) is 0", p0.compareTo(p0), 0);
        assertEquals("p1.compareTo(p1) is 0", p1.compareTo(p1), 0);
    }

    @Test
    public void slopeTo() {
        /*
          The slopeTo() method should return the slope between the
           invoking point (x0, y0) and the argument point (x1, y1),
           which is given by the formula (y1 − y0) / (x1 − x0).

          Treat the slope of a horizontal line segment as positive zero;
          treat the slope of a vertical line segment as positive infinity;
          treat the slope of a degenerate line segment (between a point and itself) as negative infinity.
         */

        int x0 = 10;
        int y0 = 20;
        Point p0 = new Point(x0, y0);
        int x1 = 30;
        int y1 = 40;
        Point p1 = new Point(x1, y1);

        float slope = (y1 - y0) / (x1 - x0);
        assertEquals(p0.slopeTo(p1), slope, 0.001);
    }

}