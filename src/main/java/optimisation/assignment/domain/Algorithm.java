package optimisation.assignment.domain;

public final class Algorithm {
    private double[] c;
    private double[][] a;
    private double[] b;
    public Algorithm(double[] c, double[][] a, double[] b) {
        this.c = c;
        this.a = a;
        this.b = b;
    }
    public SimplexResult simplex(double[][] a, double[] c, double[] b) {
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

    private boolean hasNegative(double[] row) {
        for (double el : row) {
            if (el < 0) {
                return true;
            }
        }
        return false;
    }

    private int findEnteringVariable(double[] row) {
        int index = 0;
        for (int i = 1; i < row.length; i++) {
            if (row[i] < row[index]) {
                index = i;
            }
        }
        return index;
    }

    private int findLeavingVariable(double[][] table, int enteringCol) {
        int leavingRow = -1;
        double minRatio = Double.MAX_VALUE;
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

    private void pivot(double[][] table, int enteringCol, int leavingRow) {
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
}