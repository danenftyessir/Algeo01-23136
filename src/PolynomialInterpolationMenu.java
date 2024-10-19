import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PolynomialInterpolationMenu {
    // Fungsi untuk menampilkan menu interpolasi polinom di IntroCalculator (Main Class)
    public static void displayPolynomialInterpolationMenu() {
        Scanner scanner = new Scanner(System.in);
        double[][] points = inputData(scanner);
        
        if (points == null) {
            System.out.println("Kembali ke menu utama.");
            return;
        }

        if (points.length < 2) {
            System.out.println("Jumlah titik tidak valid. Minimal dibutuhkan 2 titik.");
            return;
        }

        System.out.print("Masukkan nilai x yang ingin ditaksir: ");
        double xEstimate = scanner.nextDouble();

        if (xEstimate < points[0][0] || xEstimate > points[points.length - 1][0]) {
            System.out.println("Peringatan: Nilai x berada di luar rentang data input.");
        }

        double[] coefficients = calculateCoefficients(points);
        String polynomialString = getPolynomialString(coefficients);
        double result = evaluatePolynomial(coefficients, xEstimate);

        String output = "f(x) = " + polynomialString + "\n";
        output = output + "f(" + formatDouble(xEstimate) + ") = " + formatDouble(result);

        System.out.println(output);

        System.out.print("Apakah Anda ingin menyimpan hasil ke file? (y/n): ");
        String saveChoice = scanner.next();
        while (!saveChoice.equals("y") && !saveChoice.equals("n")) {
            System.out.println("Pilihan tidak valid, harap masukkan (y/n)!");
            saveChoice = scanner.next();
            if (saveChoice.equals("y") || saveChoice.equals("n")) {
                break;
            }
        }
        if (saveChoice.equalsIgnoreCase("y")) {
            saveToFile(output, scanner);
        }        
    }
    // Fungsi untuk input data dari keyboard atau file
    private static double[][] inputData(Scanner scanner) {
        System.out.println("/== Pilih metode input ==/");
        System.out.println("/ 1. Input dari keyboard /");
        System.out.println("/ 2. Input dari file     /");
        System.out.println("/========================/");
        System.out.print("Pilihan Anda: ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            return inputFromKeyboard(scanner);
        } else if (choice == 2) {
            return inputFromFile(scanner);
        } else {
            System.out.println("Pilihan tidak valid.");
            return null;
        }
    }

    // Fungsi untuk input data dari keyboard
    private static double[][] inputFromKeyboard(Scanner scanner) {
        System.out.print("Masukkan jumlah titik: ");
        int n = scanner.nextInt();
        ArrayList<double[]> pointsList = new ArrayList<>();
        
        int i = 0;
        while (i < n) {
            System.out.print("Masukkan titik ke-" + (i + 1) + " (x y): ");
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            if (i > 0 && x <= pointsList.get(i - 1)[0]) {
                System.out.println("Nilai x harus unik dan terurut. Masukkan ulang titik ini!");
                // i--; // Menghindari penggunaan unary operator
                i = i - 1;
            } else {
                pointsList.add(new double[]{x, y});
            }
            i = i + 1;
        }
        return pointsList.toArray(new double[0][]);
    }

    // Fungsi untuk input data dari file
    static double[][] inputFromFile(Scanner scanner) {
        ArrayList<double[]> pointsList = new ArrayList<>();
        
        while (true) {
            System.out.print("Masukkan nama file (atau ketik 'menu' untuk kembali): ");
            String fileName = scanner.next();
            if (fileName.equalsIgnoreCase("menu")) {
                return null;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                int lineNumber = 0;
                boolean isValid = true;
                while ((line = reader.readLine()) != null) {
                    lineNumber = lineNumber + 1;
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length != 2) {
                        System.out.println("Error: Format tidak valid pada baris " + lineNumber);
                        pointsList.clear();
                        isValid = false;
                        break;
                    }
                    try {
                        double x = Double.parseDouble(parts[0]);
                        double y = Double.parseDouble(parts[1]);
                        if (!pointsList.isEmpty() && x <= pointsList.get(pointsList.size() - 1)[0]) {
                            System.out.println("Error: Nilai x harus unik dan terurut pada baris " + lineNumber);
                            pointsList.clear();
                            isValid = false;
                            break;
                        }
                        pointsList.add(new double[]{x, y});
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Input bukan angka pada baris " + lineNumber);
                        pointsList.clear();
                        isValid = false;
                        break;
                    }
                }
                if (isValid && pointsList.size() >= 2) {
                    return pointsList.toArray(new double[0][]);
                } else {
                    System.out.println("Error: File harus berisi minimal 2 titik.");
                    continue;
                }
            } catch (IOException e) {
                System.out.println("Error: Tidak dapat membaca file " + fileName);
                System.out.println("Silakan masukkan ulang nama file atau ketik 'menu' untuk kembali.");
            }
        }
    }

    // Fungsi untuk menghitung koefisien polinom interpolasi
    private static double[] calculateCoefficients(double[][] points) {
        int n = points.length;
        double[][] matrix = new double[n][n + 1];
        int i = 0;
        while (i < n) {
            double x = points[i][0];
            double y = points[i][1];
            double xPower = 1;
            int j = 0;
            while (j < n) {
                matrix[i][j] = xPower;
                xPower = xPower * x;
                j = j + 1;
            }
            matrix[i][n] = y;
            i = i + 1;
        }
        matrix = GaussElimination.gaussElimination(matrix);
        return GaussElimination.backSubstitution(matrix);
    }

    // Fungsi untuk mendapatkan string polinom
    private static String getPolynomialString(double[] coefficients) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        int i = coefficients.length - 1;
        while (i >= 0) {
            double coeff = coefficients[i];
            if (coeff != 0) {
                if (coeff > 0 && !isFirst) {
                    sb.append(" + ");
                } else if (coeff < 0) {
                    sb.append(" - ");
                }
                coeff = abs(coeff);
                if (i == 0 || coeff != 1) {
                    sb.append(formatDouble(coeff));
                }
                if (i > 0) {
                    sb.append("x");
                    if (i > 1) {
                        sb.append("^");
                        sb.append(i);
                    }
                }
                isFirst = false;
            }
            i = i - 1;
        }
        return sb.toString();
    }

    // Fungsi evaluasi polinom
    private static double evaluatePolynomial(double[] coefficients, double x) {
        double result = 0;
        double xPower = 1;
        int i = 0;
        while (i < coefficients.length) {
            result = result + coefficients[i] * xPower;
            xPower = xPower * x;
            i = i + 1;
        }
        return result;
    }

    // Fungsi nilai absolut
    private static double abs(double x) {
        if (x < 0) {
            return 0 - x;
        } else {
            return x;
        }
    }

    // Format double untuk menghilangkan trailing zero
    static String formatDouble(double x) {
        long intPart = (long) x;
        if (x == intPart) {
            return Long.toString(intPart);
        }
        String formatted = String.format("%.4f", x);
        while (formatted.endsWith("0")) {
            formatted = formatted.substring(0, formatted.length() - 1);
        }
        if (formatted.endsWith(".")) {
            formatted = formatted.substring(0, formatted.length() - 1);
        }
        return formatted;
    }

    // Fungsi untuk menyimpan hasil ke file
    private static void saveToFile(String content, Scanner scanner) {
        System.out.print("Masukkan nama file untuk menyimpan hasil (tanpa ekstensi .txt): ");
        String fileName = scanner.next();
        // Memastikan nama file memiliki ekstensi .txt
        if (!fileName.endsWith(".txt")) {
            fileName = fileName + ".txt";
        }
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
            System.out.println("Hasil berhasil disimpan ke file: " + fileName);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
        }
    }
}
