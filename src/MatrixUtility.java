import java.util.Scanner;

public class MatrixUtility {
    // Fungsi utama untuk membuat, menampilkan, dan menganalisis matriks
    public static void createIdentifyAndAnalyzeMatrix() {
        Scanner scanner = new Scanner(System.in);
        int[][] matrix = createMatrix(scanner);
        System.out.println("\nMatriks yang dimasukkan:");
        printMatrix(matrix);
        identifyMatrix(matrix);

        if (isSymmetric(matrix)) {
            System.out.println("\nMatriks ini adalah matriks simetris.");
        } else {
            System.out.println("\nMatriks ini bukan matriks simetris.");
        }
        scanner.close(); // Menutup scanner di sini
    }

    // Fungsi untuk membuat matriks
    public static int[][] createMatrix(Scanner scanner) {
        boolean useProvidedScanner = (scanner != null);
        if (!useProvidedScanner) {
            scanner = new Scanner(System.in);
        }

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

        if (!useProvidedScanner) {
            scanner.close(); // Menutup scanner hanya jika kita yang membuatnya
        }

        return matrix;
    }

    // Fungsi untuk menampilkan matriks
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int elem : row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }
    }

    // Fungsi untuk mengidentifikasi matriks nol atau matriks identitas
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

    // Fungsi untuk memeriksa apakah matriks simetris
    public static boolean isSymmetric(int[][] matrix) {
        if (matrix.length != matrix[0].length) {
            return false;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < i; j++) {
                if (matrix[i][j] != matrix[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }
}