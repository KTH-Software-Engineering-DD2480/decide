package decide;

public class FinalUnlockingVector {
    // Final Unlocking Conditions
    public boolean[] conditions;

    FinalUnlockingVector(boolean[] puv, PreliminaryUnlockingMatrix pum) {
        this.conditions = new boolean[15];

        for (int i = 0; i < 15; i++) {
            boolean row = true;

            if (puv[i]) {
                for (int j = 0; j < 15; j++) {
                    if (i == j) continue;
                    row &= pum.conditions[i][j];
                }
            }

            this.conditions[i] = row;
        }
    }
}