public class InverseMenu {
    public static void displayInverseMenu() {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║               MATRIKS BALIKAN                 ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║  1. Metode Eliminasi Gauss-Jordan             ║");
        System.out.println("║  2. Metode Kofaktor-Adjoin                    ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.print("Pilih metode (1-2): ");
        int subChoice = IntroCalculator.getMenuChoice();
        System.out.println("Anda memilih metode " + subChoice);
        while (subChoice < 1 || subChoice > 2) {
            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            subChoice = IntroCalculator.getMenuChoice();
        }
        switch (subChoice) {
            case 1:
                System.out.println("Anda memilih: Metode eliminasi Gauss-Jordan");
                MatrixInputSystem matrixInputSystem = new MatrixInputSystem();
                matrixInputSystem.inputMatrix();
                double[][] matrix_det = matrixInputSystem.getMatrix();
                double[][] inverse = InverseMatrix.matrixInverse(matrix_det);
                if (inverse == null) {
                    System.out.println("\nMatriks adalah singular. Tidak ada matriks balikan.");
                } else {
                    System.out.println("\nMatriks balikan:");
                    for (int i = 0; i < inverse.length; i++) {
                        for (int j = 0; j < inverse[0].length; j++) {
                            System.out.printf("%.2f ", inverse[i][j]);
                        }
                        System.out.println();
                    }
                }
                break;
            case 2:
                System.out.println("Anda memilih: Metode Kofaktor-Adjoin");
                matrixInputSystem = new MatrixInputSystem();
                matrixInputSystem.inputMatrix();
                double[][] matrix_adj = matrixInputSystem.getMatrix();
                double[][] inverse_adj = InverseMatrix.matrixInverseAdjoint(matrix_adj);
                if (inverse_adj == null) {
                    System.out.println("\nMatriks adalah singular. Tidak ada matriks balikan.");
                } else {
                    System.out.println("\nMatriks balikan:");
                    for (int i = 0; i < inverse_adj.length; i++) {
                        for (int j = 0; j < inverse_adj[0].length; j++) {
                            System.out.printf("%.2f ", inverse_adj[i][j]);
                        }
                        System.out.println();
                    }
                }
                break;
            default:
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
        }
    }
}
