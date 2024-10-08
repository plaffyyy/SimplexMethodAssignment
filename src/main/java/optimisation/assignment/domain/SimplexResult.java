package optimisation.assignment.domain;

public final class SimplexResult {
    boolean isUnbounded;
    double[] x;
    double value;

    SimplexResult(boolean isUnbounded, double[] x, double value) {
        this.isUnbounded = isUnbounded;
        this.x = x;
        this.value = value;
    }
}
