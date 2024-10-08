package optimisation.assignment.domain;

import java.io.IOException;

public final class Picker {
    public static void main(String[] args) throws IOException {
        Input input = new Input(System.in, System.out);
        double[] c = input.inputObjectiveFunction();
        double[][] a = input.inputConstrainFunction();
        double[] b = input.inputRightHandSide();
        Algorithm algorithm = new Algorithm(c, a, b);
        SimplexResult result = algorithm.simplex(a, c, b);
        Output output = new Output(result, System.out);
        output.printResult();
    }
}
