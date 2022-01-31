package decide;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PreliminaryUnlockingMatrixTest {
    // Shorthand for creating new point
    private Point p(double x, double y) {
        return new Point(x, y);
    }
    // Shorthands for the logical operators defined in Input.java
    private static final Input.LogicalOperator N = Input.LogicalOperator.NOT_USED;
    private static final Input.LogicalOperator A = Input.LogicalOperator.AND;
    private static final Input.LogicalOperator O = Input.LogicalOperator.OR;

    // Create a cmv with all true for testing the functionality of the PUM calculator. Run the
    // PreliminaryUnlockingMatrix(input, cmv) and then assert that the PUM's conditions are correct (ie all true 
    // since AND, OR or NOT_USED all equate to true for only true inputs).
    @Test
    void PUMassertsCorrectly() {
        // Setup the *test* ConditionsMetVector
        boolean[] allTrue = new boolean[] {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
        ConditionsMetVector cmv = new ConditionsMetVector(allTrue);
        
        Input input = new Input();
        // Setup the symmetric Logical Connector Matrix, values are arbritrary (any A could equally well be O)
        input.lcm = new Input.LogicalOperator[][] {
            {A, O, O, A, A, N, A, A, O, A, A, A, O, O, N},
            {O, A, O, A, A, A, O, O, O, A, O, O, O, O, N},
            {O, O, A, A, N, A, O, A, O, O, A, A, A, A, N},
            {A, A, A, A, O, A, A, O, A, O, A, A, O, O, N},
            {A, A, N, O, A, O, O, A, A, A, O, A, O, N, N},
            {N, A, A, A, O, A, O, N, N, A, O, A, O, O, N},
            {A, O, O, A, O, O, A, A, O, N, A, N, N, N, N},
            {A, O, A, O, A, N, A, A, A, O, O, O, A, A, N},
            {O, O, O, A, A, N, O, A, A, A, O, O, N, O, N},
            {A, A, O, O, A, A, N, O, A, A, O, A, O, O, N},
            {A, O, A, A, O, O, A, O, O, O, A, N, A, O, N},
            {A, O, A, A, A, A, N, O, O, A, N, A, O, A, N},
            {O, O, A, O, O, O, N, A, N, O, A, O, A, A, N},
            {O, O, A, O, N, O, N, A, O, O, O, A, A, A, N},
            {N, N, N, N, N, N, N, N, N, N, N, N, N, N, A},
        };

        PreliminaryUnlockingMatrix pum = new PreliminaryUnlockingMatrix(input, cmv);

        // Check that the PUM's conditions are all true
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                assertEquals(true, pum.conditions[row][col]);
            }
        }
    }
}
