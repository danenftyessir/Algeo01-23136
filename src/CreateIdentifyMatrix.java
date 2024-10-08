import java.util.Scanner;

public class CreateIdentifyMatrix {
    public static void createAndAnalyzeMatrix() {
        Scanner scanner = new Scanner(System.in);
        int rows, cols;
        do {
            System.out.print("Masukkan jumlah baris (minimal 2): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
                scanner.next();
            }
            rows = scanner.nextInt();
            if (rows < 2) {
                System.out.println("Jumlah baris harus minimal 2.");
            }
        } while (rows < 2);
        do {
            System.out.print("Masukkan jumlah kolom (minimal 2): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
                scanner.next();
            }
            cols = scanner.nextInt();
            if (cols < 2) {
                System.out.println("Jumlah kolom harus minimal 2.");
            }
        } while (cols < 2);

        int[][] matrix = new int[rows][cols];

        System.out.println("\nMasukkan elemen-elemen matriks:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("Matriks[%d][%d]: ", i, j);
                while (!scanner.hasNextInt()) {
                    System.out.println("Input tidak valid. Harap masukkan angka.");
                    System.out.printf("Matriks[%d][%d]: ", i, j);
                    scanner.next();
                }
                matrix[i][j] = scanner.nextInt();
            }
        }

        System.out.println("\nMatriks yang dimasukkan:");
        printMatrix(matrix);

        identifyMatrix(matrix);

        scanner.close();
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int elem : row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }
    }

    public static void identifyMatrix(int[][] matrix) {
        boolean isZeroMatrix = true;
        boolean isIdentityMatrix = true;
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != 0) {
                    isZeroMatrix = false;
                }
                if (i == j && matrix[i][j] != 1) {
                    isIdentityMatrix = false;
                }
                if (i != j && matrix[i][j] != 0) {
                    isIdentityMatrix = false;
                }
            }
        }

        if (isZeroMatrix) {
            System.out.println("\nMatriks ini adalah matriks nol.");
        } else if (isIdentityMatrix && rows == cols) {
            System.out.println("\nMatriks ini adalah matriks identitas.");
        } else {
            System.out.println("\nMatriks ini bukan matriks nol atau matriks identitas.");
        }
    }
