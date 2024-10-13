import java.util.Scanner;

public class CramerRule {
    public static double[] solveUsingCramer(double[][] coefficients, double[] constants) {
        int n = coefficients.length;
        double determinant = calculateDeterminant(coefficients);
        if (determinant == 0) {
            return null; // Matriks koefisien singular, tidak ada solusi unik
        }
        double[] solution = new double[n];
        for (int i = 0; i < n; i++) {
            double[][] modifiedMatrix = modifyMatrix(coefficients, constants, i);
            double modifiedDeterminant = calculateDeterminant(modifiedMatrix);
            solution[i] = modifiedDeterminant / determinant;
        }
        return solution;
    }

    // Menghitung determinan dari matriks persegi menggunakan metode rekursif
    public static double calculateDeterminant(double[][] matrix) {
        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0];
        }

        if (n == 2) { // Basis: determinan matriks 2x2
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        double determinant = 0;
        for (int i = 0; i < n; i++) {
            double[][] subMatrix = new double[n - 1][n - 1];
            for (int row = 1; row < n; row++) {
                for (int col = 0, subCol = 0; col < n; col++) {
                    if (col == i) {
                        continue;
                    }
                    subMatrix[row - 1][subCol++] = matrix[row][col];
                }
            }
            double subDeterminant = calculateDeterminant(subMatrix);
            if (i % 2 == 0) {
                determinant += matrix[0][i] * subDeterminant;
            } else {
                determinant -= matrix[0][i] * subDeterminant;
            }
        }
        return determinant;
    }

    // Mengganti kolom ke-'column' dari matriks koefisien dengan konstanta
    public static double[][] modifyMatrix(double[][] coefficients, double[] constants, int column) {
        int n = coefficients.length;
        double[][] modifiedMatrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == column) {
                    modifiedMatrix[i][j] = constants[i]; // Ganti kolom ke-'column' dengan konstanta
                } else {
                    modifiedMatrix[i][j] = coefficients[i][j];
                }
            }
        }

        return modifiedMatrix;
    }

    public static void displaySolution(double[] solution) {
        if (solution == null) {
            System.out.println("\nSPL tidak memiliki solusi unik (matriks koefisien singular).");
        } else {
            System.out.println("\nSolusi SPL:");
            for (int i = 0; i < solution.length; i++) {
                System.out.printf("x%d = %.4f\n", i + 1, solution[i]);
            }
        }
    }

    public static double[][] inputCoefficients(Scanner scanner, int n) {
        double[][] coefficients = new double[n][n];
        System.out.println("Masukkan koefisien untuk setiap persamaan:");
        for (int i = 0; i < n; i++) {
            System.out.printf("Persamaan %d:\n", i + 1);
            for (int j = 0; j < n; j++) {
                System.out.printf("Masukkan koefisien x%d: ", j + 1);
                coefficients[i][j] = scanner.nextDouble();
            }
        }
        return coefficients;
    }

    public static double[] inputConstants(Scanner scanner, int n) {
        double[] constants = new double[n];
        System.out.println("Masukkan konstanta untuk setiap persamaan:");
        for (int i = 0; i < n; i++) {
            System.out.printf("Masukkan konstanta untuk persamaan %d: ", i + 1);
            constants[i] = scanner.nextDouble();
        }
        return constants;
    }
}
