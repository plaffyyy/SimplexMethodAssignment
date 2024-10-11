package optimisation.assignment.domain;

import java.io.PrintStream;

public final class Output {
    private SimplexResult result;
    private PrintStream out;

    public Output(SimplexResult result, PrintStream out) {
        this.result = result;
        this.out = out;
    }

    public void printResult() {
        if (this.result.isUnbounded) {
            out.println("The method is not applicable!");
        } else {
            out.println("A vector of decision variables - x*: " + arrayToString(result.x));
            out.println("Maximum (minimum) value of the objective function: " + result.value);
        }
    }

    private String arrayToString(double[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (double num : array) {
            sb.append(num).append(", ");
        }
        if (array.length > 0) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }
}
