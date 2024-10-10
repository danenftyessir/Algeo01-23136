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
        this.numEquations = readInt("\nMasukkan jumlah persamaan (1-4): ", 1, 4);
        this.numVariables = readInt("Masukkan jumlah variabel (1-4): ", 1, 4);
        this.coefficients = new double[getNumEquations()][getNumVariables() + 1];

        System.out.println("\nMasukkan koefisien dan konstanta untuk setiap persamaan.");
        System.out.println("Berikut format untuk " + getNumVariables() + " variabel:");
        printExample(getNumVariables());

        for (int i = 0; i < getNumEquations(); i++) {
            System.out.println("\nPersamaan " + (i + 1) + ":");
            for (int j = 0; j < getNumVariables(); j++) {
                getCoefficients()[i][j] = readDouble("  Koefisien X" + (j + 1) + ": ");
            }
            getCoefficients()[i][getNumVariables()] = readDouble("  Konstanta: ");
        }

        displayEquations();
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
        if (number < 0) {
            return 0 - number;
        }
        return number;
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
                input.append((char)c);
            } catch (Exception e) {
                System.out.println("Error: Terjadi kesalahan saat membaca input.");
                return "";
            }
        }
        return input.toString().trim();
    }
}
