package decide;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class ConditionsMetVectorTest {
    @Test
    void LIC4differentQuadrants() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        input.parameters.q_points = 3;
        input.parameters.quads = 2;
        input.points = new Point[]{
            new Point(0, 0),
            new Point(-1, 0),
            new Point(0, -1),
        };
        assertTrue(ConditionsMetVector.LIC4(input));

        // It is not possible for 2 points to be in 3 quadrants
        input.parameters.q_points = 2;
        assertFalse(ConditionsMetVector.LIC4(input));

        // If we reduce the number of quadrants, it should be possible for 2 points
        input.parameters.quads = 1;
        assertTrue(ConditionsMetVector.LIC4(input));
    }

    @Test
    void LIC4sameQuadrant() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        input.parameters.q_points = 3;
        input.parameters.quads = 2;
        input.points = new Point[]{
            new Point(0, 0),
            new Point(1, 0),
            new Point(0, 1),
        };
        // All points are in the same quadrant
        assertFalse(ConditionsMetVector.LIC4(input));
    }

    @Test
    void quadrant() {
        // Extracted from the specification
        assertEquals(0, ConditionsMetVector.quadrant(new Point(0, 0)));
        assertEquals(1, ConditionsMetVector.quadrant(new Point(-1, 0)));
        assertEquals(2, ConditionsMetVector.quadrant(new Point(0, -1)));
        assertEquals(0, ConditionsMetVector.quadrant(new Point(0, 1)));
        assertEquals(0, ConditionsMetVector.quadrant(new Point(1, 0)));

        // Make sure that the last quadrant works
        assertEquals(3, ConditionsMetVector.quadrant(new Point(1, -1)));
    }

	// Test for the LIC 5
	@Test
	void test_LIC5() {
		Input input = new Input();

		// Negative test
		input.points = new Point[] { new Point(1, 2), new Point(2, 3), new Point(2, 4) };
		assertFalse(ConditionsMetVector.LIC5(input));

		// Positive test
		input.points = new Point[] { new Point(1, 2), new Point(3, 3), new Point(2, 4) };
		assertTrue(ConditionsMetVector.LIC5(input));
	}

    
    // Test for LIC 6
    @Test
    void test_LIC6() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        input.parameters.n_points = 3;
        input.parameters.dist = 3;

        // test the base case
        input.points = new Point[]{new Point(0, 0),new Point(1, 1)};
        assertFalse(ConditionsMetVector.LIC6(input));

        // positive test with different start and end point
        input.points = new Point[] { new Point(1, 2), new Point(6, 6), new Point(1,1), new Point(0, 6)};
		assertTrue(ConditionsMetVector.LIC6(input));

		// Negative test with different start and end point
		input.points = new Point[] { new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3,3)};
		assertFalse(ConditionsMetVector.LIC6(input));

		// positive test with the same start and end point
		input.points = new Point[] { new Point(1, 2), new Point(2, 2), new Point(10,10), new Point(1, 2)};
		assertTrue(ConditionsMetVector.LIC6(input));

        // negative test with the same start and end point
		input.points = new Point[] { new Point(1, 2), new Point(1, 1), new Point(2,2), new Point(1, 2)};
		assertFalse(ConditionsMetVector.LIC6(input));
	}


    @Test
    void distance() {
        // Make sure that the computed distance is correct
        assertEquals(1.2, ConditionsMetVector.distance(new Point(0, 1.5), new Point(2,0), new Point(0,0)));

        // A point on the line should result in 0
        assertEquals(0, ConditionsMetVector.distance(new Point(2, 2), new Point(0,0), new Point(1,1)));
 
    }
}
