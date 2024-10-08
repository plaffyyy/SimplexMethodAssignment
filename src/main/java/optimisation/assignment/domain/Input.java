package optimisation.assignment.domain;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class Input {
    private double[] c;
    private BufferedReader reader;
    private PrintStream out;
    public Input(InputStream in, PrintStream out) {
        this.reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.out = out;
    }
    public double[] inputObjectiveFunction() throws IOException {
        out.print("A vector of coefficients of objective function - C: ");
        String[] coeffStrings = reader.readLine().split(" ");
        double[] c = new double[coeffStrings.length];
        for (int i = 0; i < coeffStrings.length; i++) {
            c[i] = Double.parseDouble(coeffStrings[i]);
        }
        this.c = c;
        return c;
    }

    public double[][] inputConstrainFunction() throws IOException {
        out.print("Amount of rows in a matrix of coefficients of constrain function - A: ");
        int m = Integer.parseInt(reader.readLine());
        double[][] a = new double[m][this.c.length];
        for (int i = 0; i < m; i++) {
            out.print("Enter coefficients for constraint " + (i + 1) + ": ");
            String[] termStrings = reader.readLine().split(" ");
            for (int j = 0; j < termStrings.length; j++) {
                a[i][j] = Double.parseDouble(termStrings[j]);
            }
        }
        return a;
    }

    public double[] inputRightHandSide() throws IOException {
        out.print("A vector of right-hand side numbers - b: ");
        String[] sumStrings = reader.readLine().split(" ");
        double[] b = new double[sumStrings.length];
        for (int i = 0; i < sumStrings.length; i++) {
            b[i] = Double.parseDouble(sumStrings[i]);
        }
        return b;
    }
}
