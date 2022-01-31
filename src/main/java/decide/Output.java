package decide;

public class Output {
    // Boolean to be outputed from Decide
    public boolean launch;

    Output(FinalUnlockingVector fuv) {
        this.launch = true;
        for (int i = 0; i < 15 ; i++) {
            this.launch &= fuv.conditions[i];
        }
    }
}
