import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MatrixInputSystem {
    private double[][] matrix_det;
    public double[][] getMatrix() {
        return this.matrix_det;
    }
    public void inputMatrix() {
        System.out.println("=== Sistem Input Matriks Balikan ===");
        System.out.println("Pilih metode input: ");
        System.out.println("1. Input dari keyboard");
        System.out.println("2. Input dari file");
        System.out.print("Masukkan pilihan (1/2): ");
        int choice = readInt();
        
        if (choice == 1) {
            inputFromKeyboard();
        } else if (choice == 2) {
            inputFromFile();
        }
        
    }

    public void inputFromKeyboard() {
        System.out.print("Masukkan jumlah baris: ");
        int rows = readInt();
        System.out.print("Masukkan jumlah kolom: ");
        int cols = readInt();
        this.matrix_det = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("Masukkan elemen baris %d kolom %d: ", i + 1, j + 1);
                this.matrix_det[i][j] = readDouble();
            }
        }

        System.out.println("Matriks yang dimasukkan:");
        for (double[] row : matrix_det) {
            for (double element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }

    }

    private void inputFromFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan nama file (misalnya: ../test/input.txt): ");
        String fileName = scanner.nextLine();


        try {
            try {
                Scanner fileScanner = new Scanner(new File(fileName));
                int rows = 0;
                int cols = 0;
                while (fileScanner.hasNextLine()) {
                    String[] line = fileScanner.nextLine().split(" ");
                    if (rows == 0) {
                        cols = line.length;
                    }
                    rows++;
                }
                this.matrix_det = new double[rows][cols];

                fileScanner = new Scanner(new File(fileName)); // Restart untuk membaca ulang
                for (int i = 0; i < rows; i++) {
                    String[] line = fileScanner.nextLine().split(" ");
                    for (int j = 0; j < cols; j++) {
                        this.matrix_det[i][j] = Double.parseDouble(line[j]);
                    }
                }
                fileScanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("Error: File tidak ditemukan.");
                scanner.close();
            }
        } catch (Exception e) {
            System.out.println("Error: Terjadi kesalahan saat membaca file.");
        }
    }

    private static int readInt() {
        while (true) {
            try {
                int value = Integer.parseInt(readLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Error: Input tidak valid. Masukkan angka bulat.");
            }
        }
    }
    private static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(readLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Input tidak valid. Masukkan angka.");
            }
        }
    }

    private static String readLine() {
        StringBuilder input = new StringBuilder();
        int c;
        while (true) {
            try {
                c = System.in.read();
                if (c == -1 || c == '\n') break;
                input.append((char)c);
            } catch (Exception e) {
                System.out.println("Error: Terjadi kesalahan saat membaca input.");
                return "";
            }
        }
        return input.toString().trim();
    }
}
