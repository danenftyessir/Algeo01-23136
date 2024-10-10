import java.util.Scanner;

public class InverseMatrix {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double[][] coefficients;
        double[] constants;

        System.out.print("Masukkan jumlah variabel (n): ");
        int n = scanner.nextInt();
        System.out.println("Masukkan koefisien dan konstanta untuk setiap persamaan:");
        coefficients = new double[n][n];
        constants = new double[n];
        for (int i = 0; i < n; i++) {
            System.out.printf("Persamaan %d:\n", i + 1);
            for (int j = 0; j < n; j++) {
                System.out.printf("Masukkan koefisien x%d: ", j + 1);
                coefficients[i][j] = scanner.nextDouble();
            }
            System.out.printf("Masukkan konstanta untuk persamaan %d: ", i + 1);
            constants[i] = scanner.nextDouble();
        }

        double[] solution = solveLinearEquation(coefficients, constants);

        if (solution != null) {
            System.out.println("\nSolusi SPL:");
            for (int i = 0; i < solution.length; i++) {
                System.out.printf("x%d = %.2f\n", i + 1, solution[i]);
            }
        } else {
            System.out.println("\nTidak ada solusi unik (matriks singular).");
        }

        scanner.close();
    }
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
}
