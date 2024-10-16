public class SPLMenu {
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
        int subChoice = IntroCalculator.getMenuChoice();
        System.out.println("Anda memilih metode " + subChoice);
        while (subChoice < 1 || subChoice > 4) {
            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            subChoice = IntroCalculator.getMenuChoice();
        }
        switch (subChoice) {
            case 1:
                System.out.println("Anda memilih: Metode eliminasi Gauss");
                LinearEquationInputSystem inputSystem = new LinearEquationInputSystem();
                inputSystem.inputLinearEquations();
                double[][] augmentedMatrix = inputSystem.getCoefficients();
                int n = inputSystem.getNumVariables();
                if (inputSystem.getNumEquations() != n) {
                    System.out.println("Jumlah persamaan dan variabel tidak sama. Silakan coba lagi.");
                    break;
                }

                double[][] rowEchelonForm = GaussElimination.gaussElimination(augmentedMatrix);
                System.out.println("\nMatriks setelah eliminasi Gauss:");
                GaussElimination.printMatrix(rowEchelonForm);

                int rank = GaussElimination.calculateRank(rowEchelonForm);
                int numVariables = n;
        
                if (rank < numVariables) {
                    if (GaussElimination.hasInconsistentSystem(rowEchelonForm, rank)) {
                        System.out.println("\nTidak ada solusi.");
                    } else {
                        System.out.println("\nSistem memiliki solusi banyak (parametrik).");
                        GaussElimination.printParametricSolution(rowEchelonForm, rank);
                    }
                } else {
                    double[] solutions = GaussElimination.backSubstitution(rowEchelonForm);
                    System.out.println("\nHasil SPL:");
                    for (int i = 0; i < n; i++) {
                        System.out.printf("x%d = %.2f\n", i + 1, solutions[i]);
                    }
                }
                break;
            case 2:
                System.out.println("Anda memilih: Metode eliminasi Gauss-Jordan");
                inputSystem = new LinearEquationInputSystem();
                inputSystem.inputLinearEquations();
                augmentedMatrix = inputSystem.getCoefficients();
                n = inputSystem.getNumVariables();
                if (inputSystem.getNumEquations() != n) {
                    System.out.println("Jumlah persamaan dan variabel tidak sama. Silakan coba lagi.");
                    break;
                }
                double[][] reducedRowEchelonForm = GaussJordanElimination.gaussJordanElimination(augmentedMatrix);
                System.out.println("\nMatriks setelah eliminasi Gauss-Jordan:");
                GaussElimination.printMatrix(reducedRowEchelonForm);

                rank = GaussJordanElimination.calculateRank(reducedRowEchelonForm);
                numVariables = n;
        
                if (rank < numVariables) {
                    if (GaussJordanElimination.hasInconsistentSystem(reducedRowEchelonForm, rank)) {
                        System.out.println("\nTidak ada solusi.");
                    } else {
                        System.out.println("\nSistem memiliki solusi banyak (parametrik).");
                        GaussJordanElimination.printParametricSolution(reducedRowEchelonForm, rank);
                    }
                } else {
                    System.out.println("\nHasil SPL:");
                    for (int i = 0; i < n; i++) {
                        System.out.printf("x%d = %.2f\n", i + 1, reducedRowEchelonForm[i][n]);
                    }
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
                if (solution == null) {
                    System.out.println("\nTidak ada solusi unik (matriks singular).");
                } else {
                    System.out.println("\nSolusi SPL:");
                    for (int i = 0; i < n; i++) {
                        System.out.printf("x%d = %.2f\n", i + 1, solution[i]);
                    }
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
                if (solution == null) {
                    System.out.println("\nTidak ada solusi unik (matriks singular).");
                } else {
                    System.out.println("\nSolusi SPL:");
                    for (int i = 0; i < n; i++) {
                        System.out.printf("x%d = %.2f\n", i + 1, solution[i]);
                    }
                }
                break;
            default:
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
        }
    }
}
