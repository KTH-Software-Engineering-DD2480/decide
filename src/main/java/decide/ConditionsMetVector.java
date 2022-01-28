package decide;

import java.util.Arrays;
import java.lang.Math;

public class ConditionsMetVector {
    // Launch Interceptor Conditions
    public boolean[] conditions;

    ConditionsMetVector(Input input) {
        this.conditions = new boolean[15];
        this.conditions[4] = LIC4(input);
    }

    // LIC 4
    // Returns true if there are `Q` (read from input) consecutive data points
    // that lie in more than `QUADS` (read from input) quadrants.
    public static boolean LIC4(Input input) {
        int sequence = input.parameters.q_points;
        int required_quads = input.parameters.quads;

        if (sequence > input.points.length) return false;

        // for each quadrant, how many points are in that quadrant
        int[] occurances = new int[4];

        // Count the number of quadrants in a sliding window
        for (int window = 0; window < input.points.length; window++) {
            // remove the previous quadrant if the window size exceeds the number of consecutive points
            if (window >= sequence) occurances[quadrant(input.points[window])] -= 1;
            // add the next quadrant
            occurances[quadrant(input.points[window])] += 1;

            // count the number of quadrants that have at least one point
            int num_quads = 0;
            for (int i = 0; i < 4; i++) if (occurances[i] > 0) num_quads += 1;
            if (num_quads > required_quads) return true;
        }

        return false;
    }

    // Given a point, returns the quadrant it is in
    // 1 | 0
    // --+--
    // 2 | 3
    static int quadrant(Point point) {
        if (point.x >= 0 && point.y >= 0) return 0;
        if (point.x <= 0 && point.y >= 0) return 1;
        if (point.x <= 0 && point.y <= 0) return 2;
        return 3;
    }

    // LIC 5 
    // Returns true if there exists at least one set of two consecutive data points, (X[i],Y[i]) and (X[j],Y[j]), such
    // that X[j] - X[i] < 0. (where i = j-1)   
    public static boolean LIC5(Input input){
        for (int i = 0; i < input.points.length - 1; i++) {
            if(input.points[i+1].x < input.points[i].x){
                return true;
            }
        }
        return false;
    }

    // LIC 6
    // Returns true if there exists at least one set of N_PTS consecutive data points such that at least one of the points lies a distance greater 
    // than DIST from the line joining the first and last of these N_PTS points. If the first and last points of these N_PTS are identical, 
    // then the calculated distance to compare with DIST will be the distance from the coincident point to all other points of the N_PTS consecutive points. 
    // The condition is not met when NUMPOINTS < 3.
    public static boolean LIC6(Input input){

        // Base case if there are < 3 points
        if(input.points.length < 3) return false;

        // inputs to the function
        int n_pts = input.parameters.n_points;
        double dist = input.parameters.dist;
        int numPoints = input.points.length;

        // an array to store the consecutive data points
        Point[] consecutive;

        for (int i = 0; i < numPoints - (n_pts - 1); i++) {
            // define the set of consecutive data points 
            consecutive = Arrays.copyOfRange(input.points, i, i+n_pts);

            // if the first and last points are identical
            if(consecutive[0].x == consecutive[consecutive.length-1].x && consecutive[0].y == consecutive[consecutive.length-1].y){
                // compare the distance from each point to the starting point
                for(Point point : consecutive){
                    // compute the euclidena distance and compare with dist
                    if(Math.sqrt(Math.pow((consecutive[0].x-point.x),2) + Math.pow((consecutive[0].y-point.y),2)) > dist) return true;
                    
                }
            }

            // if the first and last points are different
            else{
                
                // compute the distance from each point to the line defined by the first and the last point
                for(Point point : consecutive){
                    if(distance(consecutive[0], consecutive[consecutive.length-1], point) > dist) return true;
                }
            }
        }
        return false;
    }

    // Computes the distance between a line, defined by start and end, and the point p.
    static double distance(Point start, Point end, Point p){
        
        // The numerator of the formula is |a(p_x) + b(p_y) + c| with the line ax + by + c = 0
        double a = start.y - end.y;
        double b = end.x - start.x;
        double c = (start.x - end.x) * start.y + (end.y - start.y)*start.x;
        double numerator = Math.abs(a*p.x + b*p.y + c);

        // The denominator is sqrt(a^2 + b^2)
        double denominator = Math.sqrt(Math.pow(a,2) + Math.pow(b, 2));

        return numerator/denominator;
    }
}
