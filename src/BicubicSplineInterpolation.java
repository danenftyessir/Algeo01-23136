import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BicubicSplineInterpolation {
    private static final int MATRIX_SIZE = 4;
    private static final int COEFFICIENTS_COUNT = 16;

    private double[][] inputMatrix;
    private double[] coefficients;
    private double a;
    private double b;
    private Scanner scanner;

    public BicubicSplineInterpolation() {
        this.inputMatrix = new double[MATRIX_SIZE][MATRIX_SIZE];
        this.coefficients = new double[COEFFICIENTS_COUNT];
        this.a = 0;
        this.b = 0;
        this.scanner = new Scanner(System.in);
    }

    public void detectInterpolationType() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       DETEKSI TIPE INTERPOLASI       ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Masukkan ukuran matriks (n x n): ");
        int size = getValidIntInput(2, 10);

        double[][] matrix = new double[size][size];
        System.out.println("Masukkan elemen-elemen matriks:");
        int i = 0;
        while (i < size) {
            int j = 0;
            while (j < size) {
                System.out.print("Elemen [" + (i+1) + "][" + (j+1) + "]: ");
                matrix[i][j] = getValidDoubleInput();
                j = j + 1;
            }
            i = i + 1;
        }

        String interpolationType = determineInterpolationType(size);
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║           HASIL DETEKSI              ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ Tipe Interpolasi: " + padRight(interpolationType, 18) + "║");
        System.out.println("╚══════════════════════════════════════╝");

        if (interpolationType.equals("Bicubic Spline")) {
            System.out.print("\nApakah Anda ingin melanjutkan dengan Bicubic Spline Interpolation? (y/n): ");
            String response = scanner.next().toLowerCase();
            if (response.equals("y")) {
                this.inputMatrix = matrix;
                performBicubicSplineInterpolation();
            }
        }
    }

    private String determineInterpolationType(int size) {
        if (size == 2) {
            return "Bilinear";
        } else if (size == 4) {
            return "Bicubic Spline";
        } else if (size == 3) {
            return "In Between Spline";
        } else {
            return "Input Salah!";
        }
    }

    public void performBicubicSplineInterpolation() {
        if (this.inputMatrix == null) {
            System.out.println("Error: Input matrix belum diinisialisasi.");
            return;
        }
        inputAB();
        calculateCoefficients();
        String polynomialString = getPolynomialString();
        double result = evaluatePolynomial();
        
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  HASIL BICUBIC SPLINE INTERPOLATION  ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ f(x,y) = " + padRight(polynomialString, 28) + "║");
        System.out.println("║ f(" + formatDouble(this.a) + ", " + 
                           formatDouble(this.b) + ") = " + 
                           padRight(formatDouble(result), 27)             + "║");
        System.out.println("╚══════════════════════════════════════╝");

        System.out.print("\nApakah Anda ingin menyimpan hasil ke file? (y/n): ");
        String saveChoice = scanner.next();
        while (!saveChoice.equals("y") && !saveChoice.equals("n")) {
            System.out.println("Pilihan tidak valid, harap masukkan (y/n)!");
            saveChoice = scanner.next();
            if (saveChoice.equals("y") || saveChoice.equals("n")) {
                break;
            }
        }
        if (saveChoice.equalsIgnoreCase("y")) {
            saveOutputToFile(polynomialString, result);
        }        
    }

    private void inputAB() {
        this.a = getValidInput("Masukkan nilai a (0 <= a <= 1): ", 0, 1);
        this.b = getValidInput("Masukkan nilai b (0 <= b <= 1): ", 0, 1);
    }

    private double getValidInput(String prompt, double min, double max) {
        double input;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextDouble()) {
                System.out.println("Input tidak valid. Mohon masukkan angka.");
                scanner.next();
            }
            input = scanner.nextDouble();
            if (input < min || input > max) {
                System.out.println("Nilai harus antara " + min + " dan " + max + ".");
            }
        } while (input < min || input > max);
        return input;
    }

    private void calculateCoefficients() {
        double[][] X = buildXMatrix();
        double[] y = flattenMatrix();
        this.coefficients = solveLinearSystem(X, y);
    }

    private double[][] buildXMatrix() {
        double[][] X = new double[COEFFICIENTS_COUNT][COEFFICIENTS_COUNT];
        int i = 0;
        while (i < COEFFICIENTS_COUNT) {
            int x = i % MATRIX_SIZE;
            int y = i / MATRIX_SIZE;
            int j = 0;
            while (j < COEFFICIENTS_COUNT) {
                int power_x = j % MATRIX_SIZE;
                int power_y = j / MATRIX_SIZE;
                X[i][j] = powerFunction(x, power_x) * powerFunction(y, power_y);
                j = j + 1;
            }
            i = i + 1;
        }
        return X;
    }

    private double[] flattenMatrix() {
        double[] flattened = new double[COEFFICIENTS_COUNT];
        int i = 0;
        while (i < MATRIX_SIZE) {
            int j = 0;
            while (j < MATRIX_SIZE) {
                flattened[i * MATRIX_SIZE + j] = this.inputMatrix[i][j];
                j = j + 1;
            }
            i = i + 1;
        }
        return flattened;
    }

    private double[] solveLinearSystem(double[][] A, double[] b) {
        int n = b.length;
        int p = 0;
        while (p < n) {
            int max = p;
            int i = p + 1;
            while (i < n) {
                if (abs(A[i][p]) > abs(A[max][p])) {
                    max = i;
                }
                i = i + 1;
            }
            double[] temp = A[p];
            A[p] = A[max];
            A[max] = temp;
            double t = b[p];
            b[p] = b[max];
            b[max] = t;
            if (A[p][p] == 0) {
                throw new ArithmeticException("Matriks singular atau hampir singular");
            }
            i = p + 1;
            while (i < n) {
                double alpha = A[i][p] / A[p][p];
                b[i] = b[i] - alpha * b[p];
                int j = p;
                while (j < n) {
                    A[i][j] = A[i][j] - alpha * A[p][j];
                    j = j + 1;
                }
                i = i + 1;
            }
            p = p + 1;
        }
        double[] x = new double[n];
        int i = n - 1;
        while (i >= 0) {
            double sum = 0.0;
            int j = i + 1;
            while (j < n) {
                sum = sum + A[i][j] * x[j];
                j = j + 1;
            }
            x[i] = (b[i] - sum) / A[i][i];
            i = i - 1;
        }
        return x;
    }

    private static double powerFunction(double base, int exponent) {
        double result = 1;
        int i = 0;
        while (i < exponent) {
            result = result * base;
            i = i + 1;
        }
        return result;
    }

    private static double abs(double x) {
        if (x < 0) {
            return -x;
        }
        return x;
    }

    private String getPolynomialString() {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        int i = 0;
        while (i < COEFFICIENTS_COUNT) {
            double coeff = this.coefficients[i];
            if (coeff != 0) {
                if (coeff > 0 && !isFirst) {
                    sb.append(" + ");
                } else if (coeff < 0) {
                    sb.append(" - ");
                }
                coeff = abs(coeff);
                if (coeff != 1 || (i % MATRIX_SIZE == 0 && i / MATRIX_SIZE == 0)) {
                    sb.append(formatDouble(coeff));
                }
                int power_x = i % MATRIX_SIZE;
                int power_y = i / MATRIX_SIZE;
                if (power_x > 0) {
                    sb.append("x");
                    if (power_x > 1) {
                        sb.append("^").append(power_x);
                    }
                }
                if (power_y > 0) {
                    sb.append("y");
                    if (power_y > 1) {
                        sb.append("^").append(power_y);
                    }
                }
                isFirst = false;
            }
            i = i + 1;
        }
        return sb.toString();
    }

    private double evaluatePolynomial() {
        double result = 0;
        int i = 0;
        while (i < COEFFICIENTS_COUNT) {
            int power_x = i % MATRIX_SIZE;
            int power_y = i / MATRIX_SIZE;
            double term = this.coefficients[i] * powerFunction(this.a, power_x) * powerFunction(this.b, power_y);
            result = result + term;
            i = i + 1;
        }
        return result;
    }

    private void saveOutputToFile(String polynomialString, double result) {
        System.out.print("Masukkan nama file untuk menyimpan hasil (tanpa ekstensi .txt): ");
        String fileName = scanner.next();

        if (!fileName.endsWith(".txt")) {
            fileName = fileName + ".txt";
        }

        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println("Persamaan Polinomial Interpolasi:");
            writer.println("f(x,y) = " + polynomialString);
            writer.println();
            writer.println("Taksiran nilai fungsi:");
            writer.println("f(" + formatDouble(this.a) + ", " + 
                           formatDouble(this.b) + ") = " + 
                           formatDouble(result));
            System.out.println("Output berhasil disimpan ke file '" + fileName + "'");
        } catch (IOException e) {
            System.out.println("Error saat menyimpan output ke file: " + e.getMessage());
        }
    }

    private int getValidIntInput(int min, int max) {
        int input;
        while (true) {
            try {
                input = Integer.parseInt(scanner.next());
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.print("Input harus antara " + min + " dan " + max + ". Coba lagi: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Input tidak valid. Masukkan angka: ");
            }
        }
    }

    private double getValidDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.next());
            } catch (NumberFormatException e) {
                System.out.print("Input tidak valid. Masukkan angka: ");
            }
        }
    }

    private static String formatDouble(double x) {
        if (x == 0) {
            return "0";
        }
        String formatted = String.format("%.4f", abs(x));
        while (formatted.endsWith("0")) {
            formatted = formatted.substring(0, formatted.length() - 1);
        }
        if (formatted.endsWith(".")) {
            formatted = formatted.substring(0, formatted.length() - 1);
        }
        return formatted;
    }

    private static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }
}