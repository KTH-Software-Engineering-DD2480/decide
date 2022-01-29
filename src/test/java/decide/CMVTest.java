package decide;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CMVTest {
    // Same input available for all tests, this makes sure the LIC doesn't alter the input.
    // If a unique input is wanted, simply write new data to the input object.
    Input input = new Input();

    // Shorthand for creating new point
    private Point p(double x, double y) {
        return new Point(x, y);
    }

    @Test
    void LIC1() {
        // Largest possible triangle that will fit in a circle of radius 1
        input.points = new Point[] {p(0, 0), p(Math.sqrt(3), 0), p(Math.sqrt(3)/2, 1.5)};
        input.parameters = new Input.Parameters();
        input.parameters.radius1 = 1;
        assertEquals(false, new ConditionsMetVector(input).LIC1(input));

        // Edge case: all points equal
        input.points = new Point[] {p(0, 0), p(0, 0), p(0, 0)};
        assertEquals(false, new ConditionsMetVector(input).LIC1(input));

        // Edge case: all points collinear and unevenly spaced, but can fit inside optimal circle
        input.points = new Point[] {p(0, 0), p(1, 0), p(10, 0)};
        input.parameters.radius1 = 5;
        assertEquals(false, new ConditionsMetVector(input).LIC1(input));

        // Testing non-optimized case (neither optimization 1 or 2 works)
        input.points = new Point[] {p(0.45, -0.45), p(1.3, -0.45), p(Math.sqrt(3)/2, 1.5)};
        input.parameters.radius1 = 1;
        assertEquals(true, new ConditionsMetVector(input).LIC1(input));
    }
}
