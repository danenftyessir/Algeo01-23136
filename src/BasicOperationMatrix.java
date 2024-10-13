public class BasicOperationMatrix {
    // Fungsi untuk melakukan transpose matriks
    public static double[][] transposeMatrix(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] transposed = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    // Fungsi untuk menghitung trace matriks (jumlah elemen diagonal utama)
    public static int traceMatrix(int[][] matrix) {
        if (matrix.length != matrix[0].length) {
            throw new IllegalArgumentException("Matriks harus simetris/persegi untuk menghitung trace matriks tersebut!");
        }
        int trace = 0;
        for (int i = 0; i < matrix.length; i++) {
            trace += matrix[i][i];
        }
        return trace;
    }

    // Fungsi untuk menjumlahkan dua matriks
    public static int[][] addMatrices(int[][] matrix1, int[][] matrix2) {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            throw new IllegalArgumentException("Kedua matriks harus memiliki ukuran yang sama");
        }
        int rows = matrix1.length;
        int cols = matrix1[0].length;
        int[][] result = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return result;
    }

    // Fungsi untuk mengalikan dua matriks
    public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        if (matrix1[0].length != matrix2.length) {
            throw new IllegalArgumentException("Jumlah kolom matriks pertama harus sama dengan jumlah baris matriks kedua");
        }
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;
        int[][] result = new int[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }
}
