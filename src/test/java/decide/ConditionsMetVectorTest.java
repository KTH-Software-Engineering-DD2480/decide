package decide;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class ConditionsMetVectorTest {

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
