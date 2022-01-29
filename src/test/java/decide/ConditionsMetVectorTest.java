package decide;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class ConditionsMetVectorTest {
    // Shorthand for creating new point
    private Point p(double x, double y) {
        return new Point(x, y);
    }

    // LIC 0 - Test 1
    // Assert true if there exist only two points that have a distance greater than 10
    // Where the Length1 is 10
    @Test
    void LIC0assertTrueIfDistanceIsGreaterThanLength1() {
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.parameters.length1 = 10;
        input.points = new Point[] {
            new Point(0,0),
            new Point(0,11),
        };

        //Assert
        assertTrue(ConditionsMetVector.LIC0(input));
    }

    // LIC 0 - Test 2
    // Assert false if there exist only two points that have a distance smaller than 10
    // Where the Length1 is 10
    @Test
    void LIC0AssertFalseIfDistanceIsLessThanAsLength1() {
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.parameters.length1 = 10;
        input.points = new Point[] {
            new Point(0,0),
            new Point(0,9),
        };

        //Assert
        assertFalse(ConditionsMetVector.LIC0(input));
    }

    // LIC 0 - Test 3
    // Assert false if there exist only two points that have a distance than 10
    // Where the Length1 is 10
    @Test
    void LIC0AssertFalseIfDistanceIsSameAsLength1() {
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.parameters.length1 = 10;
        input.points = new Point[] {
            new Point(0,0),
            new Point(0,10),
        };

        //Assert
        assertFalse(ConditionsMetVector.LIC0(input));
    }

    // LIC1 - Test 1
    // Assert that the largest possible triangle fits inside a circle of radius 1
    // See: https://www.geogebra.org/calculator/agwc9tmw (A-B-C) for diagram
    @Test
    void LIC1_largest_triangle_to_fit() {
        // Largest possible triangle that will fit in a circle of radius 1
        Input input = new Input();
        input.points = new Point[] {p(0, 0), p(Math.sqrt(3), 0), p(Math.sqrt(3)/2, 1.5)};
        input.parameters = input.new Parameters();
        input.parameters.radius1 = 1;
        assertEquals(false, new ConditionsMetVector(input).LIC1(input));
    }

    // LIC1 - Test 2
    // Assert that some edge cases fit inside circle of radius 1
    @Test
    void LIC1_edge_cases() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        input.parameters.radius1 = 1;
        // Edge case: all points equal
        input.points = new Point[] {p(0, 0), p(0, 0), p(0, 0)};
        assertEquals(false, new ConditionsMetVector(input).LIC1(input));

        // Edge case: all points collinear and unevenly spaced, but can fit inside optimal circle
        input.points = new Point[] {p(0, 0), p(1, 0), p(10, 0)};
        input.parameters.radius1 = 5;
        assertEquals(false, new ConditionsMetVector(input).LIC1(input));
    }

    // LIC1 - Test 3
    // Assert that last resort (non-optimized solution) works
    @Test
    void LIC1_non_optimized_case() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        input.parameters.radius1 = 1;
        // Testing non-optimized case (neither optimization 1 or 2 works)
        input.points = new Point[] {p(0.45, -0.45), p(1.3, -0.45), p(Math.sqrt(3)/2, 1.5)};
        input.parameters.radius1 = 1;
        assertEquals(true, new ConditionsMetVector(input).LIC1(input));
    }

    // LIC 2 - Test 1
    // Assert true if there exists three consectutive nodes that have an angle smaller than PI - Epsilon
    // Where epsilon is PI/2
    @Test
    void LIC2AssertTrueIfAngleIsSmallerPIMinusEpsilon(){
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.parameters.epsilon1 = Math.PI / 2.0;
        input.points = new Point[] {
            new Point(1,0),
            new Point(0,0),
            new Point(1.0/2.0, Math.sqrt(3)/2.0),
        };

        //Assert
        assertTrue(ConditionsMetVector.LIC2(input));
    }

    // LIC 2 - Test 2
    // Assert false if there exists three consectutive nodes that have an angle bigger than PI - Epsilon
    // Where epsilon is PI/2
    @Test
    void LIC2AssertFalseIfAngleisBiggerThanPIMinusEpsilon(){
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.parameters.epsilon1 = Math.PI / 2.0;
        input.points = new Point[] {
            new Point(1,0),
            new Point(0,0),
            new Point(-Math.sqrt(3)/2.0,1.0/2.0),
        };

        //Assert
        assertFalse(ConditionsMetVector.LIC2(input));
    }

    // LIC 2 - Test 3
    // Assert false if two points coincides
    // Where epsilon is PI/2
    @Test
    void LIC2AssertFalseIfpointsCoincides(){
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.parameters.epsilon1 = Math.PI / 2.0;
        input.points = new Point[] {
            new Point(0,0),
            new Point(0,0),
            new Point(-Math.sqrt(3)/2.0,1.0/2.0),
        };

        //Assert
        assertFalse(ConditionsMetVector.LIC2(input));
    }
    
    @Test
    void LIC3trueTest() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        // use `area` = 10 for testing purposes
        input.parameters.area = 10;

        // expected area = 12.5
        input.points = new Point[] {    
            new Point(0, 0),
            new Point(0, 5),
            new Point(5, 0)
        };

        assertTrue(ConditionsMetVector.LIC3(input, 0, 0));
    }

    @Test
    void LIC3falseTest() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        // use `area` = 10 for testing purposes
        input.parameters.area = 10;

        // expected area = 2
        input.points = new Point[] {    
            new Point(0, 0),
            new Point(0, 2),
            new Point(2, 0)
        };

        assertFalse(ConditionsMetVector.LIC3(input, 0, 0));
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

    // LIC 9 - Test 1
    // Assert true if there exists three nodes seperated by Cpts and dpts that have an angle smaller than PI - Epsilon
    // Where epsilon is PI/2 and cPts = 2 and dPts=3
    @Test
    void LIC9AssertTrueIfAngleIsSmallerPIMinusEpsilon(){
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.parameters.epsilon1 = Math.PI / 2.0;
        input.parameters.c_points = 2;
        input.parameters.d_points = 2;
        input.points = new Point[] {
            new Point(0,0),
            new Point(1,0), // First point
            new Point(0,0),
            new Point(3,0),
            new Point(0,0), // Second point
            new Point(4,0),
            new Point(3,0),
            new Point(1.0/2.0, Math.sqrt(3)/2.0), // Third Point
            new Point(6,0),
        };

        //Assert
        assertTrue(ConditionsMetVector.LIC9(input));
    }

    // LIC 9 - Test 2
    // Assert false if there exists three nodes seperated by Cpts and dpts that have an angle larger than PI - Epsilon
    // Where epsilon is PI/2 and cPts = 2 and dPts=3
    @Test
    void LIC9AssertFalseIfAngleisBiggerThanPIMinusEpsilon(){
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.parameters.epsilon1 = Math.PI / 2.0;
        input.parameters.c_points = 2;
        input.parameters.d_points = 2;
        input.points = new Point[] {
            new Point(0,0),
            new Point(1,0), // First point
            new Point(0,0),
            new Point(3,0),
            new Point(0,0), // Second point
            new Point(4,0),
            new Point(3,0),
            new Point(-Math.sqrt(3)/2.0,1.0/2.0), // Third Point
            new Point(6,0),
        };

        //Assert
        assertFalse(ConditionsMetVector.LIC9(input));
    }

    // LIC 9 - Test 3
    // Assert false if numpoints < 5
    // Where epsilon is PI/2
    @Test
    void LIC9AssertFalseIfpointsIsLessThanFive(){
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.parameters.epsilon1 = Math.PI / 2.0;
        input.parameters.c_points = 1;
        input.parameters.d_points = 1;
        input.points = new Point[] {
            new Point(1,0),
            new Point(0,0),
            new Point(-Math.sqrt(3)/2.0,1.0/2.0),
            new Point(2,0),
        };

        //Assert
        assertFalse(ConditionsMetVector.LIC9(input));
    }

    // LIC 9 - Test 4
    // Assert false if points coincide
    // Where epsilon is PI/2
    @Test
    void LIC9AssertFalseIfpointsCoincide(){
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.parameters.epsilon1 = Math.PI / 2.0;
        input.parameters.c_points = 1;
        input.parameters.d_points = 1;
        input.points = new Point[] {
            new Point(1,0),
            new Point(0,0),
            new Point(0,0),
            new Point(1.0/2.0, Math.sqrt(3)/2.0),
            new Point(0,0),
            new Point(1.0/2.0, Math.sqrt(3)/2.0),
        };

        //Assert
        assertFalse(ConditionsMetVector.LIC9(input));
    }

    @Test
    void LIC10trueTest() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        // use `area` = 10 for testing purposes
        input.parameters.area = 10;

        input.parameters.e_points = 2;
        input.parameters.f_points = 3;

        // expected area = 12.5
        input.points = new Point[] {    
            new Point(0, 0),
            new Point(1, 1),
            new Point(1, 1),
            new Point(0, 5),
            new Point(1, 1),
            new Point(1, 1),
            new Point(1, 1),
            new Point(5, 0)
        };

        assertTrue(ConditionsMetVector.LIC10(input));
    }

    @Test
    void LIC10falseTest() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        // use `area` = 10 for testing purposes
        input.parameters.area = 10;

        input.parameters.e_points = 2;
        input.parameters.f_points = 3;

        // expected area = 2
        input.points = new Point[] {    
            new Point(0, 0),
            new Point(1, 1),
            new Point(1, 1),
            new Point(0, 2),
            new Point(1, 1),
            new Point(1, 1),
            new Point(1, 1),
            new Point(2, 0)
        };

        assertFalse(ConditionsMetVector.LIC10(input));
    }

    @Test
    void LIC10lengthTest() {
        Input input = new Input();
        input.parameters = input.new Parameters();

        // number of points < 5
        input.points = new Point[] {    
            new Point(0, 0),
            new Point(1, 1),
            new Point(1, 1),
            new Point(0, 5),
        };

        assertFalse(ConditionsMetVector.LIC10(input));
    }

    // LIC 12 - Test 1
    // Assert true if two datapoints seperated at K_points have a distance greater than length1
    // And two datapoints seperated at k_points have distance less than length2
    // And more than 3 datapoints
    // Where the Length1 is 10
    // K_pts = 2
    @Test
    void LIC12assertTrueIfDistanceIsGreaterThanLength1AndSmallerThanLength2() {
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.parameters.length1 = 10;
        input.parameters.length2 = 10;
        input.parameters.k_points = 2;
        input.points = new Point[] {
            new Point(0,5), // Condition 2
            new Point(0,0), // Condition 1
            new Point(0,6), 
            new Point(0,7), // Condition 2
            new Point(0,11), // Condition 1
        };

        //Assert
        assertTrue(ConditionsMetVector.LIC12(input));
    }

    // LIC 12 - Test 2
    // Assert false if two datapoints seperated at K_points have a distance smaller than length1
    // And two datapoints have distance smaller than length2
    // Where the Length1 is 10
     // K_pts = 2
    @Test
    void LIC12AssertFalseIfDistanceIsLessThanAsLength1ButSmallerThanLength2() {
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.parameters.length1 = 10;
        input.parameters.length2 = 10;
        input.parameters.k_points = 2;
        input.points = new Point[] {
            new Point(0,0),
            new Point(0,1),
            new Point(0,2),
            new Point(0,3),
        };

        //Assert
        assertFalse(ConditionsMetVector.LIC12(input));
    }

    // LIC 12 - Test 3
    // Assert false if two datapoints seperated at K_points have a distance larger than length1
    // And two datapoints have distance larger than length2
    // Where the Length1 is 10
     // K_pts = 2
     @Test
     void LIC12AssertFalseIfDistanceIsLargerThanAsLength1ButLargerThanLength2() {
         Input input = new Input();
         input.parameters = input.new Parameters();
 
         //Setup
         input.parameters.length1 = 10;
         input.parameters.length2 = 1;
         input.parameters.k_points = 2;
         input.points = new Point[] {
             new Point(0,0),
             new Point(0,11),
             new Point(0,22),
             new Point(0,33),
         };
 
         //Assert
         assertFalse(ConditionsMetVector.LIC12(input));
     }

     // LIC 12 - Test 4
    // Assert false if two datapoints less than 3
    // Where the Length1 is 10
     // K_pts = 2
     @Test
     void LIC12AssertFalseIfNumPointsIsLessThan3() {
         Input input = new Input();
         input.parameters = input.new Parameters();
 
         //Setup
         input.parameters.length1 = 10;
         input.parameters.length2 = 1;
         input.parameters.k_points = 2;
         input.points = new Point[] {
             new Point(0,0),
             new Point(0,11),
         };
 
         //Assert
         assertFalse(ConditionsMetVector.LIC12(input));
     }

}
