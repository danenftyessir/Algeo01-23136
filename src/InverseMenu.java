import java.io.FileWriter;
import java.io.IOException;

public class InverseMenu {
    // Fungsi untuk menampilkan menu matriks balikan di IntroCalculator (Main Class)
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
                StringBuilder results = new StringBuilder();
                if (inverse == null) {
                    System.out.println("\nMatriks adalah singular. Tidak ada matriks balikan.");
                    results.append("Matriks adalah singular. Tidak ada matriks balikan.");
                } else {
                    System.out.println("\nMatriks balikan:");
                    results.append("Matriks balikan:\n");
                    for (int i = 0; i < inverse.length; i++) {
                        for (int j = 0; j < inverse[0].length; j++) {
                            System.out.printf("%.2f ", inverse[i][j]);
                            results.append(inverse[i][j]).append(" ");
                        }
                        System.out.println();
                        results.append("\n");
                    }
                }
                System.out.println("Apakah Anda ingin menyimpan hasil ke file? (y/n) ");
                String saveChoice = readLine();
                if (saveChoice.equalsIgnoreCase("y")) {
                    saveToFile(results.toString());
                }
                break;
            case 2:
                System.out.println("Anda memilih: Metode Kofaktor-Adjoin");
                matrixInputSystem = new MatrixInputSystem();
                matrixInputSystem.inputMatrix();
                double[][] matrix_adj = matrixInputSystem.getMatrix();
                double[][] inverse_adj = InverseMatrix.matrixInverseAdjoint(matrix_adj);
                results = new StringBuilder();
                if (inverse_adj == null) {
                    System.out.println("\nMatriks adalah singular. Tidak ada matriks balikan.");
                    results.append("Matriks adalah singular. Tidak ada matriks balikan.");
                } else {
                    System.out.println("\nMatriks balikan:");
                    results.append("Matriks balikan:\n");
                    for (int i = 0; i < inverse_adj.length; i++) {
                        for (int j = 0; j < inverse_adj[0].length; j++) {
                            System.out.printf("%.2f ", inverse_adj[i][j]);
                            results.append(inverse_adj[i][j]).append(" ");
                        }
                        System.out.println();
                        results.append("\n");
                    }
                }
                System.out.println("Apakah Anda ingin menyimpan hasil ke file? (y/n) ");
                saveChoice = readLine();
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
