package decide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.function.Consumer;

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

    public class Parameters {
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

    // Parse an `Input` object from a string of text.
    //
    // The input is made up of a number of key-value pairs: `key: value`.
    // Key-value pairs must be separated by whitespace (such as newlines, tabs
    // and spaces).
    // 
    // Here, `key` is a string starting with an alphabetic character (without
    // any whitespace) and `value` is some sequence of (floating point) numbers
    // or strings starting with non-alphabetic characters (separated by any
    // whitespace).
    // 
    // The keys will be named according to their counterparts in the
    // specification. For example:
    // 
    //      POINTS: 
    //          1 2
    //          3 4
    //          5 6
    //      LCM: 
    //          & & & & 
    //          | | | | 
    //          _ _ _ _ 
    //          & & & &
    //      PUV: 1 1 0 1 0 0 1 1 1 0 0
    // 
    //      LENGTH1: 4
    //      RADIUS1: 3.14
    //      QUADS: 123
    //      ...
    //
    // (note: matrices and vectors are shortened for illustration purposes, they
    // should really be 15x15)
    // 
    // `NUMPOINTS` is implicitly inferred as `POINTS.length / 2`.
    public static Input parse(String text) {
        InputParser parser = new InputParser(text);
        return parser.parseInput();
    }


    static class InputParser {
        StringTokenizer tokens;
        String next;

        InputParser(String text) {
            tokens = new StringTokenizer(text, ": \n\t\r\f");
        }

        Input parseInput() {
            Input input = new Input();
            input.parameters = input.new Parameters();
            input.lcm = new LogicalOperator[15][15];
            input.puv = new boolean[15];

            HashMap<String, Consumer<InputParser>> keyParsers = new HashMap<>();
            keyParsers.put("POINTS", parser -> input.points = parser.parsePoints());
            keyParsers.put("LCM", parser -> input.lcm = parser.parseLCM());
            keyParsers.put("PUV", parser -> input.puv = parser.parsePUV());
            keyParsers.put("LENGTH1", parser -> input.parameters.length1 = parser.parseNumber());
            keyParsers.put("RADIUS1", parser -> input.parameters.radius1 = parser.parseNumber());
            keyParsers.put("EPSILON", parser -> input.parameters.epsilon1 = parser.parseNumber());
            keyParsers.put("AREA1", parser -> input.parameters.area = parser.parseNumber());
            keyParsers.put("Q_PTS", parser -> input.parameters.q_points = parser.parseInteger());
            keyParsers.put("QUADS", parser -> input.parameters.quads = parser.parseInteger());
            keyParsers.put("DIST", parser -> input.parameters.dist = parser.parseNumber());
            keyParsers.put("N_PTS", parser -> input.parameters.n_points = parser.parseInteger());
            keyParsers.put("K_PTS", parser -> input.parameters.k_points = parser.parseInteger());
            keyParsers.put("A_PTS", parser -> input.parameters.a_points = parser.parseInteger());
            keyParsers.put("B_PTS", parser -> input.parameters.b_points = parser.parseInteger());
            keyParsers.put("C_PTS", parser -> input.parameters.c_points = parser.parseInteger());
            keyParsers.put("D_PTS", parser -> input.parameters.d_points = parser.parseInteger());
            keyParsers.put("E_PTS", parser -> input.parameters.e_points = parser.parseInteger());
            keyParsers.put("F_PTS", parser -> input.parameters.f_points = parser.parseInteger());
            keyParsers.put("G_PTS", parser -> input.parameters.g_points = parser.parseInteger());
            keyParsers.put("LENGTH2", parser -> input.parameters.length2 = parser.parseNumber());
            keyParsers.put("RADIUS2", parser -> input.parameters.radius2 = parser.parseNumber());
            keyParsers.put("AREA2", parser -> input.parameters.area2 = parser.parseNumber());

            HashSet<String> usedKeys = new HashSet<>();

            advance();
            while (next != null) {
                if (!isKey(next))
                    throw new RuntimeException("expected a key, found: " + next);
                
                String rawKey = next;
                String key = rawKey.toUpperCase();
                advance();

                Consumer<InputParser> parseFn = keyParsers.get(key);
                if (parseFn == null) throw new RuntimeException("unknown key: " + rawKey);
                if (usedKeys.contains(key)) throw new RuntimeException("key specified twice: " + rawKey);

                parseFn.accept(this);
                usedKeys.add(key);
            }

            // check if there was a missing key
            if (!usedKeys.containsAll(keyParsers.keySet())) {
                // sort the keys so that the first reported missing key is deterministic
                String[] keys = keyParsers.keySet().toArray(new String[keyParsers.size()]);
                Arrays.sort(keys);

                for (String key : keys) {
                    if (!usedKeys.contains(key)) {
                        throw new RuntimeException("missing key: " + key);
                    }
                }
            }

            return input;
        }

        // Store the next token in `next` (or null if at the end of file).
        void advance() {
            if (tokens.hasMoreTokens()) {
                next = tokens.nextToken();
            } else {
                next = null;
            }
        }

        // Check if the given string is a key token
        static boolean isKey(String token) {
            return Character.isAlphabetic(token.charAt(0));
        }

        Point[] parsePoints() {
            ArrayList<Point> points = new ArrayList<>();
            while (next != null) {
                if (isKey(next)) break;
                double x = parseNumber();
                double y = parseNumber();
                points.add(new Point(x, y));
            }
            return points.toArray(new Point[points.size()]);
        }

        LogicalOperator[][] parseLCM() {
            LogicalOperator[][] lcm = new LogicalOperator[15][15];
            for (int row = 0; row < 15; row++) {
                for (int col = 0; col < 15; col++) {
                    if (next == null) throw new RuntimeException("expected `&`, `|` or `_`, found EOF");
                    if (isKey(next)) throw new RuntimeException("expected `&`, `|` or `_`, found key");

                    LogicalOperator op;
                    if (next.equals("&")) op = LogicalOperator.AND;
                    else if (next.equals("|")) op = LogicalOperator.OR;
                    else if (next.equals("_")) op = LogicalOperator.NOT_USED;
                    else throw new RuntimeException("expected `&`, `|` or `_`, found: " + next);

                    lcm[row][col] = op;

                    advance();
                }
            }
            return lcm;
        }

        boolean[] parsePUV() {
            boolean[] puv = new boolean[15];
            for (int i = 0; i < 15; i++) {
                if (next == null) throw new RuntimeException("expected `1` or `0`, found EOF");
                if (isKey(next)) throw new RuntimeException("expected `1` or `0`, found key");

                boolean bool;
                if (next.equals("1")) bool = true;
                else if (next.equals("0")) bool = false;
                else throw new RuntimeException("expected `1` or `0`, found: " + next);

                puv[i] = bool;

                advance();
            }
            return puv;
        }

        double parseNumber() {
            if (next == null) throw new RuntimeException("expected number, found EOF");
            if (isKey(next)) throw new RuntimeException("expected number, found key");
            double value = Double.parseDouble(next);
            advance();
            return value;
        }

        int parseInteger() {
            // A double has 53-bit integer precision, so rounding should be safe for any valid integer input
            double raw = parseNumber();
            int integer = (int)raw;

            // we should be able to cast back to double without loss, otherwise we have a rounding error
            if ((double)integer != raw) throw new RuntimeException("number is not a 32-bit integer");

            return integer;
        }
    }
}
