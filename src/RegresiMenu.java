import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RegresiMenu {
    private static Scanner scanner = new Scanner(System.in);

    public static void displayRegresiMenu() {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║                    Regresi                    ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║  1. Regresi Linear Berganda                   ║");
        System.out.println("║  2. Regresi Kuadratik Berganda                ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.print("Pilih metode (1-2): ");
        int subChoice = readInt();

        while (subChoice < 1 || subChoice > 2) {
            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            System.out.print("Pilih metode (1-2): ");
            subChoice = readInt();
        }

        switch (subChoice) {
            case 1:
                System.out.println("Anda memilih: Regresi Linear Berganda");
                double[][] dataLinear = inputData();
                if (dataLinear != null) {
                    int n = dataLinear.length;
                    
                    // mengambil data kecuali data terakhir
                    double[][] regressionData = new double[n-1][dataLinear[0].length];
                    for (int i = 0; i < n-1; i++) {
                        System.arraycopy(dataLinear[i], 0, regressionData[i], 0, dataLinear[0].length);
                    }
                    
                    double[] koefisienLinear = Regresi.regresiLinear(regressionData);
                    int nLinear = regressionData[0].length - 1;
                    String outputLinear = formatRegressionOutput(koefisienLinear, false, nLinear);
                    System.out.println("Hasil Regresi Linear Berganda:");
                    System.out.println(outputLinear);

                    // menggunakan koefisien untuk menghitung nilai y
                    double[] xLinear = new double[nLinear];
                    for (int i = 0; i < nLinear; i++) {
                        xLinear[i] = dataLinear[n-1][i];
                    }
                    
                    double yLinear = Regresi.calculateY(koefisienLinear, xLinear, false);
                    System.out.printf("Nilai taksiran y untuk x yang diberikan: %.2f%n", yLinear);

                    System.out.println("Apakah Anda ingin menyimpan hasil ke file? (y/n)");
                    String saveChoiceLinear = readLine();
                    while (!saveChoiceLinear.equalsIgnoreCase("y") && !saveChoiceLinear.equalsIgnoreCase("n")) {
                        System.out.println("Pilihan tidak valid, harap masukkan (y/n)!");
                        saveChoiceLinear = readLine();
                    }
                    if (saveChoiceLinear.equalsIgnoreCase("y")) {
                        saveToFile(outputLinear + "\n\nNilai taksiran y untuk x yang diberikan: " + yLinear);
                    }
                }
                System.out.println("Tekan Enter untuk kembali ke menu utama!");
                readLine();
                break;

            case 2:
                System.out.println("Anda memilih: Regresi Kuadratik Berganda");
                double[][] dataKuadratik = inputData();
                if (dataKuadratik != null) {
                    double[] koefisienKuadratik = Regresi.regresiKuadratik(dataKuadratik);
                    int nKuadratik = dataKuadratik[0].length - 1;
                    String outputKuadratik = formatRegressionOutput(koefisienKuadratik, true, nKuadratik);
                    System.out.println("Hasil Regresi Kuadratik Berganda:");
                    System.out.println(outputKuadratik);

                    System.out.println("Masukkan nilai-nilai x yang akan ditaksir:");
                    double[] xKuadratik = new double[nKuadratik];
                    for (int i = 0; i < nKuadratik; i++) {
                        System.out.print("x" + (i + 1) + ": ");
                        xKuadratik[i] = readDouble();
                    }

                    double yKuadratik = Regresi.calculateY(koefisienKuadratik, xKuadratik, true);
                    System.out.printf("Nilai taksiran y untuk x yang diberikan: %.2f%n", yKuadratik);

                    System.out.println("Apakah Anda ingin menyimpan hasil ke file? (y/n)");
                    String saveChoiceKuadratik = readLine();
                    while (!saveChoiceKuadratik.equalsIgnoreCase("y") && !saveChoiceKuadratik.equalsIgnoreCase("n")) {
                        System.out.println("Pilihan tidak valid, harap masukkan (y/n)!");
                        saveChoiceKuadratik = readLine();
                    }
                    if (saveChoiceKuadratik.equalsIgnoreCase("y")) {
                        saveToFile(outputKuadratik + "\n\nNilai taksiran y untuk x yang diberikan: " + yKuadratik);
                    }
                }
                System.out.println("Tekan Enter untuk kembali ke menu utama!");
                readLine();
                break;
        }
    }

    private static double[][] inputData() {
        System.out.println("=== Input Data Regresi ===");
        System.out.println("Pilih metode input:");
        System.out.println("1. Input dari keyboard");
        System.out.println("2. Input dari file");
        System.out.print("Masukkan pilihan (1/2): ");
        int choice = readInt();

        if (choice == 1) {
            return inputFromKeyboard();
        } else if (choice == 2) {
            return inputFromFile();
        }
        return null;
    }

    private static double[][] inputFromKeyboard() {
        System.out.println("\nFormat input: x1 x2 x3 y");
        System.out.print("Masukkan jumlah data: ");
        int rows = readInt();

        double[][] data = new double[rows][4];
        System.out.println("Masukkan data (x1 x2 x3 y) setiap baris:");
        
        for (int i = 0; i < rows; i++) {
            System.out.print("Data ke-" + (i + 1) + ": ");
            String[] elements = readLine().trim().split("\\s+");
            for (int j = 0; j < 4; j++) {
                data[i][j] = Double.parseDouble(elements[j]);
            }
        }
        return data;
    }

    private static double[][] inputFromFile() {
        System.out.print("Masukkan nama file: ");
        String filename = readLine();

        List<double[]> rows = new ArrayList<>();
        
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] elements = line.split("\\s+");
                    double[] row = new double[elements.length];
                    for (int i = 0; i < elements.length; i++) {
                        row[i] = Double.parseDouble(elements[i]);
                    }
                    rows.add(row);
                }
            }
            
            double[][] data = new double[rows.size()][];
            for (int i = 0; i < rows.size(); i++) {
                data[i] = rows.get(i);
            }
            return data;
        } catch (Exception e) {
            return new double[1][4];
        }
    }

    // Menyimpan hasil ke file
    private static void saveToFile(String results) {
        System.out.print("Masukkan nama file yang akan disimpan (tanpa ekstensi): ");
        String name = readLine();
        name += ".txt";
        try (FileWriter writer = new FileWriter(name)) {
            writer.write(results);
            System.out.printf("Berhasil disimpan ke file %s%n", name);
        } catch (IOException e) {
            System.out.println("Error: Gagal menyimpan ke file");
        }
    }

    // Memformat output regresi
    private static String formatRegressionOutput(double[] koefisien, boolean isKuadratik, int nLinier) {
        String output = "y = " + String.format("%.4f", koefisien[0]);
        int index = 1;

        // Variabel linier
        for (int i = 1; i <= nLinier; i++) {
            output += " + " + String.format("%.4f", koefisien[index]) + "*X" + i;
            index++;
        }

        if (isKuadratik) {
            // Variabel kuadrat
            for (int i = 1; i <= nLinier; i++) {
                output += " + " + String.format("%.4f", koefisien[index]) + "*X" + i + "^2";
                index++;
            }

            // Variabel interaksi
            for (int i = 1; i <= nLinier; i++) {
                for (int j = i + 1; j <= nLinier; j++) {
                    output += " + " + String.format("%.4f", koefisien[index]) + "*X" + i + "X" + j;
                    index++;
                }
            }
        }

        return output;
    }

    // Membaca input integer dengan validasi
    private static int readInt() {
        while (true) {
            try {
                String input = readLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Input tidak valid. Masukkan angka bulat.");
            }
        }
    }

    // Membaca input double dengan validasi
    private static double readDouble() {
        while (true) {
            try {
                String input = readLine();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Input tidak valid. Masukkan angka.");
            }
        }
    }

    // Membaca baris input dari pengguna
    private static String readLine() {
        StringBuilder input = new StringBuilder();
        int c;
        try {
            while (true) {
                c = System.in.read();
                if (c == -1 || c == '\n') break;
                input.append((char)c);
            }
            return input.toString().trim();
        } catch (Exception e) {
            return input.toString().trim();
        }
    }
}
