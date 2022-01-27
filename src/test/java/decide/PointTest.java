package decide;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PointTest {
    static final double EPSILON = 1e-6;

    // Shorthand for creating new point
    private Point p(double x, double y) {
        return new Point(x, y);
    }

    @Test
    void distance() {
        assertEquals(1, p(0, 0).distance(p(1, 0)), EPSILON);
        assertEquals(Math.sqrt(2), p(0, 0).distance(p(1, 1)), EPSILON);
        assertEquals(5, (p(1, 2)).distance(p(4, 6)), EPSILON);
    }

    @Test
    void angle() {
        // 90-degree angle
        assertEquals(Math.PI / 2, Point.angle(p(1, 0), p(0, 0), p(0, 1)), EPSILON);

        // 45-degree angle
        assertEquals(Math.PI / 4, Point.angle(p(1, 1), p(0, 0), p(0, 1)), EPSILON);
        // make sure we always choose the smallest positive angle
        assertEquals(Math.PI / 4, Point.angle(p(-1, 1), p(0, 0), p(0, 1)), EPSILON);

        // colinear points
        assertEquals(Math.PI, Point.angle(p(-1, 0), p(0, 0), p(1, 0)), EPSILON);
    }

    @Test
    void triangleArea() {
        assertEquals(0.5, Point.triangleArea(p(0, 0), p(1, 0), p(0, 1)), EPSILON);
        assertEquals(1.0, Point.triangleArea(p(0, 0), p(2, 0), p(0, 1)), EPSILON);
        assertEquals(4.0, Point.triangleArea(p(2, 1), p(5, 3), p(3, -1)), EPSILON);
    }
}
