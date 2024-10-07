package optimisation.assignment.domain;
import java.util.Scanner;

public class SimplexSimple {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("A vector of coefficients of objective function - C: ");
        String cInput = scanner.nextLine();
        String[] coeffStrings = cInput.split(" ");
        double[] c = new double[coeffStrings.length];
        for (int i = 0; i < coeffStrings.length; i++) {
            c[i] = Double.parseDouble(coeffStrings[i]);
        }

        System.out.print("A matrix of coefficients of constraint function - A: ");
        int m = Integer.parseInt(scanner.nextLine());
        double[][] a = new double[m][c.length];
        for (int i = 0; i < m; i++) {
            System.out.print("Enter coefficients for constraint " + (i + 1) + ": ");
            String[] termStrings = scanner.nextLine().split(" ");  
            for (int j = 0; j < termStrings.length; j++) {
                a[i][j] = Double.parseDouble(termStrings[j]);
            }
        }

        System.out.print("A vector of right-hand side numbers - b: ");
        String bInput = scanner.nextLine();
        String[] sumStrings = bInput.split(" ");  
        double[] b = new double[sumStrings.length];
        for (int i = 0; i < sumStrings.length; i++) {
            b[i] = Double.parseDouble(sumStrings[i]);
        }

        SimplexResult result = simplex(a, c, b);
        if (result.isUnbounded) {
            System.out.println("The method is not applicable!");
        } else {
            System.out.println("A vector of decision variables - x*: " + arrayToString(result.x));
            System.out.println("Maximum (minimum) value of the objective function: " + result.value);
        }
    }

    public static SimplexResult simplex(double[][] a, double[] c, double[] b) {
        int m = a.length;
        int n = a[0].length;
        double[][] table = new double[m + 1][n + m + 1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                table[i][j] = a[i][j];
            }
            table[i][n + i] = 1; 
            table[i][n + m] = b[i];
        }

        for (int j = 0; j < n; j++) {
            table[m][j] = -c[j];
        }

        while (hasNegative(table[m])) {
            int enteringCol = findEnteringVariable(table[m]);
            int leavingRow = findLeavingVariable(table, enteringCol);
            if (leavingRow == -1) {
                return new SimplexResult(true, null, 0);
            }
            pivot(table, enteringCol, leavingRow);
        }

        double[] x = new double[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (table[i][j] == 1) {
                    x[j] = table[i][n + m];
                }
            }
        }

        return new SimplexResult(false, x, table[m][n + m]);
    }

    private static boolean hasNegative(double[] row) {
        for (double el : row) {
            if (el < 0) {
                return true;
            }
        }
        return false;
    }

    private static int findEnteringVariable(double[] row) {
        int index = 0;
        for (int i = 1; i < row.length; i++) {
            if (row[i] < row[index]) {
                index = i;
            }
        }
        return index;
    }

    private static int findLeavingVariable(double[][] table, int enteringCol) {
        int leavingRow = -1;
        double minRatio = 10000000000.0;
        for (int i = 0; i < table.length - 1; i++) {
            if (table[i][enteringCol] > 0) {
                double ratio = table[i][table[i].length - 1] / table[i][enteringCol];
                if (ratio < minRatio) {
                    minRatio = ratio;
                    leavingRow = i;
                }
            }
        }
        return leavingRow;
    }

    private static void pivot(double[][] table, int enteringCol, int leavingRow) {
        double pivotValue = table[leavingRow][enteringCol];
        for (int j = 0; j < table[leavingRow].length; j++) {
            table[leavingRow][j] /= pivotValue;
        }
        for (int i = 0; i < table.length; i++) {
            if (i != leavingRow) {
                double factor = table[i][enteringCol];
                for (int j = 0; j < table[i].length; j++) {
                    table[i][j] -= table[leavingRow][j] * factor;
                }
            }
        }
    }

    private static String arrayToString(double[] array) {
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

    static class SimplexResult {
        boolean isUnbounded;
        double[] x;
        double value;

        SimplexResult(boolean isUnbounded, double[] x, double value) {
            this.isUnbounded = isUnbounded;
            this.x = x;
            this.value = value;
        }
    }
}