import java.util.Scanner;

public class GaussJordanElimination {
    private int n;
    private double[][] augmentedMatrix;
    private Scanner scanner;
    public GaussJordanElimination() {
        scanner = new Scanner(System.in);
    }

    public void run() {
        inputData();
        processAndOutputResults();
        scanner.close();
    }
    
    private void inputData() {
        System.out.print("Masukkan jumlah variabel n: ");
        n = scanner.nextInt();
        System.out.println("Masukkan koefisien matriks augmented:");
        augmentedMatrix = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                System.out.printf("Masukkan elemen matriks[%d][%d]: ", i, j);
                augmentedMatrix[i][j] = scanner.nextDouble();
            }
        }
    }

    private void processAndOutputResults() {
        double[][] reducedRowEchelonForm = gaussJordanElimination(augmentedMatrix);
        System.out.println("\nMatriks eselon tereduksi:");
        printMatrix(reducedRowEchelonForm);  // Cetak matriks setelah tereduksi
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

    public static String[] parametricSolution(double[][] matrix, int rank) {
        int n = matrix[0].length - 1;  // Jumlah variabel (kolom matriks - 1 karena ada konstanta di kolom terakhir)
        String[] solutions = new String[n];
        boolean[] isFreeVariable = new boolean[n];  // Untuk menandai variabel bebas
    
        // Inisialisasi solusi parametrik
        for (int i = 0; i < rank; i++) {
            if (matrix[i][i] == 1) {  // Diagonal utama sudah 1 dalam bentuk Gauss-Jordan
                StringBuilder solution = new StringBuilder(String.format("%.2f", matrix[i][n])); // Mulai dengan konstanta
    
                for (int j = i + 1; j < n; j++) {
                    double coefficient = matrix[i][j];
                    if (coefficient != 0) {
                        if (coefficient < 0) {
                            solution.append(String.format(" + %.2fx%d", -coefficient, j + 1));
                        } else {
                            solution.append(String.format(" - %.2fx%d", coefficient, j + 1));
                        }
                    }
                }
    
                solutions[i] = solution.toString();
            } else {
                isFreeVariable[i] = true;  // Menandai sebagai variabel bebas
                solutions[i] = "p" + (i + 1);  // Variabel bebas ditandai sebagai p1, p2, dst.
            }
        }
    
        // Menandai variabel bebas yang tersisa sebagai parameter
        for (int i = rank; i < n; i++) {
            solutions[i] = "p" + (i + 1);
        }
    
        return solutions;
    }
    
    

    private static void swapRows(double[][] matrix, int row1, int row2) {
        double[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }

    private static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double element : row) {
                System.out.printf("%.2f\t", element);
            }
            System.out.println();
        }
    }
}
