import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BicubicSplineInterpolation {
    private static final int MATRIX_SIZE = 4;
    private static final int COEFFICIENTS_COUNT = 16;

    private double[][] inputMatrix;
    private double[] coefficients;
    private double a;
    private double b;

    // Tambahan: Matriks untuk turunan parsial
    private double[][] dx;
    private double[][] dy;
    private double[][] dxy;

    // Konstruktor
    public BicubicSplineInterpolation() {
        this.inputMatrix = new double[MATRIX_SIZE][MATRIX_SIZE];
        this.coefficients = new double[COEFFICIENTS_COUNT];
        this.a = 0;
        this.b = 0;

        // Inisialisasi matriks turunan
        this.dx = new double[MATRIX_SIZE][MATRIX_SIZE];
        this.dy = new double[MATRIX_SIZE][MATRIX_SIZE];
        this.dxy = new double[MATRIX_SIZE][MATRIX_SIZE];
    }
    
    public void setA(double a) {
        this.a = a;
    }
    public void setB(double b) {
        this.b = b;
    }
    public void setInputMatrix(double[][] inputMatrix) {
        this.inputMatrix = inputMatrix;
    }

    // Metode utama untuk menjalankan interpolasi
    public void runInterpolation() {
        Scanner scanner = new Scanner(System.in);
        int inputChoice = 0;

        // Menampilkan menu input
        System.out.println("\n===== Menu Input =====");
        System.out.println("Pilih metode input matriks:");
        System.out.println("1. Input melalui keyboard");
        System.out.println("2. Input melalui file");
        System.out.print("Pilihan Anda: ");

        // Membaca pilihan input dari pengguna
        while (true) {
            String choiceStr = scanner.nextLine();
            inputChoice = parseInteger(choiceStr);
            if (inputChoice == 1 || inputChoice == 2) {
                break;
            } else {
                System.out.print("Pilihan tidak valid. Masukkan 1 atau 2: ");
            }
        }

        if (inputChoice == 1) {
            // Input melalui keyboard
            inputFromKeyboard(scanner);
        } else {
            // Input melalui file
            System.out.print("Masukkan nama file input (contoh: test/input.txt): ");
            String inputFileName = scanner.nextLine();
            readInputFromFile(inputFileName);

            if (this.inputMatrix == null) {
                System.out.println("Gagal membaca data dari file. Program dihentikan.");
                scanner.close();
                return;
            }
        }

        // Meminta nama file output dari pengguna
        System.out.print("Masukkan nama file output (contoh: output.txt): ");
        String outputFileName = scanner.nextLine();

        // Melakukan interpolasi dan menyimpan output
        performBicubicSplineInterpolation(outputFileName);
    }

    // Metode untuk input melalui keyboard
    private void inputFromKeyboard(Scanner scanner) {
        int size = MATRIX_SIZE;
        double[][] matrix = new double[size][size];

        System.out.println("Masukkan elemen-elemen matriks 4x4:");

        // Membaca elemen matriks dari pengguna
        int i = 0;
        while (i < size) {
            int j = 0;
            while (j < size) {
                System.out.print("Elemen [" + i + "][" + j + "]: ");
                String inputStr = scanner.nextLine();
                matrix[i][j] = parseDouble(inputStr);
                j = j + 1;
            }
            i = i + 1;
        }

        // Membaca nilai a
        System.out.print("Masukkan nilai a (0 <= a <= 1): ");
        while (true) {
            String aStr = scanner.nextLine();
            double aValue = parseDouble(aStr);
            if (aValue >= 0 && aValue <= 1) {
                this.a = aValue;
                break;
            } else {
                System.out.print("Nilai a harus antara 0 dan 1. Coba lagi: ");
            }
        }

        // Membaca nilai b
        System.out.print("Masukkan nilai b (0 <= b <= 1): ");
        while (true) {
            String bStr = scanner.nextLine();
            double bValue = parseDouble(bStr);
            if (bValue >= 0 && bValue <= 1) {
                this.b = bValue;
                break;
            } else {
                System.out.print("Nilai b harus antara 0 dan 1. Coba lagi: ");
            }
        }

        this.inputMatrix = matrix;
    }

    // Metode untuk membaca input dari file
    public void readInputFromFile(String fileName) {
        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            int size = MATRIX_SIZE;

            this.inputMatrix = new double[size][size];
            System.out.println("Membaca elemen-elemen matriks dari file...");
            int i = 0;
            while (i < size) {
                int j = 0;
                while (j < size) {
                    if (fileScanner.hasNext()) {
                        String valueStr = fileScanner.next();
                        this.inputMatrix[i][j] = parseDouble(valueStr);
                    } else {
                        System.out.println("File input tidak memiliki cukup elemen matriks.");
                        this.inputMatrix = null; // Mengatur inputMatrix ke null jika terjadi kesalahan
                        return;
                    }
                    j = j + 1;
                }
                i = i + 1;
            }
            // Membaca nilai a dan b
            if (fileScanner.hasNext()) {
                this.a = parseDouble(fileScanner.next());
            } else {
                System.out.println("Nilai a tidak ditemukan dalam file input.");
                this.inputMatrix = null; // Mengatur inputMatrix ke null jika terjadi kesalahan
                return;
            }
            if (fileScanner.hasNext()) {
                this.b = parseDouble(fileScanner.next());
            } else {
                System.out.println("Nilai b tidak ditemukan dalam file input.");
                this.inputMatrix = null; // Mengatur inputMatrix ke null jika terjadi kesalahan
                return;
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + fileName);
            this.inputMatrix = null; // Mengatur inputMatrix ke null jika terjadi kesalahan
        }
    }

    // Metode untuk melakukan interpolasi bicubic spline
    public void performBicubicSplineInterpolation(String outputFileName) {
        if (this.inputMatrix == null) {
            System.out.println("Error: Matriks input belum diinisialisasi.");
            return;
        }
        calculateDerivatives(); // Menghitung turunan parsial
        calculateCoefficients(); // Menghitung koefisien
        double result = evaluatePolynomial(); // Menghitung nilai fungsi pada titik (a, b)

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  HASIL BICUBIC SPLINE INTERPOLATION  ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("  f(" + formatDouble(this.a) + ", " +
                formatDouble(this.b) + ") = " +
                padRight(formatDouble(result), 27));
        System.out.println("╚══════════════════════════════════════╝");

        // Menyimpan output ke file dengan nama yang ditentukan
        try {
            PrintWriter writer = new PrintWriter(outputFileName);
            writer.println("Taksiran nilai fungsi:");
            writer.println("f(" + formatDouble(this.a) + ", " +
                    formatDouble(this.b) + ") = " +
                    formatDouble(result));
            writer.close();
            System.out.println("Output berhasil disimpan ke file '" + outputFileName + "'");
        } catch (IOException e) {
            System.out.println("Error saat menyimpan output ke file: " + e.getMessage());
        }
    }

    // Menghitung turunan parsial menggunakan perbedaan hingga
    private void calculateDerivatives() {
        int size = MATRIX_SIZE;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Turunan terhadap x
                if (i == 0) {
                    dx[i][j] = (inputMatrix[i + 1][j] - inputMatrix[i][j]);
                } else if (i == size - 1) {
                    dx[i][j] = (inputMatrix[i][j] - inputMatrix[i - 1][j]);
                } else {
                    dx[i][j] = (inputMatrix[i + 1][j] - inputMatrix[i - 1][j]) / 2.0;
                }

                // Turunan terhadap y
                if (j == 0) {
                    dy[i][j] = (inputMatrix[i][j + 1] - inputMatrix[i][j]);
                } else if (j == size - 1) {
                    dy[i][j] = (inputMatrix[i][j] - inputMatrix[i][j - 1]);
                } else {
                    dy[i][j] = (inputMatrix[i][j + 1] - inputMatrix[i][j - 1]) / 2.0;
                }

                // Turunan silang
                if (i == 0 || i == size - 1 || j == 0 || j == size - 1) {
                    // Untuk titik tepi, kita asumsikan turunan silang adalah nol
                    dxy[i][j] = 0.0;
                } else {
                    dxy[i][j] = (inputMatrix[i + 1][j + 1] - inputMatrix[i + 1][j - 1]
                            - inputMatrix[i - 1][j + 1] + inputMatrix[i - 1][j - 1]) / 4.0;
                }
            }
        }
    }

    // Menghitung koefisien untuk polinomial interpolasi
    public void calculateCoefficients() {
        // Matriks nilai fungsi dan turunannya pada titik (0,0), (0,1), (1,0), (1,1)
        double[] F = new double[16];
        int index = 0;
        for (int i = 0; i <= 1; i++) { // y
            for (int j = 0; j <= 1; j++) { // x
                F[index++] = inputMatrix[j][i]; // f
                F[index++] = dx[j][i];          // f_x
                F[index++] = dy[j][i];          // f_y
                F[index++] = dxy[j][i];         // f_xy
            }
        }

        // Membangun matriks X menggunakan persamaan
        double[][] X = new double[16][16];
        index = 0;
        for (int i = 0; i <= 1; i++) { // y
            for (int j = 0; j <= 1; j++) { // x
                double xi = j;
                double yi = i;
                // Basis fungsi polinomial bicubic
                double[] basis = new double[16];
                int idx = 0;
                for (int n = 0; n <= 3; n++) { // pangkat y
                    for (int m = 0; m <= 3; m++) { // pangkat x
                        basis[idx++] = Math.pow(xi, m) * Math.pow(yi, n);
                    }
                }
                // f
                for (int k = 0; k < 16; k++) {
                    X[index][k] = basis[k];
                }
                index++;

                // f_x
                idx = 0;
                for (int n = 0; n <= 3; n++) {
                    for (int m = 0; m <= 3; m++) {
                        if (m == 0) {
                            basis[idx++] = 0;
                        } else {
                            basis[idx++] = m * Math.pow(xi, m - 1) * Math.pow(yi, n);
                        }
                    }
                }
                for (int k = 0; k < 16; k++) {
                    X[index][k] = basis[k];
                }
                index++;

                // f_y
                idx = 0;
                for (int n = 0; n <= 3; n++) {
                    for (int m = 0; m <= 3; m++) {
                        if (n == 0) {
                            basis[idx++] = 0;
                        } else {
                            basis[idx++] = n * Math.pow(xi, m) * Math.pow(yi, n - 1);
                        }
                    }
                }
                for (int k = 0; k < 16; k++) {
                    X[index][k] = basis[k];
                }
                index++;

                // f_xy
                idx = 0;
                for (int n = 0; n <= 3; n++) {
                    for (int m = 0; m <= 3; m++) {
                        if (m == 0 || n == 0) {
                            basis[idx++] = 0;
                        } else {
                            basis[idx++] = m * n * Math.pow(xi, m - 1) * Math.pow(yi, n - 1);
                        }
                    }
                }
                for (int k = 0; k < 16; k++) {
                    X[index][k] = basis[k];
                }
                index++;
            }
        }

        // Membangun matriks augmented
        double[][] augmentedMatrix = new double[16][17]; // 16 persamaan, 16 variabel + 1 kolom RHS
        for (int i = 0; i < 16; i++) {
            System.arraycopy(X[i], 0, augmentedMatrix[i], 0, 16);
            augmentedMatrix[i][16] = F[i];
        }

        // Menyelesaikan sistem persamaan linear menggunakan Gauss-Jordan Elimination
        double[][] rrefMatrix = GaussJordanElimination.gaussJordanElimination(augmentedMatrix);

        // Ekstrak koefisien dari matriks tereduksi
        this.coefficients = new double[16];
        for (int i = 0; i < 16; i++) {
            this.coefficients[i] = rrefMatrix[i][16];
        }
    }

    // Menghitung nilai polinomial pada titik (a, b)
    public double evaluatePolynomial() {
        double x = this.a;
        double y = this.b;
        double result = 0.0;
        int index = 0;
        for (int n = 0; n <= 3; n++) { // pangkat y
            for (int m = 0; m <= 3; m++) { // pangkat x
                double term = this.coefficients[index] * powerFunctionDouble(x, m) * powerFunctionDouble(y, n);
                result += term;
                index++;
            }
        }
        return result;
    }

    // Menghitung base pangkat eksponen untuk double tanpa menggunakan Math.pow
    private static double powerFunctionDouble(double base, int exponent) {
        double result = 1.0;
        int i = 0;
        while (i < exponent) {
            result = result * base;
            i = i + 1;
        }
        return result;
    }

    // Format double menjadi string dengan maksimal 4 desimal
    private static String formatDouble(double x) {
        String str = doubleToString(x, 4);
        return str;
    }

    // Konversi double ke string dengan jumlah desimal tertentu tanpa menggunakan String.format
    private static String doubleToString(double value, int decimals) {
        StringBuilder sb = new StringBuilder();
        long intPart = (long) value;
        double fractionalPart = value - intPart;

        if (value < 0) {
            sb.append("-");
            intPart = intPart * (-1);
            fractionalPart = fractionalPart * (-1);
        }

        sb.append(longToString(intPart));
        if (decimals > 0) {
            sb.append(".");
            int i = 0;
            while (i < decimals) {
                fractionalPart = fractionalPart * 10;
                int digit = (int) fractionalPart;
                sb.append((char) ('0' + digit));
                fractionalPart = fractionalPart - digit;
                i = i + 1;
            }
        }

        return sb.toString();
    }

    // Konversi long ke string
    private static String longToString(long value) {
        StringBuilder sb = new StringBuilder();
        if (value == 0) {
            return "0";
        }
        boolean negative = false;
        if (value < 0) {
            negative = true;
            value = value * (-1);
        }
        while (value > 0) {
            int digit = (int) (value % 10);
            sb.insert(0, (char) ('0' + digit));
            value = value / 10;
        }
        if (negative) {
            sb.insert(0, "-");
        }
        return sb.toString();
    }

    // Menambahkan padding spasi di kanan string tanpa menggunakan String.format
    private static String padRight(String s, int n) {
        StringBuilder sb = new StringBuilder(s);
        int spacesToAdd = n - s.length();
        int i = 0;
        while (i < spacesToAdd) {
            sb.append(" ");
            i = i + 1;
        }
        return sb.toString();
    }

    // Metode untuk parsing integer tanpa menggunakan Integer.parseInt
    private static int parseInteger(String s) {
        int result = 0;
        int i = 0;
        int length = s.length();
        boolean negative = false;

        if (length == 0) {
            return 0;
        }

        if (s.charAt(0) == '-') {
            negative = true;
            i = i + 1;
        }

        while (i < length) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                result = result * 10 + (c - '0');
            } else {
                return 0; // Jika karakter bukan digit, kembalikan 0
            }
            i = i + 1;
        }

        if (negative) {
            result = result * (-1);
        }

        return result;
    }

    // Metode untuk parsing double tanpa menggunakan Double.parseDouble
    private static double parseDouble(String s) {
        double result = 0.0;
        int i = 0;
        int length = s.length();
        boolean negative = false;
        boolean fractional = false;
        double fractionalDivider = 1.0;

        if (length == 0) {
            return 0.0;
        }

        if (s.charAt(0) == '-') {
            negative = true;
            i = i + 1;
        }

        while (i < length) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                if (!fractional) {
                    result = result * 10 + (c - '0');
                } else {
                    fractionalDivider = fractionalDivider * 10;
                    result = result + (c - '0') / fractionalDivider;
                }
            } else if (c == '.') {
                if (!fractional) {
                    fractional = true;
                } else {
                    return 0.0; // Jika terdapat lebih dari satu titik desimal, kembalikan 0.0
                }
            } else {
                return 0.0; // Jika karakter bukan digit atau titik, kembalikan 0.0
            }
            i = i + 1;
        }

        if (negative) {
            result = result * (-1);
        }

        return result;
    }
}
