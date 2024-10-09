import java.util.Scanner;

public class GaussJordanElimination {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan jumlah variabel n: ");
        int n = scanner.nextInt();

        System.out.println("Masukkan koefisien matriks augmented:");
        double[][] augmentedMatrix = new double[n][n + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                System.out.printf("Masukkan elemen matriks[%d][%d]: ", i, j);
                augmentedMatrix[i][j] = scanner.nextDouble();
            }
        }

        double[][] reducedRowEchelonForm = gaussJordanElimination(augmentedMatrix);

        boolean hasUniqueSolution = true;
        for (int i = 0; i < n; i++) {
            if (reducedRowEchelonForm[i][i] == 0) {
                hasUniqueSolution = false;
                break;
            }
        }

        if (hasUniqueSolution) {
            System.out.println("\nHasil SPL:");
            for (int i = 0; i < n; i++) {
                System.out.printf("x%d = %.2f\n", i + 1, reducedRowEchelonForm[i][n]);
            }
        } else {
            System.out.println("\nMatriks adalah singular atau tidak memiliki solusi unik.");
        }

        scanner.close();
    }


    public static double[][] gaussJordanElimination(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            if (matrix[i][i] == 0) {
                boolean swapped = false;
                for (int j = i + 1; j < rows; j++) {
                    if (matrix[j][i] != 0) {
                        swapRows(matrix, i, j);
                        swapped = true;
                        break;
                    }
                }
                if (!swapped) {
                    return matrix; // Singular matrix, no solution
                }
            }

            double pivot = matrix[i][i];
            for (int k = 0; k < cols; k++) {
                matrix[i][k] /= pivot;
            }

            for (int j = 0; j < rows; j++) {
                if (j != i) {
                    double factor = matrix[j][i];
                    for (int k = 0; k < cols; k++) {
                        matrix[j][k] -= factor * matrix[i][k];
                    }
                }
            }
        }

        return matrix;
    }

    private static void swapRows(double[][] matrix, int row1, int row2) {
        double[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }
}