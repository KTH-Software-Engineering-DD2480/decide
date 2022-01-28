package decide;

public class Point {
    double x, y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Compute the distance between two points
    public double distance(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return length(dx, dy);
    }

    // Return the length of a vector
    private static double length(double x, double y) {
        return Math.sqrt(x*x + y*y);
    }

    // Return the angle (in radians) between the vectors `ba` and `bc`:
    //   b--------a
    //    \   
    //     \
    //      c
    public static double angle(Point a, Point b, Point c) {
        // `ba` vector
        double bax = a.x - b.x;
        double bay = a.y - b.y;
        double ba_length = length(bax, bay);

        // `bc` vector
        double bcx = c.x - b.x;
        double bcy = c.y - b.y;
        double bc_length = length(bcx, bcy);

        // ba . bc = |ba|*|bc| * cos(angle) 
        // => angle = acos((ba.bc) / |ba| / |bc|)
        double dot = bax*bcx + bay*bcy;
        double angle = Math.acos(dot / ba_length / bc_length);

        return angle;
    }

    // Returns the area of the triangle formed by `abc`
    public static double triangleArea(Point a, Point b, Point c) {
        // `ab` vector
        double abx = b.x - a.x;
        double aby = b.y - a.y;

        // `ac` vector
        double acx = c.x - a.x;
        double acy = c.y - a.y;

        // compute the (signed) area of the paralellogram spanned by `ab` and
        // `ac` by using the determinant
        double det = abx*acy - aby*acx;

        // The area of the triangle is half of the paralellogram
        return Math.abs(det / 2);
    }
}
