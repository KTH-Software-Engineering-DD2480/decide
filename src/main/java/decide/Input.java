package decide;

// Input to the DECIDE routine
public class Input {
    // Coordinates of the data points
    public Point[] points;

    // Parameters for the LIC's
    public Parameters parameters;

    // Logical Connector Matrix
    public LogicalOperator[][] lcm;

    // Preliminary Unlocking Vector: decides if the given LIC should hold back launch
    public boolean[] puv;

    // Binary logical operators
    public enum LogicalOperator {
        // always `true`
        NOT_USED,
        // `true` iff both inputs are `true`
        AND,
        // `true` iff any of the inputs are `true`
        OR,
    }

    public static class Parameters {
        public double length1;     // Length in LICs 0, 7, 12
        public double radius1;     // Radius in LICs 1, 8, 13
        public double epsilon1;    // Deviation from PI in LICs 2, 9
        public double area;        // Area in LICs 3, 10, 14
        public int q_points;       // No. of consecutive points in LIC 4
        public int quads;          // No. of quadrants in LIC 4
        public double dist;        // Distance in LIC 6
        public int n_points;       // No. of consecutive pts . in LIC 6
        public int k_points;       // No. of int. pts. in LICs 7, 12
        public int a_points;       // No. of int. pts. in LICs 8, 13
        public int b_points;       // No. of int. pts. in LICs 8, 13
        public int c_points;       // No. of int. pts. in LIC 9
        public int d_points;       // No. of int. pts. in LIC 9
        public int e_points;       // No. of int. pts. in LICs 10, 14
        public int f_points;       // No. of int. pts. in LICs 10, 14
        public int g_points;       // No. of int. pts. in LIC 11
        public double length2;     // Maximum length in LIC 12
        public double radius2;     // Maximum radius in LIC 13
        public double area2;       // Maximum area in LIC 14
    }
}
