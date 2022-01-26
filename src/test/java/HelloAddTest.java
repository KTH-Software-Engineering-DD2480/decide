import hello.HelloWorld;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloAddTest {
    @Test
    void addHelloGood() {
        assertEquals(HelloWorld.add(1, 2), 3);
    }

    @Test
    void addHelloBad() {
        assertEquals(HelloWorld.add(1, 2), 4);
    }
}
