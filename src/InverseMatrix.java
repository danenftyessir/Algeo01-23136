public class InverseMatrix {

    public static double[] solveLinearEquation(double[][] coefficients, double[] constants) {
        double[][] inverse = matrixInverse(coefficients);
        if (inverse == null) {
            return null; // Matriks singular
        }
        double[] solution = new double[constants.length];
        // Mengalikan matriks balikan dengan vektor konstanta
        for (int i = 0; i < inverse.length; i++) {
            for (int j = 0; j < inverse[0].length; j++) {
                solution[i] += inverse[i][j] * constants[j];
            }
        }
        return solution;
    }

    public static double[][] matrixInverse(double[][] matrix) {
        int n = matrix.length;
        double[][] augmentedMatrix = new double[n][2 * n];
        // Membuat matriks augmented [A | I]
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = matrix[i][j];
                augmentedMatrix[i][j + n] = (i == j) ? 1 : 0;
            }
        }
        // Reduksi baris matriks augmented menjadi bentuk eselon
        for (int i = 0; i < n; i++) {
            double pivot = augmentedMatrix[i][i];
            if (pivot == 0) {
                return null; // Matriks singular
            }
            for (int j = 0; j < 2 * n; j++) {
                augmentedMatrix[i][j] /= pivot;
            }
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmentedMatrix[k][i];
                    for (int j = 0; j < 2 * n; j++) {
                        augmentedMatrix[k][j] -= factor * augmentedMatrix[i][j];
                    }
                }
            }
        }

        // Membuat matriks balikan dari bagian kanan matriks augmented [I | B^(-1)]
        double[][] inverseMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverseMatrix[i][j] = augmentedMatrix[i][j + n];
            }
        }
        return inverseMatrix;
    }

    public static double[][] matrixInverseAdjoint(double[][] matrix) {
        int n = matrix.length;
        double det = DeterminantCalculator.calculateDeterminant(matrix);
        if (det == 0) {
            return null; // Matriks singular tidak memiliki invers
        }
        // Hitung adjoin, yaitu transpos dari matriks kofaktor
        double[][] cofactorMatrix = cofactor(matrix);
        double[][] adjointMatrix = BasicOperationMatrix.transposeMatrix(cofactorMatrix);
        
        // Bagi matriks adjoin dengan determinan untuk mendapatkan invers
        double[][] inverseMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverseMatrix[i][j] = adjointMatrix[i][j] / det;
            }
        }
        return inverseMatrix;
    }

    public static double[][] cofactor(double[][] matrix) {
        int n = matrix.length;
        double[][] cofactorMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cofactorMatrix[i][j] = Math.pow(-1, i + j) * DeterminantCalculator.calculateDeterminant(minor(matrix, i, j));
            }
        }
        return cofactorMatrix;
    }

    public static double[][] minor(double[][] matrix, int row, int col) {
        int n = matrix.length;
        double[][] minor = new double[n - 1][n - 1];
        int r = 0;
        for (int i = 0; i < n; i++) {
            if (i == row) continue; // Lewati baris yang dihapus
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == col) continue; // Lewati kolom yang dihapus
                minor[r][c] = matrix[i][j];
                c++;
            }
            r++;
        }
        return minor;
    }
}
