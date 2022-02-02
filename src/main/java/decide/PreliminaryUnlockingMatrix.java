package decide;

public class PreliminaryUnlockingMatrix {
    public boolean[][] conditions;

    PreliminaryUnlockingMatrix(boolean[][] initialConditions) {
        this.conditions = initialConditions;
    }

    PreliminaryUnlockingMatrix(Input input, ConditionsMetVector cmv) {
        if (!input.isLCMSymmetric()) {
            throw new RuntimeException("`Input.lcm` is not symmetric");
        }

        this.conditions = new boolean[15][15];
        for (int row = 0; row < 15; row++) {
            for (int col = row; col < 15; col++) {   // we only need to iterate over the upper triangle (cause symmetry)
                if (input.lcm[row][col] == Input.LogicalOperator.NOT_USED) {
                    this.conditions[row][col] = true;
                    this.conditions[col][row] = true;
                } else if (input.lcm[row][col] == Input.LogicalOperator.AND) {
                    if (cmv.conditions[row] && cmv.conditions[col]) {
                        this.conditions[row][col] = true;
                        this.conditions[col][row] = true;
                    }
                } else { // OR
                    if (cmv.conditions[row] || cmv.conditions[col]) {
                        this.conditions[row][col] = true;
                        this.conditions[col][row] = true;
                    }
                }
            }
        }
    }
}
