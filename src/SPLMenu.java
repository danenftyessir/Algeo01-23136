import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SPLMenu {
    private static Scanner scanner = new Scanner(System.in);
    public static void displaySPLMenu() {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║         SISTEM PERSAMAAN LINIER               ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║  1. Metode eliminasi Gauss                    ║");
        System.out.println("║  2. Metode eliminasi Gauss-Jordan             ║");
        System.out.println("║  3. Metode matriks balikan                    ║");
        System.out.println("║  4. Kaidah Cramer                             ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.print("Pilih metode (1-4): ");
        int subChoice = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Anda memilih metode " + subChoice);
        while (subChoice < 1 || subChoice > 4) {
            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            System.out.print("Pilih metode (1-4): ");
            subChoice = scanner.nextInt();
            scanner.nextLine();
        }
        switch (subChoice) {
            case 1:
                System.out.println("Anda memilih: Metode eliminasi Gauss");
                LinearEquationInputSystem inputSystem = new LinearEquationInputSystem();
                inputSystem.inputLinearEquations();
                double[][] augmentedMatrix = inputSystem.getCoefficients();
                int n = inputSystem.getNumVariables();

                double[][] rowEchelonForm = GaussElimination.gaussElimination(augmentedMatrix);
                System.out.println("\nMatriks setelah eliminasi Gauss:");
                GaussElimination.printMatrix(rowEchelonForm);

                int rank = GaussElimination.calculateRank(rowEchelonForm);
                int numVariables = n;
                StringBuilder results = new StringBuilder();
        
                if (rank < numVariables) {
                    if (GaussElimination.hasInconsistentSystem(rowEchelonForm, rank)) {
                        System.out.println("\nTidak ada solusi.");
                        results.append("Tidak ada solusi.\n");
                    } else {
                        System.out.println("\nSistem memiliki solusi banyak (parametrik).");
                        results.append("Sistem memiliki solusi banyak (parametrik).\n");
                        String[] parSolutions = GaussElimination.parametricSolutions(rowEchelonForm, rank);
                        // Cetak solusi parametrik
                        System.out.println("Solusi Parametrik:");
                        for (String solution : parSolutions) {
                            if (solution != null) {
                                System.out.println(solution);
                            }
                        }
                    }
                } else {
                    double[] solutions = GaussElimination.backSubstitution(rowEchelonForm);
                    System.out.println("\nHasil SPL:");
                    results.append("Hasil SPL:\n");
                    for (int i = 0; i < n; i++) {
                        System.out.printf("x%d = %.2f\n", i + 1, solutions[i]);
                        results.append(String.format("x%d = %.2f\n", i + 1, solutions[i]));
                    }
                }
                System.out.print("Apakah Anda ingin menyimpan hasil ke file? (y/n): ");
                String saveChoice = readLine();
                while (!saveChoice.equals("y") && !saveChoice.equals("n")) {
                    System.out.println("Pilihan tidak valid, harap masukkan (y/n)!");
                    saveChoice = readLine();
                    if (saveChoice.equals("y") || saveChoice.equals("n")) {
                        break;
                    }
                }
                if (saveChoice.equalsIgnoreCase("y")) {
                    saveToFile(results.toString());
                }
                break;
            case 2:
                System.out.println("Anda memilih: Metode eliminasi Gauss-Jordan");
                inputSystem = new LinearEquationInputSystem();
                inputSystem.inputLinearEquations();
                augmentedMatrix = inputSystem.getCoefficients();
                n = inputSystem.getNumVariables();
                double[][] reducedRowEchelonForm = GaussJordanElimination.gaussJordanElimination(augmentedMatrix);
                System.out.println("\nMatriks setelah eliminasi Gauss-Jordan:");
                GaussElimination.printMatrix(reducedRowEchelonForm);

                rank = GaussJordanElimination.calculateRank(reducedRowEchelonForm);
                numVariables = n;
                results = new StringBuilder();
        
                if (rank < numVariables) {
                    if (GaussJordanElimination.hasInconsistentSystem(reducedRowEchelonForm, rank)) {
                        System.out.println("\nTidak ada solusi.");
                        results.append("Tidak ada solusi.\n");
                    } else {
                        System.out.println("\nSistem memiliki solusi banyak (parametrik).");
                        results.append("Sistem memiliki solusi banyak (parametrik).\n");
                        String[] parSolutions = GaussJordanElimination.parametricSolutions(reducedRowEchelonForm, rank);
                        // Cetak solusi parametrik
                        System.out.println("Solusi Parametrik:");
                        for (String solution : parSolutions) {
                            if (solution != null) {
                                System.out.println(solution);
                            }
                        }
                    }
                } else {
                    System.out.println("\nHasil SPL:");
                    results.append("Hasil SPL:\n");
                    for (int i = 0; i < n; i++) {
                        System.out.printf("x%d = %.2f\n", i + 1, reducedRowEchelonForm[i][n]);
                        results.append(String.format("x%d = %.2f\n", i + 1, reducedRowEchelonForm[i][n]));
                    }
                }
                System.out.print("Apakah Anda ingin menyimpan hasil ke file? (y/n): ");
                saveChoice = readLine();
                while (!saveChoice.equals("y") && !saveChoice.equals("n")) {
                    System.out.println("Pilihan tidak valid, harap masukkan (y/n)!");
                    saveChoice = readLine();
                    if (saveChoice.equals("y") || saveChoice.equals("n")) {
                        break;
                    }
                }
                if (saveChoice.equalsIgnoreCase("y")) {
                    saveToFile(results.toString());
                }                
                break;
            case 3:
                System.out.println("Anda memilih: Matriks balikan");
                inputSystem = new LinearEquationInputSystem();
                inputSystem.inputLinearEquations();
                augmentedMatrix = inputSystem.getCoefficients();
                n = inputSystem.getNumVariables();
                if (inputSystem.getNumEquations() != n) {
                    System.out.println("Jumlah persamaan dan variabel tidak sama. Silakan coba lagi.");
                    break;
                }
                double[][] coefficients = new double[n][n];
                double[] constants = new double[n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        coefficients[i][j] = augmentedMatrix[i][j];
                    }
                    constants[i] = augmentedMatrix[i][n];
                }
                double[] solution = InverseMatrix.solveLinearEquation(coefficients, constants);
                results = new StringBuilder();
                if (solution == null) {
                    System.out.println("\nTidak ada solusi unik (matriks singular).");
                    results.append("Tidak ada solusi unik (matriks singular).\n");
                } else {
                    System.out.println("\nSolusi SPL:");
                    results.append("Solusi SPL:\n");
                    for (int i = 0; i < n; i++) {
                        System.out.printf("x%d = %.2f\n", i + 1, solution[i]);
                        results.append(String.format("x%d = %.2f\n", i + 1, solution[i]));
                    }
                }
                System.out.print("Apakah Anda ingin menyimpan hasil ke file? (y/n): ");
                saveChoice = readLine();
                while (!saveChoice.equals("y") && !saveChoice.equals("n")) {
                    System.out.println("Pilihan tidak valid, harap masukkan (y/n)!");
                    saveChoice = readLine();
                    if (saveChoice.equals("y") || saveChoice.equals("n")) {
                        break;
                    }
                }
                if (saveChoice.equalsIgnoreCase("y")) {
                    saveToFile(results.toString());
                }                
                break;

            case 4:
                System.out.println("Anda memilih: Kaidah Cramer");
                inputSystem = new LinearEquationInputSystem();
                inputSystem.inputLinearEquations();
                augmentedMatrix = inputSystem.getCoefficients();
                n = inputSystem.getNumVariables();
                if (inputSystem.getNumEquations() != n) {
                    System.out.println("Jumlah persamaan dan variabel tidak sama. Silakan coba lagi.");
                    break;
                }
                coefficients = new double[n][n];
                constants = new double[n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        coefficients[i][j] = augmentedMatrix[i][j];
                    }
                    constants[i] = augmentedMatrix[i][n];
                }
                solution = CramerRule.solveUsingCramer(coefficients, constants);
                results = new StringBuilder();
                if (solution == null) {
                    System.out.println("\nTidak ada solusi unik (matriks singular).");
                    results.append("Tidak ada solusi unik (matriks singular).\n");
                } else {
                    System.out.println("\nSolusi SPL:");
                    results.append("Solusi SPL:\n");
                    for (int i = 0; i < n; i++) {
                        System.out.printf("x%d = %.2f\n", i + 1, solution[i]);
                        results.append(String.format("x%d = %.2f\n", i + 1, solution[i]));
                    }
                }
                System.out.print("Apakah Anda ingin menyimpan hasil ke file? (y/n): ");
                saveChoice = readLine();
                while (!saveChoice.equals("y") && !saveChoice.equals("n")) {
                    System.out.println("Pilihan tidak valid, harap masukkan (y/n)!");
                    saveChoice = readLine();
                    if (saveChoice.equals("y") || saveChoice.equals("n")) {
                        break;
                    }
                }
                if (saveChoice.equalsIgnoreCase("y")) {
                    saveToFile(results.toString());
                }                
                break;
            default:
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
        }
    }
    private static void saveToFile(String results) {
        System.out.print("Masukkan nama file yang akan disimpan (tanpa ekstensi): ");
        String name = readLine();
        name += ".txt";
        try (FileWriter writer = new FileWriter(name)) {
            writer.write(results);
            System.out.printf("Berhasil disimpan ke file %s\n",name);
        } catch (IOException e) {
            System.out.println("Error: Gagal menyimpan ke file");
        }
    }

    public static String readLine() {
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
