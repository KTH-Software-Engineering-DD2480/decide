package decide;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class FinalUnlockingVectorTest {
    static PreliminaryUnlockingMatrix filledPum(boolean value) {
        boolean[][] pumConditions = new boolean[15][15];

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                pumConditions[i][j] = value;
            }
        }
        return new PreliminaryUnlockingMatrix(pumConditions);
    }

    @Test
    void FuvAssertTureIfPuvIsAllTrueAndPumAllTrue() {
        boolean[] puv = new boolean[15];
        Arrays.fill(puv, true);

        PreliminaryUnlockingMatrix pum = filledPum(true);

        FinalUnlockingVector fuv = new FinalUnlockingVector(puv, pum);
        for (int i = 0; i < 15; i++) {
            assertTrue(fuv.conditions[i]);
        }
    }

    @Test
    void FuvAssertTureIfPuvIsAllTrueAndPumAllFalse() {
        boolean[] puv = new boolean[15];
        Arrays.fill(puv, true);

        PreliminaryUnlockingMatrix pum = filledPum(false);

        FinalUnlockingVector fuv = new FinalUnlockingVector(puv, pum);
        for (int i = 0; i < 15; i++) {
            assertFalse(fuv.conditions[i]);
        }
    }

    // If all puv is false fuv should be true for all conditions
    @Test 
    void FuvAssertTrueIfPuvIsAllFalse(){
        boolean[] puv = new boolean[15];
        PreliminaryUnlockingMatrix pum = filledPum(false);
        FinalUnlockingVector fuv = new FinalUnlockingVector(puv, pum);

        for (int i = 0; i < 15; i++) {
            assertTrue(fuv.conditions[i]);
        }
    }
}
