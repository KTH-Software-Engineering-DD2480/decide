package decide;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class ConditionsMetVectorTest {

    // LIC 0 - Test 1
    // Assert true if there exist only two points that have a distance greater than 10
    // Where the Length1 is 10
    @Test
    void LIC0assertTrueIfDistanceIsGreaterThanLength1() {
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.length1 = 10;
        input.parameters.points = new Point[] {
            new Point(0,0),
            new Point(0,11),
        };

        //Assert
        assertTrue(ConditionsMetVector.LIC1(input));
    }

    // LIC 0 - Test 2
    // Assert false if there exist only two points that have a distance smaller than 10
    // Where the Length1 is 10
    @Test
    void LIC0AssertFalseIfDistanceIsLessThanAsLength1() {
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.length1 = 10;
        input.parameters.points = new Point[] {
            new Point(0,0),
            new Point(0,9),
        };

        //Assert
        assertFalse(ConditionsMetVector.LIC1(input));
    }

    // LIC 0 - Test 3
    // Assert false if there exist only two points that have a distance than 10
    // Where the Length1 is 10
    @Test
    void LIC0AssertFalseIfDistanceIsSameAsLength1() {
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.length1 = 10;
        input.parameters.points = new Point[] {
            new Point(0,0),
            new Point(0,10),
        };

        //Assert
        assertFalse(ConditionsMetVector.LIC1(input));
    }
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
}
