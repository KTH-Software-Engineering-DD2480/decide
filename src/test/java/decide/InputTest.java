package decide;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import decide.Input.LogicalOperator;

public class InputTest {
    @Test
    void parseSimple() {
        String text = """
            POINTS: 
                1 2
                3 4
                5 6

            LCM: 
                & & & & & & & & & & & & & & &
                | | | | | | | | | | | | | | |
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ 
                & & & & & & & & & & & & & & &
                | | | | | | | | | | | | | | |
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ 
                & & & & & & & & & & & & & & &
                | | | | | | | | | | | | | | |
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ 
                & & & & & & & & & & & & & & &
                | | | | | | | | | | | | | | |
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ 
                & & & & & & & & & & & & & & &
                | | | | | | | | | | | | | | |
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ 

            PUV: 
                1 1 0 1 0 0 1 1 1 0 0 1 1 0 1

            LENGTH1: 4
            RADIUS1: 3.14
            EPSILON: 1e-2
            AREA1: 4

            Q_PTS: 5
            QUADS: 123

            DIST: 14.3

            N_PTS: 1
            K_PTS: 2
            A_PTS: 3
            B_PTS: 4
            C_PTS: 5
            D_PTS: 6
            E_PTS: 7
            F_PTS: 8
            G_PTS: 9

            LENGTH2: 14.2
            RADIUS2: 13.2
            AREA2: 666
        """;

        Input input = Input.parse(text);

        assertEquals(3, input.points.length);
        assertEquals(1, input.points[0].x);
        assertEquals(2, input.points[0].y);
        assertEquals(3, input.points[1].x);
        assertEquals(4, input.points[1].y);
        assertEquals(5, input.points[2].x);
        assertEquals(6, input.points[2].y);

        for (int row = 0; row < 15; row++) {
            LogicalOperator op = (row % 3 == 0 ? LogicalOperator.AND : (row % 3 == 1 ? LogicalOperator.OR : LogicalOperator.NOT_USED));
            for (int col = 0; col < 15; col++) {
                assertEquals(op, input.lcm[row][col]);
            }
        }

        boolean[] puv_expected = new boolean[]{
            true, true, false, true, false, false, true, true, true, false, false, true, true, false, true
        };
        for (int i = 0; i < 15; i++) {
            assertEquals(puv_expected[i], input.puv[i]);
        }

        assertEquals(4, input.parameters.length1, 1e-6);
        assertEquals(3.14, input.parameters.radius1, 1e-6);
        assertEquals(1e-2, input.parameters.epsilon1, 1e-6);
        assertEquals(4, input.parameters.area, 1e-6);

        assertEquals(5, input.parameters.q_points);
        assertEquals(123, input.parameters.quads);

        assertEquals(14.3, input.parameters.dist, 1e-6);

        assertEquals(1, input.parameters.n_points);
        assertEquals(2, input.parameters.k_points);
        assertEquals(3, input.parameters.a_points);
        assertEquals(4, input.parameters.b_points);
        assertEquals(5, input.parameters.c_points);
        assertEquals(6, input.parameters.d_points);
        assertEquals(7, input.parameters.e_points);
        assertEquals(8, input.parameters.f_points);
        assertEquals(9, input.parameters.g_points);
        
        assertEquals(14.2, input.parameters.length2, 1e-6);
        assertEquals(13.2, input.parameters.radius2, 1e-6);
        assertEquals(666, input.parameters.area2, 1e-6);
    }
}
