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
        assertEquals(false, ConditionsMetVector.LIC1(input, 0, 0, input.parameters.radius1, false));
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
        assertEquals(false, ConditionsMetVector.LIC1(input, 0, 0, input.parameters.radius1, false));

        // Edge case: all points collinear and unevenly spaced, but can fit inside optimal circle
        input.points = new Point[] {p(0, 0), p(1, 0), p(10, 0)};
        input.parameters.radius1 = 5;
        assertEquals(false, ConditionsMetVector.LIC1(input, 0, 0, input.parameters.radius1, false));
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
        assertEquals(true, ConditionsMetVector.LIC1(input, 0, 0, input.parameters.radius1, false));
    }

    // LIC 2 - Test 1
    // Assert true if there exists three consectutive nodes that have an angle smaller than PI - Epsilon
    // Where epsilon is PI/2
    @Test
    void LIC2AssertTrueIfAngleIsSmallerPIMinusEpsilon(){
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.parameters.epsilon = Math.PI / 2.0;
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
        input.parameters.epsilon = Math.PI / 2.0;
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
        input.parameters.epsilon = Math.PI / 2.0;
        input.points = new Point[] {
            new Point(0,0),
            new Point(0,0),
            new Point(-Math.sqrt(3)/2.0,1.0/2.0),
        };

        //Assert
        assertFalse(ConditionsMetVector.LIC2(input));
    }

    // Test that LIC3 returns true when area of triangle > `area`
    @Test
    void LIC3trueTest() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        // use `area` = 10 for testing purposes
        input.parameters.area1 = 10;

        // expected area = 12.5
        input.points = new Point[] {
            new Point(0, 0),
            new Point(0, 5),
            new Point(5, 0)
        };

        assertTrue(ConditionsMetVector.LIC3(input, 0, 0, false));
    }

    // Test that LIC3 returns false when area of triangle <= `area`
    @Test
    void LIC3falseTest() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        // use `area` = 10 for testing purposes
        input.parameters.area1 = 10;

        // expected area = 2
        input.points = new Point[] {
            new Point(0, 0),
            new Point(0, 2),
            new Point(2, 0)
        };

        assertFalse(ConditionsMetVector.LIC3(input, 0, 0, false));
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


    // Test for LIC 7
    @Test
    void test_LIC7(){
        Input input = new Input();
        input.parameters = input.new Parameters();
        input.parameters.k_points = 3;
        input.parameters.length1 = 2;
        
        // test the base case with less than 3 points
        input.points = new Point[]{new Point(0, 0),new Point(1, 1)};
        assertFalse(ConditionsMetVector.LIC7(input));

        // positive test with 3 consecutive intervening points and a distance greater than 2
        input.points = new Point[]{new Point(-1, -1),new Point(1, 1), new Point(2,2), new Point(3,3), new Point(4,4)};
        assertTrue(ConditionsMetVector.LIC7(input));

        // negative test with 3 consecutive intervening points and a distance less than 2
        input.points = new Point[]{new Point(0, 0),new Point(1, 1), new Point(-1,-1), new Point(0,1), new Point(-1,0)};
        assertFalse(ConditionsMetVector.LIC7(input));


    }


    // LIC8 - Test 1
    // Assert that LIC8 correctly calls LIC1 (which it depends on)
    @Test
    void LIC8_non_optimized_case() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        input.parameters.radius1 = 1;
        // Testing non-optimized case (neither optimization 1 or 2 works)
        input.points = new Point[] {
                p(0.45, -0.45), 
                p(0, 0), p(1, 1), p(2, 2),
                p(1.3, -0.45), 
                p(0, 0), p(1, 1), p(2, 2),
                p(Math.sqrt(3)/2, 1.5)};
        input.parameters.radius1 = 1;
        input.parameters.a_points = 3;
        input.parameters.b_points = 3;
        assertEquals(true, ConditionsMetVector.LIC8(input));
        input.parameters.radius1 = 1.05;
        assertEquals(false, ConditionsMetVector.LIC8(input));
    }

    // LIC 9 - Test 1
    // Assert true if there exists three nodes seperated by Cpts and dpts that have an angle smaller than PI - Epsilon
    // Where epsilon is PI/2 and cPts = 2 and dPts=3
    @Test
    void LIC9AssertTrueIfAngleIsSmallerPIMinusEpsilon(){
        Input input = new Input();
        input.parameters = input.new Parameters();

        //Setup
        input.parameters.epsilon = Math.PI / 2.0;
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
        input.parameters.epsilon = Math.PI / 2.0;
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
        input.parameters.epsilon = Math.PI / 2.0;
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
        input.parameters.epsilon = Math.PI / 2.0;
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

    // Test that LIC10 returns true when area of triangle > `area` 
    @Test
    void LIC10trueTest() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        // use `area` = 10 for testing purposes
        input.parameters.area1 = 10;

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

    // Test that LIC10 returns false if area of traingle <= `area`
    @Test
    void LIC10falseTest1() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        // use `area` = 10 for testing purposes
        input.parameters.area1 = 10;

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

    // Test that LIC10 returns false when `input.points.length < 5`
    @Test
    void LIC10falseTest2() {
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

    // Test for LIC 11
    @Test
    void test_LIC11(){
        Input input = new Input();
        input.parameters = input.new Parameters();
        input.parameters.g_points = 3;
        
        // test the base case with less than 3 points
        input.points = new Point[]{new Point(0, 0),new Point(1, 1)};
        assertFalse(ConditionsMetVector.LIC11(input));

        // positive test with 3 consecutive intervening points
        input.points = new Point[]{new Point(5, 4),new Point(1, 1), new Point(2,2), new Point(3,3), new Point(2,3)};
        assertTrue(ConditionsMetVector.LIC11(input));

        // negative test
        input.points = new Point[]{new Point(0, 4),new Point(1, 1), new Point(2,2), new Point(3,3), new Point(4,3), new Point(5,6)};
        assertFalse(ConditionsMetVector.LIC11(input));


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

    // LIC 13 - Test 1
    // Assert that non-optimized case is handled correctly, i.e LIC13 calls LIC1 with right parameters
    @Test
    void LIC13_non_optimized_case() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        input.parameters.radius1 = 1;
        // Testing non-optimized case (neither optimization 1 or 2 works)
        input.points = new Point[] {
                p(0.45, -0.45), 
                p(0, 0), p(1, 1), p(2, 2),
                p(1.3, -0.45), 
                p(0, 0), p(1, 1), p(2, 2),
                p(Math.sqrt(3)/2, 1.5)};
        input.parameters.radius1 = 1;
        input.parameters.radius2 = 1.1;
        input.parameters.a_points = 3;
        input.parameters.b_points = 3;
        assertEquals(true, ConditionsMetVector.LIC13(input));
    }

    // Test that LIC14 returns true if `area` < area of triangle < `area2`
    @Test
    void LIC14trueTest() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        input.parameters.area1 = 10; // minimum area for testing purposes
        input.parameters.area2 = 15; // maximum area for testing purposes

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

        assertTrue(ConditionsMetVector.LIC14(input));
    }

    // Test that LIC14 returns false if area of triangle <= `area` 
    @Test
    void LIC14falseTest1() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        input.parameters.area1 = 10; // minimum area for testing purposes
        input.parameters.area2 = 15; // maximum area for testing purposes

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

        assertFalse(ConditionsMetVector.LIC14(input));
    }

    // Test that LIC14 returns false if area of triangle >= `area2`  
    @Test
    void LIC14falseTest2() {
        Input input = new Input();
        input.parameters = input.new Parameters();
        input.parameters.area1 = 10; // minimum area for testing purposes
        input.parameters.area2 = 15; // maximum area for testing purposes

        input.parameters.e_points = 2;
        input.parameters.f_points = 3;

        // expected area = 2
        input.points = new Point[] {
            new Point(0, 0),
            new Point(1, 1),
            new Point(1, 1),
            new Point(0, 6),
            new Point(1, 1),
            new Point(1, 1),
            new Point(1, 1),
            new Point(6, 0)
        };

        assertFalse(ConditionsMetVector.LIC14(input));
    }

    // Test that LIC14 returns false `input.points.length` < 5
    @Test
    void LIC14falseTest3() {
        Input input = new Input();
        input.parameters = input.new Parameters();

        // number of points < 5
        input.points = new Point[] {
            new Point(0, 0),
            new Point(1, 1),
            new Point(1, 1),
            new Point(0, 5),
        };

        assertFalse(ConditionsMetVector.LIC14(input));
    }


}
