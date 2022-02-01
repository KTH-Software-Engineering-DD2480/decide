package decide;

import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    void main_test_with_args() {
        Main.main(new String[] {"./in/input1.txt"});
    }

    // Must redirect System.in or will read empty input and crash
    // @Test
    // void main_test_stdin() {
    //     Main.main(new String[0]); 
    // }

}
