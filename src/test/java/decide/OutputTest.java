package decide;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class OutputTest {
	// If all elements in the FUV is true then LAUNCH should be set to true
    @Test
    void assertTrueIfAllFuvIsTrue(){
        boolean[] conditions = new boolean[15];
        Arrays.fill(conditions, true);

        FinalUnlockingVector fuv = new FinalUnlockingVector(conditions);
        Output out = new Output(fuv);
        
        assertTrue(out.launch);
    }

	// LAUNCH should be set to false if FUV contains false
    @Test
    void assertFalseIfAnyFuvIsFalse() {
        boolean[] conditions = new boolean[15];
        Arrays.fill(conditions, true);
        conditions[4] = false;

        FinalUnlockingVector fuv = new FinalUnlockingVector(conditions);
        Output out = new Output(fuv);
        
        assertFalse(out.launch);
    }
}