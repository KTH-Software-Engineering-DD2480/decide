package decide;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    // Shorthands for the logical operators defined in Input.java
    private static final Input.LogicalOperator N = Input.LogicalOperator.NOT_USED;
    private static final Input.LogicalOperator A = Input.LogicalOperator.AND;
    private static final Input.LogicalOperator O = Input.LogicalOperator.OR;

    @Test
    void main_test_with_args() {
        Main.main(new String[] {"./in/input1.txt"});
    }

    // Must redirect System.in or will read empty input and crash
    @Test
    void main_test_stdin() {
        try {
            Main.main(new String[0]);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // Assert that main function returns false for incorrect input
    @Test
    void assertFalseIfIncorrectInput() {

        Input input = new Input();
        input.parameters = input.new Parameters();

        /**
         * Following LCM will give launch=false if LIC:s are assigned as follows:
         * false: 0, 1, 3, 5, 6
         * true: 2, 4, 7, 9, 12
         */
        input.lcm = new Input.LogicalOperator[][] {
            {A, A, A, A, A, A, A, A, A, A, A, A, A, A, A},
            {A, A, O, O, O, O, O, O, O, O, O, O, O, O, O},
            {A, O, A, A, N, A, O, A, O, O, A, A, A, A, O},
            {A, O, A, A, O, A, A, O, A, O, A, A, O, O, O},
            {A, O, N, O, A, A, O, A, A, A, O, A, O, N, O},
            {A, O, A, A, A, A, O, N, N, A, O, A, O, O, O},
            {A, O, O, A, O, O, A, A, O, N, A, N, N, N, O},
            {A, O, A, O, A, N, A, A, A, O, O, O, A, A, O},
            {A, O, O, A, A, N, O, A, A, A, O, O, N, O, O},
            {A, O, O, O, A, A, N, O, A, A, A, A, O, O, O},
            {A, O, A, A, O, O, A, O, O, A, A, O, A, O, O},
            {A, O, A, A, A, A, N, O, O, A, O, A, O, A, O},
            {A, O, A, O, O, O, N, A, N, O, A, O, A, A, O},
            {A, O, A, O, N, O, N, A, O, O, O, A, A, A, O},
            {A, O, O, O, O, O, O, O, O, O, O, O, O, O, A},
        };

        assertTrue(input.isLCMSymmetric());

        input.parameters.length1 = 100;
        input.parameters.radius1 = 300;
        input.parameters.epsilon = Math.PI/4; // create three consecutive points that form angle < 135 -> LIC2 = true
        input.parameters.radius2 = 400;
        input.parameters.area1 = 700;
        input.parameters.q_points = 2;
        input.parameters.quads = 1;     // create q points that lie in quad1 and quad2 respectively -> LIC4 = true
        input.parameters.n_points = 3;
        input.parameters.dist = 100;
        input.parameters.k_points = 1;  // create two points, with one intervening point, such that their distance > 100 -> LIC7 = true
        input.parameters.a_points = 1;
        input.parameters.b_points = 1;
        input.parameters.c_points = 1;
        input.parameters.d_points = 1;  // create two points, with c_points and d_points intervening points respectively, such that their angle < 95
        input.parameters.e_points = 1;
        input.parameters.f_points = 1;
        input.parameters.g_points = 1;  // make sure that all x-coordinates are non decreasing
        input.parameters.length2 = 200;
        input.parameters.area2 = 202;

        input.points = new Point[]{
            new Point(-5, 0),
            new Point(0, 0),
            new Point(0, 5),    // LIC2 = true
            new Point(6, 0),
            new Point(7, -6),   // LIC4 = true
            new Point(8, 0),
            new Point(9, 150),
            new Point(9, 150),  // LIC7 = true
            new Point(10, 10),
            new Point(10, 0),
            new Point(20, 10),  // LIC9 = true, LIC12 = true
        };
        
        input.puv = new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};

        ConditionsMetVector cmv = new ConditionsMetVector(input);
        boolean[] conditions = cmv.conditions;

        // Assert that LIC:s are assigned as intended
        assertTrue(conditions[0]);
        assertFalse(conditions[1]);
        assertTrue(conditions[2]);
        assertFalse(conditions[3]);
        assertTrue(conditions[4]);
        assertFalse(conditions[5]);
        assertFalse(conditions[6]);
        assertTrue(conditions[7]);
        assertFalse(conditions[8]);
        assertTrue(conditions[9]);
        assertFalse(conditions[10]);
        assertFalse(conditions[11]);
        assertTrue(conditions[12]);
        assertFalse(conditions[13]);
        assertFalse(conditions[14]);

        Output output = Main.computeOutput(input);
        assertFalse(output.launch);
    }
}