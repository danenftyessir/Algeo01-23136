public class DeterminantCalculator {

    public static double calculateDeterminant(double[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matriks tidak boleh null atau kosong.");
        }
        if (!isSquareMatrix(matrix)) {
            throw new IllegalArgumentException("Matriks harus persegi untuk menghitung determinan.");
        }

        if (isUpperTriangular(matrix) || isLowerTriangular(matrix)) {
            return calculateTriangularDeterminant(matrix);
        }

        return calculateDeterminantByRowReduction(matrix);
    }

    private static boolean isSquareMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            if (row.length != matrix.length) return false;
        }
        return true;
    }

    private static boolean isUpperTriangular(double[][] matrix) {
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 0; j < i; j++) {
                if (matrix[i][j] != 0) return false;
            }
        }
        return true;
    }

    private static boolean isLowerTriangular(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                if (matrix[i][j] != 0) return false;
            }
        }
        return true;
    }

    private static double calculateTriangularDeterminant(double[][] matrix) {
        double det = 1;
        for (int i = 0; i < matrix.length; i++) {
            det *= matrix[i][i];
        }
        return det;
    }

    private static double calculateDeterminantByRowReduction(double[][] matrix) {
        double[][] temp = copyMatrix(matrix);
        double det = 1;
        int swaps = 0;

        for (int i = 0; i < temp.length; i++) {
            int maxRow = findMaxPivotRow(temp, i);

            if (maxRow != i) {
                swapRows(temp, i, maxRow);
                swaps++;
            }

            if (temp[i][i] == 0) {
                return 0; // Matriks singular
            }

            for (int j = i + 1; j < temp.length; j++) {
                double factor = temp[j][i] / temp[i][i];
                for (int k = i; k < temp.length; k++) {
                    temp[j][k] -= factor * temp[i][k];
                }
            }
        }

        for (int i = 0; i < temp.length; i++) {
            det *= temp[i][i];
        }

        return (swaps % 2 == 0) ? det : -det;
    }

    private static int findMaxPivotRow(double[][] matrix, int col) {
        int maxRow = col;
        for (int i = col + 1; i < matrix.length; i++) {
            if (absoluteValue(matrix[i][col]) > absoluteValue(matrix[maxRow][col])) {
                maxRow = i;
            }
        }
        return maxRow;
    }

    private static void swapRows(double[][] matrix, int row1, int row2) {
        double[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }

    private static double[][] copyMatrix(double[][] matrix) {
        double[][] copy = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                copy[i][j] = matrix[i][j];
            }
        }
        return copy;
    }

    private static double absoluteValue(double value) {
        return value < 0 ? -value : value;
    }

    // Metode untuk matriks int (memanggil versi double)
    public static double calculateDeterminant(int[][] matrix) {
        return calculateDeterminant(convertToDoubleMatrix(matrix));
    }

    private static double[][] convertToDoubleMatrix(int[][] matrix) {
        double[][] doubleMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                doubleMatrix[i][j] = matrix[i][j];
            }
        }
        return doubleMatrix;
    }
}