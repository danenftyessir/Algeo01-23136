public class Regresi {
    // Melakukan regresi untuk Main Class
    public static double[] regresiLinear(double[][] data) {
        int n = data[0].length - 1;
        int m = data.length;
        double[][] X = new double[m][n + 1];
        double[] Y = new double[m];
        for (int i = 0; i < m; i++) {
            X[i][0] = 1;
            for (int j = 0; j < n; j++) {
                X[i][j + 1] = data[i][j];
            }
            Y[i] = data[i][n];
        }
        double[][] Xt = BasicOperationMatrix.transposeMatrix(X);
        double[][] XtX = BasicOperationMatrix.multiplyMatricesDouble(Xt, X);
        double[] XtY = multiplyMatrixVector(Xt, Y);
        double[] koefisien = solveSystem(XtX, XtY);
        return koefisien;
    }

    // Melakukan regresi kuadratik berganda
    public static double[] regresiKuadratik(double[][] data) {
        int n = data[0].length - 1;
        int m = data.length;
        int Count = n * (n - 1) / 2;
        int totalVars = 1 + n + n + Count;
        double[][] X = new double[m][totalVars];
        double[] Y = new double[m];
        for (int i = 0; i < m; i++) {
            int col = 0;
            X[i][col++] = 1;
            for (int j = 0; j < n; j++) {
                X[i][col++] = data[i][j];
            }
            for (int j = 0; j < n; j++) {
                X[i][col++] = data[i][j] * data[i][j];
            }
            for (int j = 0; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    X[i][col++] = data[i][j] * data[i][k];
                }
            } Y[i] = data[i][n];
        }
        double[][] Xt = BasicOperationMatrix.transposeMatrix(X);
        double[][] XtX = BasicOperationMatrix.multiplyMatricesDouble(Xt, X);
        double[] XtY = multiplyMatrixVector(Xt, Y);
        double[] koefisien = solveSystem(XtX, XtY);
        return koefisien;
    }

    // Mengalikan matriks dengan vektor
    private static double[] multiplyMatrixVector(double[][] a, double[] b) {
        int m = a.length;
        int n = a[0].length;
        double[] result = new double[m];
        for (int i = 0; i < m; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) {
                sum += a[i][j] * b[j];
            } result[i] = sum;
        } return result;
    }

    // Menyelesaikan sistem persamaan linier menggunakan eliminasi Gauss
    private static double[] solveSystem(double[][] A, double[] B) {
        int n = B.length;
        for (int p = 0; p < n; p++) {
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }
            double[] temp = A[p];
            A[p] = A[max];
            A[max] = temp;
            double t = B[p];
            B[p] = B[max];
            B[max] = t;
            if (A[p][p] == 0) {
                throw new ArithmeticException("Sistem persamaan tidak memiliki solusi unik.");
            }
            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                B[i] -= alpha * B[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }
        double[] solution = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = B[i];
            for (int j = i + 1; j < n; j++) {
                sum -= A[i][j] * solution[j];
            }
            solution[i] = sum / A[i][i];
        } return solution;
    }

    // Menghitung nilai Y berdasarkan koefisien dan nilai x yang diberikan
    public static double calculateY(double[] koefisien, double[] x, boolean isKuadratik) {
        double y = koefisien[0]; 
        int n = x.length;
        int index = 1;
        for (int i = 0; i < n; i++) {
            y += koefisien[index++] * x[i];
        }
        if (isKuadratik) {
            for (int i = 0; i < n; i++) {
                y += koefisien[index++] * x[i] * x[i];
            }
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    y += koefisien[index++] * x[i] * x[j];
                }
            }
        } return y;
    }
}