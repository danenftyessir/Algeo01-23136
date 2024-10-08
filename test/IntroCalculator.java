public class IntroCalculator {
    public static void main(String[] args) {
        int choice;
        do {
            displayMainMenu();
            choice = getMenuChoice();
            processMainMenuChoice(choice);
        } while (choice != 8);
    }

    private static void displayMainMenu() {
        System.out.println("\n\n");
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║             KALKULATOR MATRIKS                ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║  1. Sistem Persamaan Linier                   ║");
        System.out.println("║  2. Determinan                                ║");
        System.out.println("║  3. Matriks balikan                           ║");
        System.out.println("║  4. Interpolasi Polinom                       ║");
        System.out.println("║  5. Interpolasi Bicubic Spline                ║");
        System.out.println("║  6. Regresi linier dan kuadratik berganda     ║");
        System.out.println("║  7. Interpolasi Gambar (Bonus)                ║");
        System.out.println("║  8. Keluar                                    ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.print("Pilih menu (1-8): ");
    }

    private static int getMenuChoice() {
        int choice = 0;
        boolean validInput = false;
        while (!validInput) {
            int input = readSingleChar();
            if (input >= '1' && input <= '8') {
                choice = input - '0';
                validInput = true;
            } else {
                System.out.println("Input tidak valid. Masukkan angka antara 1-8.");
            }
            clearInputBuffer();
        }
        return choice;
    }

    private static int readSingleChar() {
        int input = 0;
        try {
            input = System.in.read();
        } catch (java.io.IOException e) {
            return -1;
        }
        return input;
    }
    private static void clearInputBuffer() {
        try {
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (java.io.IOException e) {
        }
    }
    private static void processMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                System.out.println("Anda memilih: Sistem Persamaan Linier");
                displaySubMenu();
                break;
            case 2:
                System.out.println("Anda memilih: Determinan");
                CustomizableMatrix.createAndAnalyzeMatrix();
                break;
            case 3:
                System.out.println("Anda memilih: Matriks balikan");
                break;
            case 4:
                System.out.println("Anda memilih: Interpolasi Polinom");
                break;
            case 5:
                System.out.println("Anda memilih: Interpolasi Bicubic Spline");
                break;
            case 6:
                System.out.println("Anda memilih: Regresi linier dan kuadratik berganda");
                break;
            case 7:
                System.out.println("Anda memilih: Interpolasi Gambar (Bonus)");
                break;
            case 8:
                System.out.println("Terima kasih telah menggunakan kalkulator kami. Sampai jumpa!");
                break;
            default:
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
        }
        if (choice != 8) {
            System.out.println("\nTekan Enter untuk kembali ke menu utama...");
            clearInputBuffer();
        }
    }

    private static void displaySubMenu() {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║         SISTEM PERSAMAAN LINIER               ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║  1. Metode eliminasi Gauss                    ║");
        System.out.println("║  2. Metode eliminasi Gauss-Jordan             ║");
        System.out.println("║  3. Metode matriks balikan                    ║");
        System.out.println("║  4. Kaidah Cramer                             ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.print("Pilih metode (1-4): ");
        int subChoice = getMenuChoice();
        System.out.println("Anda memilih metode " + subChoice);
    }
}

class CustomizableMatrix {
    public static void createAndAnalyzeMatrix() {
        int[][] matrix = makeMatrix();
        System.out.println("\nMatriks yang dimasukkan:");
        printMatrix(matrix);   
        if (isIdentityMatrix(matrix)) {
            System.out.println("Matriks ini adalah matriks identitas.");
        } else {
            System.out.println("Matriks ini bukan matriks identitas.");
        }
    }
    private static int[][] makeMatrix() {
        System.out.print("Masukkan jumlah baris: ");
        int rows = readInt();
        System.out.print("Masukkan jumlah kolom: ");
        int cols = readInt();   
        int[][] matrix = new int[rows][cols];
        System.out.println("Masukkan elemen-elemen matriks:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("Matriks[" + i + "][" + j + "]: ");
                matrix[i][j] = readInt();
            }
        }
        return matrix;
    }
    private static boolean isIdentityMatrix(int[][] matrix) {
        if (matrix.length != matrix[0].length) {
            return false;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == j && matrix[i][j] != 1) {
                    return false;
                }
                if (i != j && matrix[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }
    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    private static int readInt() {
        int result = 0;
        boolean isNegative = false;
        int ch = 0;
        while (true) {
            try {
                ch = System.in.read();
                if (ch == '-') {
                    isNegative = true;
                    continue;
                }
                if (ch >= '0' && ch <= '9') {
                    result = result * 10 + (ch - '0');
                } else if (ch == '\n') {
                    break;
                }
            } catch (java.io.IOException e) {
                return 0;
            }
        }
        if (isNegative) {
            result = -result;
        }
        return result;
    }
}
