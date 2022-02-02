package decide;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MainTest {

	// Shorthands for the logical operators defined in Input.java
	private static final Input.LogicalOperator N = Input.LogicalOperator.NOT_USED;
	private static final Input.LogicalOperator A = Input.LogicalOperator.AND;
	private static final Input.LogicalOperator O = Input.LogicalOperator.OR;

    @Test
    void main_test_with_args(){
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
	
	@Test
	void assertTrueIfCorrectInput() {
		// The parameters should result in the follwing:
		// True LIC: 0, 1, 2, 3, 4, 5, 6, 7, 11, 12
		// False LIC: 8, 9, 10, 13, 14

		// Define the parameters
		Input input = new Input();
		input.parameters = input.new Parameters();
		input.parameters.length1 = 3;
		input.parameters.radius1 = 1;
		input.parameters.epsilon1 = Math.PI / 2.0;
		input.parameters.area = 5;
		input.parameters.q_points = 3;
		input.parameters.quads = 1;
		input.parameters.dist = 3;
		input.parameters.n_points = 3;
		input.parameters.k_points = 3;
		input.parameters.a_points = 3;
		input.parameters.b_points = 3;
		input.parameters.c_points = 2;
		input.parameters.d_points = 2;
		input.parameters.e_points = 2;
		input.parameters.f_points = 3;
		input.parameters.g_points = 2;
		input.parameters.length2 = 10;
		input.parameters.radius2 = 1.1;
		input.parameters.area2 = 15;

		// Define the points
		input.points = new Point[] {
				new Point(0, 0),
				new Point(0, 4),
				new Point(0.45, -0.45),
				new Point(1.3, -0.45),
				new Point(Math.sqrt(3) / 2, 1.5),
				new Point(0, -5),
				new Point(5, 0), };

		// Define LCM
		input.lcm = new Input.LogicalOperator[][] {
				{ A, O, O, A, A, O, A, A, O, O, O, A, O, O, O }, // 0
				{ O, A, O, A, A, A, O, O, O, N, O, O, O, O, N }, // 1
				{ O, O, A, A, N, A, O, A, O, O, N, A, A, N, O }, // 2
				{ O, A, A, A, O, A, A, O, O, O, N, A, O, O, O }, // 3
				{ A, A, N, O, A, O, O, A, N, O, O, A, O, N, N }, // 4
				{ N, A, A, A, O, A, O, N, N, N, O, A, O, O, O }, // 5
				{ A, O, O, A, O, O, A, A, O, N, O, N, N, N, N }, // 6
				{ A, O, A, O, A, N, A, A, O, O, O, O, A, O, N }, // 7
				{ O, O, O, A, A, O, O, A, N, O, O, O, A, O, A }, // 8
				{ A, A, O, O, A, A, A, O, O, N, O, A, O, O, A }, // 9
				{ A, O, A, A, O, O, A, O, O, O, A, O, A, O, A }, // 10
				{ A, O, A, A, A, A, N, O, O, N, N, A, O, O, O }, // 11
				{ O, O, A, O, O, O, N, A, N, O, N, O, A, N, N }, // 12
				{ O, O, A, O, O, O, N, A, O, O, O, A, A, A, A }, // 13
				{ A, O, O, A, A, O, A, O, O, N, O, A, A, O, A }, // 14
		};

		// Define PUV
		input.puv = new boolean[] { true, true, true, true, true, true, true, true, false, false, false, true, true,
				false, false };
		
		Output output = computeOut(input);
		assertTrue(output.launch);

	}

}
