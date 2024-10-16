import java.util.Scanner;

public class GaussElimination {

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

        double[][] rowEchelonForm = gaussElimination(augmentedMatrix);

        System.out.println("\nMatriks setelah eliminasi Gauss:");
        printMatrix(rowEchelonForm);

        boolean hasUniqueSolution = true;
        for (int i = 0; i < n; i++) {
            if (rowEchelonForm[i][i] == 0) {
                hasUniqueSolution = false;
                break;
            }
        }

        if (hasUniqueSolution) {
            double[] solutions = backSubstitution(rowEchelonForm);
            System.out.println("\nHasil SPL:");
            for (int i = 0; i < n; i++) {
                System.out.printf("x%d = %.2f\n", i + 1, solutions[i]);
            }
        } else {
            System.out.println("\nMatriks adalah singular atau tidak memiliki solusi unik.");
        }

        scanner.close();
    }

    public static double[][] gaussElimination(double[][] matrix) {
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

            // Eliminasi Gauss di bawah pivot
            for (int j = i + 1; j < rows; j++) {
                double factor = matrix[j][i] / matrix[i][i];
                for (int k = i; k < cols; k++) {
                    matrix[j][k] -= factor * matrix[i][k];
                }
            }
        }

        return matrix;
    }

    public static double[] backSubstitution(double[][] matrix) {
        int n = matrix.length;
        double[] solutions = new double[n];

        for (int i = n - 1; i >= 0; i--) {
            solutions[i] = matrix[i][n];
            for (int j = i + 1; j < n; j++) {
                solutions[i] -= matrix[i][j] * solutions[j];
            }
            solutions[i] /= matrix[i][i];
        }

        return solutions;
    }

    public static int calculateRank(double[][] matrix) {
        int rank = 0;
        int rows = matrix.length;
        int cols = matrix[0].length - 1;

        for (int i = 0; i < rows; i++) {
            boolean nonZeroRow = false;
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != 0) {
                    nonZeroRow = true;
                    break;
                }
            }
            if (nonZeroRow) {
                rank++;
            }
        }

        return rank;
    }

    public static boolean hasInconsistentSystem(double[][] matrix, int rank) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = rank; i < rows; i++) {
            boolean allZeros = true;
            for (int j = 0; j < cols - 1; j++) {
                if (matrix[i][j] != 0) {
                    allZeros = false;
                    break;
                }
            }
            if (allZeros && matrix[i][cols - 1] != 0) {
                return true;
            }
        }

        return false;
    }

    public static void printParametricSolution(double[][] matrix, int rank) {
        int n = matrix.length;
        String[] solutions = new String[n];
        boolean[] isFreeVariable = new boolean[n];  // Untuk menandai variabel bebas
    
        // Inisialisasi solusi parametrik
        for (int i = rank - 1; i >= 0; i--) {
            if (matrix[i][i] != 0) {
                StringBuilder solution = new StringBuilder(String.format("%.2f", matrix[i][n]));
    
                for (int j = i + 1; j < n; j++) {
                    if (!isFreeVariable[j]) {
                        solution.append(String.format(" - %.2fx%d", matrix[i][j], j + 1));
                    }
                }
    
                solutions[i] = String.format("(%s) / %.2f", solution.toString(), matrix[i][i]);
            } else {
                isFreeVariable[i] = true;  // Menandai sebagai variabel bebas
                solutions[i] = "p" + (i + 1);  // Variabel bebas ditandai sebagai p1, p2, dst.
            }
        }
    
        // Variabel bebas sebagai parameter
        for (int i = rank; i < n; i++) {
            solutions[i] = "p" + (i + 1);
        }
    
        // Cetak solusi parametrik
        System.out.println("Solusi parametrik:");
        for (int i = 0; i < n; i++) {
            System.out.printf("x%d = %s\n", i + 1, solutions[i]);
        }
    }
    

    

    private static void swapRows(double[][] matrix, int row1, int row2) {
        double[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }

    public static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double element : row) {
                System.out.printf("%.2f\t", element);
            }
            System.out.println();
        }
    }
}
