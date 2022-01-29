package decide;

public class ConditionsMetVector {
    // Launch Interceptor Conditions
    public boolean[] conditions;

    ConditionsMetVector(Input input) {
        this.conditions = new boolean[15];
    }

    // Sets conditions[1] = true iff 3 consecutive points *cannot be contained within circle of radius "radius1"
    public boolean LIC1(Input input) {
        // for (int i = 0; i < 98; i++) {
        // Variable length input for more efficient testing
        for (int i = 0; i < input.points.length - 2; i++) {
            Point first = input.points[i];
            Point second = input.points[i + 1];
            Point third = input.points[i + 2];
            
            // Optimization prerequisites
            double length1 = first.distance(second);
            double length2 = second.distance(third);
            double length3 = third.distance(first);

            // Optimization 1:
            // If any of the points are further appart than 2*radius1, the points can't fit in the circle
            if (length1 > 2 * input.parameters.radius1 
                || length2 > 2 * input.parameters.radius1 
                || length3 > 2 * input.parameters.radius1) {
                this.conditions[1] = true;
                return true;
            }

            // Optimization 2:
            // If the circumference of the triangle is larger than: 3 * sqrt(3 * radius1^2), the points can't fit in the circle
            // See here for explanation: https://1drv.ms/u/s!Ap4Pha57tInRiZpVrpTWePByWzrrhw?e=B9XD6y
            if (length1 + length2 + length3 > 3 * Math.sqrt(3 * input.parameters.radius1 * input.parameters.radius1)) {
                this.conditions[1] = true;
                return true;
            }

            // Angular sweep solution, inspired by: https://www.geeksforgeeks.org/angular-sweep-maximum-points-can-enclosed-circle-given-radius/
            // For all but one case (handled by optimization 2), only two points are of interest. Imagine a circle of radius "radius1"
            // with one of the two points on it's circumference, then for some span of an angle where the circle is rotated about the 
            // point with respect to the x-axis, the other points are either within or outside the circle. If the spans for the other two points
            // overlap, the points can fit in the circle. This check needs only be done for two of the three considered points.
            
            // Checking point 1
            double A = Math.atan((first.y-second.y)/(first.x-second.x));
            double B = Math.acos(first.distance(second) / (2 * input.parameters.radius1));
            double alpha1 = A - B;
            double beta1 = A + B;

            A = Math.atan((first.y-third.y)/(first.x-third.x));
            B = Math.acos(first.distance(third) / (2 * input.parameters.radius1));
            double alpha2 = A - B;
            double beta2 = A + B;
            
            // If the spans don't overlap, we must check the second point
            if (alpha1 > beta2 || beta1 < alpha2) {
                // Checking point 2
                A = Math.atan((second.y-third.y)/(second.x-third.x));
                B = Math.acos(second.distance(third) / (2 * input.parameters.radius1));
                alpha1 = A - B;
                beta1 = A + B;

                A = Math.atan((second.y-first.y)/(second.x-first.x));
                B = Math.acos(second.distance(first) / (2 * input.parameters.radius1));
                alpha2 = A - B;
                beta2 = A + B;

                // If the spans don't overlap then the circle can't contain them
                if (alpha1 > beta2 || beta1 < alpha2) {
                    this.conditions[1] = true;
                    return true;
                }
            }
        }
        return false;
    }
}
