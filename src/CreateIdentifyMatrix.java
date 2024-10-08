import java.util.Scanner;

public class CreateIdentifyMatrix {
    public static void createAndAnalyzeMatrix() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan jumlah baris: ");
        int rows = scanner.nextInt();
        System.out.print("Masukkan jumlah kolom: ");
        int cols = scanner.nextInt();
        int[][] matrix = new int[rows][cols];
        System.out.println("\nMasukkan elemen-elemen matriks:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("Matriks[%d][%d]: ", i, j);
                matrix[i][j] = scanner.nextInt();
            }
        }
        System.out.println("\nMatriks yang dimasukkan:");
        printMatrix(matrix);
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
}
