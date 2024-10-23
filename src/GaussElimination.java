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
        int i = 0; // Baris
        int j = 0; // Kolom
    
        while (i < rows && j < cols) {
            // Jika elemen pivot (matrix[i][j]) adalah 0, coba tukar dengan baris di bawahnya
            if (matrix[i][j] == 0) {
                boolean swapped = false;
                for (int k = i + 1; k < rows; k++) {
                    if (matrix[k][j] != 0) {
                        swapRows(matrix, i, k);
                        swapped = true;
                        break;
                    }
                }
                // Jika tidak ada baris yang bisa ditukar, lanjutkan ke kolom berikutnya
                if (!swapped) {
                    j++;
                    continue;
                }
            }
    
            // Bagi seluruh baris dengan nilai pivot untuk memastikan pivot menjadi 1
            double pivot = matrix[i][j];
            for (int k = j; k < cols; k++) {
                matrix[i][k] /= pivot;
            }
    
            // Lakukan eliminasi Gauss di bawah pivot
            for (int k = i + 1; k < rows; k++) {
                double factor = matrix[k][j];
                for (int l = j; l < cols; l++) {
                    matrix[k][l] -= factor * matrix[i][l];
                }
            }
    
            // Lanjutkan ke baris dan kolom berikutnya
            i++;
            j++;
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
    

        public static String[] parametricSolutions(double[][] matrix, int rank) {
        int rows = matrix.length;
        int cols = matrix[0].length - 1; // Jumlah variabel (tidak termasuk kolom hasil)
        
        boolean[] isPivotColumn = new boolean[cols]; // Menandai kolom pivot
        String[] solutions = new String[rows];
        
        // Tandai kolom mana yang merupakan pivot
        for (int i = 0; i < rank; i++) {
            for (int j = 0; j < cols; j++) {
            if (matrix[i][j] != 0) {
                isPivotColumn[j] = true;
                break;
            }
            }
        }
        
        // Inisialisasi solusi sebagai string
        for (int i = 0; i < cols; i++) {
            if (!isPivotColumn[i]) {
            // Variabel bebas
            solutions[i] = "x" + (i + 1) + " = t" + (i + 1) + " (variabel bebas)";
            }
        }
        
        // Proses solusi untuk variabel dasar
        for (int i = rank - 1; i >= 0; i--) {
            int pivotCol = -1;
            for (int j = 0; j < cols; j++) {
            if (matrix[i][j] != 0) {
                pivotCol = j;
                break;
            }
            }
        
            if (pivotCol != -1) {
            StringBuilder solution = new StringBuilder("x" + (pivotCol + 1) + " = " + matrix[i][cols]); // Kolom hasil
            // Mengurangi kontribusi variabel bebas dari solusi
            for (int j = pivotCol + 1; j < cols; j++) {
                if (matrix[i][j] != 0) {
                    solution.append(" + ").append(-matrix[i][j]).append("*t").append(j + 1);
                }
            }
            solutions[pivotCol] = solution.toString();
            }
        }
        
        return solutions;
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
