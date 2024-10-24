import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LinearEquationInputSystem {
    private int numEquations;
    private int numVariables;
    private double[][] coefficients;

    // Konstruktor
    public LinearEquationInputSystem() {
        this.numEquations = 0;
        this.numVariables = 0;
        this.coefficients = null;
    }

    public int getNumEquations() {
        return this.numEquations;
    }

    public int getNumVariables() {
        return this.numVariables;
    }

    public double[][] getCoefficients() {
        return this.coefficients;
    }

    // Fungsi untuk input persamaan linear
    public void inputLinearEquations() {
        System.out.println("=== Sistem Input Persamaan Linear ===");
        System.out.println("Pilih metode input: ");
        System.out.println("1. Input dari keyboard");
        System.out.println("2. Input dari file");

        int choice = readInt("Masukkan pilihan (1/2): ", 1, 2);
        
        if (choice == 1) {
            inputFromKeyboard();
        } else if (choice == 2) {
            inputFromFile();
        }
        
        if (coefficients != null) {
            displayEquations();
        }
    }

    // Fungsi untuk input dari keyboard
    private void inputFromKeyboard() {
        this.numEquations = readInt("\nMasukkan jumlah persamaan (1-10): ", 1, 10);
        this.numVariables = readInt("Masukkan jumlah variabel (1-10): ", 1, 10);
        
        this.coefficients = new double[getNumVariables()][getNumVariables() + 1];

        System.out.println("\nMasukkan koefisien dan konstanta untuk setiap persamaan.");
        printExample(getNumVariables());

        for (int i = 0; i < getNumEquations(); i++) {
            System.out.println("\nPersamaan " + (i + 1) + ":");
            for (int j = 0; j < getNumVariables(); j++) {
                getCoefficients()[i][j] = readDouble("  Koefisien X" + (j + 1) + ": ");
            }
            getCoefficients()[i][getNumVariables()] = readDouble("  Konstanta: ");
        }
        for (int i = getNumEquations(); i < getNumVariables(); i++) {
            System.out.println("\nPersamaan " + (i + 1) + ":");
            for (int j = 0; j < getNumVariables(); j++) {
                getCoefficients()[i][j] = 0;
            }
        }
    }

    // Fungsi untuk input dari file
    private void inputFromFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan nama file (misalnya: ../test/input.txt): ");
        String fileName = scanner.nextLine();


        try{
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
            this.numVariables = cols - 1; // Karena kolom terakhir adalah konstanta
            if (rows > this.numVariables) {
                this.numEquations = this.numVariables;
            }
            else {
                this.numEquations = rows;
            }
            this.coefficients = new double[numVariables][cols];

            fileScanner = new Scanner(new File(fileName)); // Restart untuk membaca ulang
            for (int i = 0; i < numEquations; i++) {
                String[] line = fileScanner.nextLine().split(" ");
                for (int j = 0; j < cols; j++) {
                    coefficients[i][j] = Double.parseDouble(line[j]);
                }
            }
            for (int i = numEquations; i < numVariables; i++) {
                for (int j = 0; j < cols; j++) {
                    coefficients[i][j] = 0;
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

    // Fungsi untuk menampilkan persamaan linear yang dimasukkan
    public void displayEquations() {
        System.out.println("\nSistem Persamaan Linear yang dimasukkan:");
        for (int i = 0; i < getNumEquations(); i++) {
            boolean isFirstTerm = true;
            for (int j = 0; j < getNumVariables(); j++) {
                double coeff = getCoefficients()[i][j];
                if (coeff != 0) {
                    if (!isFirstTerm) {
                        System.out.print(coeff >= 0 ? " + " : " - ");
                    } else if (coeff < 0) {
                        System.out.print("-");
                    }
                    System.out.print(absoluteValue(coeff) + "X" + (j + 1));
                    isFirstTerm = false;
                }
            }
            double constant = getCoefficients()[i][getNumVariables()];
            if (constant >= 0) {
                System.out.println(" = " + constant);
            } else {
                System.out.println(" = -" + absoluteValue(constant));
            }
        }
    }

    // Fungsi untuk menampilkan format persamaan linear
    private static void printExample(int numVariables) {
        StringBuilder example = new StringBuilder();
        example.append("a").append("X").append(1);
        for (int i = 1; i < numVariables; i++) {
            example.append(" ± ");
            example.append((char)('a' + i)).append("X").append(i + 1);
        }
        example.append(" = ± z");
        System.out.println(example.toString());
    }

    private static double absoluteValue(double number) {
        return number < 0 ? -number : number;
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(readLine());
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Error: Nilai harus antara " + min + " dan " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Input tidak valid. Masukkan angka bulat.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
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
                input.append((char) c);
            } catch (Exception e) {
                System.out.println("Error: Terjadi kesalahan saat membaca input.");
                return "";
            }
        }
        return input.toString().trim();
    }
}
