public class LinearRegression {
    double[] coefficients;

    static LinearRegression fit(double[][] X, double[] y) {
        int n = X.length;
        int d = X[0].length;

        double[][] design = new double[n][d + 1];
        for (int i = 0; i < n; i++) {
            design[i][0] = 1.0;
            for (int j = 0; j < d; j++) {
                design[i][j + 1] = X[i][j];
            }
        }

        double[][] Xt = transpose(design);
        double[][] XtX = multiply(Xt, design);
        double[] Xty = multiplyVector(Xt, y);

        LinearRegression model = new LinearRegression();
        model.coefficients = solve(XtX, Xty);
        return model;
    }

    double predict(double[] features) {
        double result = coefficients[0];
        for (int i = 0; i < features.length; i++) {
            result += coefficients[i + 1] * features[i];
        }
        return result;
    }

    static double[][] transpose(double[][] m) {
        int rows = m.length, cols = m[0].length;
        double[][] t = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                t[j][i] = m[i][j];
            }
        }
        return t;
    }

    static double[][] multiply(double[][] a, double[][] b) {
        int rows = a.length, cols = b[0].length, inner = b.length;
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double sum = 0;
                for (int k = 0; k < inner; k++) {
                    sum += a[i][k] * b[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    static double[] multiplyVector(double[][] a, double[] v) {
        int rows = a.length, cols = a[0].length;
        double[] result = new double[rows];
        for (int i = 0; i < rows; i++) {
            double sum = 0;
            for (int j = 0; j < cols; j++) {
                sum += a[i][j] * v[j];
            }
            result[i] = sum;
        }
        return result;
    }

    static double[] solve(double[][] A, double[] b) {
        int n = b.length;
        double[][] m = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i], 0, m[i], 0, n);
            m[i][n] = b[i];
        }

        for (int col = 0; col < n; col++) {
            int pivotRow = col;
            for (int row = col + 1; row < n; row++) {
                if (Math.abs(m[row][col]) > Math.abs(m[pivotRow][col])) {
                    pivotRow = row;
                }
            }
            double[] swap = m[col];
            m[col] = m[pivotRow];
            m[pivotRow] = swap;

            double pivotValue = m[col][col];
            for (int j = col; j <= n; j++) {
                m[col][j] /= pivotValue;
            }

            for (int row = 0; row < n; row++) {
                if (row == col) continue;
                double factor = m[row][col];
                for (int j = col; j <= n; j++) {
                    m[row][j] -= factor * m[col][j];
                }
            }
        }

        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = m[i][n];
        }
        return x;
    }
}
